import grpc
from google.protobuf.descriptor_pool import DescriptorPool
from google.protobuf.message_factory import MessageFactory
from grpc_reflection.v1alpha.proto_reflection_descriptor_database import ProtoReflectionDescriptorDatabase

SERVER_ADDRESS = "localhost:50051"
SERVICE_NAME = "colors.ColorTransform"
METHOD_NAME = "HSVColorsAggregate"

channel = grpc.insecure_channel(SERVER_ADDRESS)
reflection_db = ProtoReflectionDescriptorDatabase(channel)
desc_pool = DescriptorPool(reflection_db)

# getting service descriptor
service_desc = desc_pool.FindServiceByName(SERVICE_NAME)

# getting method descriptor
method_desc = service_desc.FindMethodByName(METHOD_NAME)

# getting message descriptor and types

# by name:
HSVColor = MessageFactory(desc_pool).GetPrototype(desc_pool.FindMessageTypeByName("colors.HSVColor"))
ColorsOperationArgument = MessageFactory(desc_pool).GetPrototype(desc_pool.FindMessageTypeByName("colors.ColorsOperationArgument"))
OperationType = desc_pool.FindEnumTypeByName("colors.OperationType")

request = ColorsOperationArgument(colors=[
    HSVColor(h=.5, s=.6, v=.2),
    HSVColor(h=.1, s=.6, v=.2),
    HSVColor(h=.2, s=.6, v=.2)
], operation=OperationType.values_by_name["MAX"].index)

print(f"SENDING REQUEST to {METHOD_NAME} method passing:\n{request}")

response = channel.unary_unary(
        method=f'/{SERVICE_NAME}/{METHOD_NAME}',
        request_serializer=ColorsOperationArgument.SerializeToString,
        response_deserializer=HSVColor.FromString
    )(request)

print(f"RESPONSE:\n{response}")


