from grpc_requests import Client

client = Client.get_by_endpoint("localhost:50051")

print(client.service_names)

request_data = {"arg1": 1, 'arg2': 2}
result = client.request("calculator.Calculator", "Add", request_data)
print(result)