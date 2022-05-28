from time import sleep

import grpc
from google.protobuf.descriptor import MethodDescriptor
from google.protobuf.message_factory import MessageFactory
from grpc_reflection.v1alpha import reflection_pb2_grpc
from grpc_reflection.v1alpha.proto_reflection_descriptor_database import ProtoReflectionDescriptorDatabase
from google.protobuf.descriptor_pool import DescriptorPool
from google.protobuf import descriptor_pb2, descriptor_pool as _descriptor_pool, symbol_database as _symbol_database

channel = grpc.insecure_channel("localhost:50051", [("content-type", "text/plain")])
reflection_db = ProtoReflectionDescriptorDatabase(channel)
services = reflection_db.get_services()

desc_pool = DescriptorPool(reflection_db)

service_desc = desc_pool.FindServiceByName("calculator.Calculator")
method_desc = service_desc.FindMethodByName("Add")

request_desc = desc_pool.FindMessageTypeByName("calculator.ArithmeticOpArguments")
request = MessageFactory(desc_pool).GetPrototype(request_desc)(arg1=5, arg2=10)


service_full_name = service_desc.full_name

for method_proto in service_desc.methods:
    method_name = method_proto.name
    print(method_name)
    method_desc: MethodDescriptor = service_desc.methods_by_name[method_name]
#
    input_type = _symbol_database.Default().GetPrototype(method_desc.input_type)
    output_type = _symbol_database.Default().GetPrototype(method_desc.output_type)

    # method_type = MethodTypeMatch[(method_proto.client_streaming, method_proto.server_streaming)]
    method_type = 'unary_unary'
#
    method_register_func = channel.unary_unary
    handler = method_register_func(
        method=f'/{service_full_name}/{method_name}',
        request_serializer=input_type.SerializeToString,
        response_deserializer=output_type.FromString
    )

    print(handler(request))

#     metadata[method_name] = MethodMetaData(
#         method_type=method_type,
#         input_type=input_type,
#         output_type=output_type,
#         handler=handler
#     )
# return metadata

