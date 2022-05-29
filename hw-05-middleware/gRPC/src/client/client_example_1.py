import grpc
from google.protobuf.descriptor_pool import DescriptorPool
from google.protobuf.message_factory import MessageFactory
from grpc_reflection.v1alpha.proto_reflection_descriptor_database import ProtoReflectionDescriptorDatabase

SERVER_ADDRESS = "localhost:50051"
SERVICE_NAME = "colors.ColorTransform"
METHOD_NAME = "HSVToRGB"

channel = grpc.insecure_channel(SERVER_ADDRESS)
reflection_db = ProtoReflectionDescriptorDatabase(channel)
desc_pool = DescriptorPool(reflection_db)

# listing all offered services
print("SERVICES:")
for service_name in reflection_db.get_services():
    print("-", service_name)
print()

# getting service descriptor
service_desc = desc_pool.FindServiceByName(SERVICE_NAME)

# listing all methods of a service
print(f"METHODS of {service_desc.full_name}:")
for method_desc in service_desc.methods:
    print("-", method_desc.name)
print()

# getting method descriptor
method_desc = service_desc.FindMethodByName(METHOD_NAME)

# listing information about the method
print(f"METHOD info:")
print("- full name:", method_desc.full_name)
print("- containing service:", method_desc.containing_service.name)
print("- client stream?", method_desc.client_streaming)
print("- server stream?", method_desc.server_streaming)
print("- input type:", method_desc.input_type.name)
print("- output type:", method_desc.output_type.name)
print("- index:", method_desc.index)
print()

# getting message descriptor and types

# # by name:
# message_desc = desc_pool.FindMessageTypeByName("colors.RGBColor")
# RGBColor = MessageFactory(desc_pool).GetPrototype(message_desc)

# from method descriptor:
input_type = MessageFactory(desc_pool).GetPrototype(method_desc.input_type)
output_type = MessageFactory(desc_pool).GetPrototype(method_desc.output_type)

# creating and sending a request
print("MESSAGE info:")
input_type_desc = input_type.DESCRIPTOR
print("- name:", input_type_desc.name)
print("- file:", input_type_desc.file.name)
print("- syntax:", input_type_desc.syntax)
print("- fields:", list(input_type_desc.fields_by_name))
print()

request = input_type()

print("FIELD info:")
for field in input_type_desc.fields:
    print('- containing type:', field.containing_type.name)
    print('- json name:', field.json_name)
    print('- name:', field.name)
    print('- number:', field.number)
    print('- type:', field.type)
    print('(value of int32:', field.TYPE_INT32, ')')
    print('(value of float:', field.TYPE_FLOAT, ')')
    print('- label:', field.label)
    print('- default value:', field.default_value)
    print('- cpp type:', field.cpp_type)

    print()
    break

for field in input_type_desc.fields:
    value = input(f'{field.name}: ')

    match field.type:
        case field.TYPE_INT32:
            value = int(value)
        case field.TYPE_FLOAT:
            value = float(value)

    setattr(request, field.name, value)

print()
print(f"SENDING REQUEST to {METHOD_NAME} method passing:\n{request}")

match method_desc.client_streaming, method_desc.server_streaming:
    case True, True:
        handler = channel.stream_stream
    case True, False:
        handler = channel.stream_unary
    case False, True:
        handler = channel.unary_stream
    case False, False:
        handler = channel.unary_unary

response = handler(
        method=f'/{service_desc.full_name}/{method_desc.name}',
        request_serializer=input_type.SerializeToString,
        response_deserializer=output_type.FromString
    )(request)

print(f"RESPONSE:\n{response}")


