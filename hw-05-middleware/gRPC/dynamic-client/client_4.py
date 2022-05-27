import grpc
from google.protobuf.descriptor_pool import DescriptorPool
from google.protobuf.message_factory import MessageFactory
from grpc_reflection.v1alpha.proto_reflection_descriptor_database import ProtoReflectionDescriptorDatabase

channel = grpc.insecure_channel("localhost:50051")
reflection_db = ProtoReflectionDescriptorDatabase(channel)
desc_pool = DescriptorPool(reflection_db)

message_desc = desc_pool.FindMessageTypeByName("calculator.ArithmeticOpArguments")
ArithmeticOpArguments = MessageFactory(desc_pool).GetPrototype(message_desc)

message_desc = desc_pool.FindMessageTypeByName("calculator.ArithmeticOpResult")
ArithmeticOpResults = MessageFactory(desc_pool).GetPrototype(message_desc)

request = ArithmeticOpArguments(arg1=5, arg2=10)

print(channel.unary_unary(
        method='/calculator.Calculator/Add',
        request_serializer=ArithmeticOpArguments.SerializeToString,
        response_deserializer=ArithmeticOpResults.FromString
    )(request)
)
