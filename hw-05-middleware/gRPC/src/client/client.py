import grpc
from google.protobuf.descriptor_pool import DescriptorPool
from google.protobuf.message_factory import MessageFactory
from grpc_reflection.v1alpha.proto_reflection_descriptor_database import ProtoReflectionDescriptorDatabase

channel = grpc.insecure_channel("localhost:50051")
reflection_db = ProtoReflectionDescriptorDatabase(channel)
desc_pool = DescriptorPool(reflection_db)

# listing all offered services
print("SERVICES:")
for service_name in reflection_db.get_services():
    print("-", service_name)
print()

# getting service descriptor
service_desc = desc_pool.FindServiceByName("colors.ChangeMode")

# listing all methods of a service
print(f"METHODS of {service_desc.full_name}:")
for method_desc in service_desc.methods:
    print("-", method_desc.name)
print()

# getting method descriptor
method_desc = service_desc.FindMethodByName("RGBToHSV")

# listing information about the method
print(f"Descriptor of {method_desc.full_name}:")
print("- containing service:", method_desc.containing_service.name)
print("- client stream?", method_desc.client_streaming)
print("- server stream?", method_desc.server_streaming)
print("- input type:", method_desc.input_type.name)
print("- output type:", method_desc.output_type.name)
print("- index:", method_desc.index)
print()

# getting message descriptor and types

# by name:
message_desc = desc_pool.FindMessageTypeByName("colors.RGBColor")
RGBColor = MessageFactory(desc_pool).GetPrototype(message_desc)

# from method descriptor:
HSVColor = MessageFactory(desc_pool).GetPrototype(method_desc.output_type)

# creating and sending a request
request = RGBColor(r=255, g=255, b=128)

print(f"SENDING REQUEST to RGBToHSV method passing:\n{request}")
response = channel.unary_unary(
        method='/colors.ChangeMode/RGBToHSV',
        request_serializer=RGBColor.SerializeToString,
        response_deserializer=HSVColor.FromString
    )(request)

print(f"RESPONSE:\n{response}")

request = HSVColor(h=.5, s=.7, v=.1)

print(f"SENDING REQUEST to HSVToRGB method passing:\n{request}")
response = channel.unary_unary(
        method='/colors.ChangeMode/HSVToRGB',
        request_serializer=HSVColor.SerializeToString,
        response_deserializer=RGBColor.FromString
    )(request)

print(f"RESPONSE:\n{response}")
