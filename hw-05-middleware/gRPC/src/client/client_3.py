import grpc
from google.protobuf import symbol_database as _symbol_database
from google.protobuf.descriptor import MethodDescriptor
from google.protobuf.descriptor_pb2 import ServiceDescriptorProto
from google.protobuf.descriptor_pool import DescriptorPool
from google.protobuf.message_factory import MessageFactory
from grpc_reflection.v1alpha.proto_reflection_descriptor_database import ProtoReflectionDescriptorDatabase

channel = grpc.insecure_channel("localhost:50051")
reflection_db = ProtoReflectionDescriptorDatabase(channel)
services = reflection_db.get_services()

desc_pool = DescriptorPool(reflection_db)

service_descriptor = desc_pool.FindServiceByName("calculator.Calculator")

request_desc = desc_pool.FindMessageTypeByName("calculator.ArithmeticOpArguments")
request = MessageFactory(desc_pool).GetPrototype(request_desc)(arg1=5, arg2=10)

svc_desc_proto = ServiceDescriptorProto()
service_descriptor.CopyToProto(svc_desc_proto)
service_full_name = service_descriptor.full_name

method_desc = service_descriptor.methods_by_name["Add"]
input_type = _symbol_database.Default().GetPrototype(method_desc.input_type)
output_type = _symbol_database.Default().GetPrototype(method_desc.output_type)

method_register_func = getattr(channel, 'unary_unary')

handler = channel.unary_unary(
    method=f'/calculator.Calculator/Add',
    request_serializer=input_type.SerializeToString,
    response_deserializer=output_type.FromString
)

print(handler(request))