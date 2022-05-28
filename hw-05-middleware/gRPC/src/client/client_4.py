import grpc
from google.protobuf.descriptor_pool import DescriptorPool
from google.protobuf.message_factory import MessageFactory
from grpc_reflection.v1alpha.proto_reflection_descriptor_database import ProtoReflectionDescriptorDatabase

channel = grpc.insecure_channel("localhost:50051")
reflection_db = ProtoReflectionDescriptorDatabase(channel)
desc_pool = DescriptorPool(reflection_db)

services = reflection_db.get_services()
print(services)

service_desc = desc_pool.FindServiceByName("colors.ChangeMode")
method_desc = service_desc.FindMethodByName("RGBToHSV")

# print(method_desc.client_streaming)
# print(method_desc.server_streaming)

message_desc = desc_pool.FindMessageTypeByName("colors.RGBColor")
RGBColor = MessageFactory(desc_pool).GetPrototype(message_desc)

message_desc = desc_pool.FindMessageTypeByName("colors.HSVColor")
HSVColor = MessageFactory(desc_pool).GetPrototype(message_desc)

request = RGBColor(r=255, g=255, b=128)

response = channel.unary_unary(
        method='/colors.ChangeMode/RGBToHSV',
        request_serializer=RGBColor.SerializeToString,
        response_deserializer=HSVColor.FromString
    )(request)

print(response)

response = channel.unary_unary(
        method='/colors.ChangeMode/HSVToRGB',
        request_serializer=HSVColor.SerializeToString,
        response_deserializer=RGBColor.FromString
    )(HSVColor(h=.5, s=.7, v=.1))

print(response)
