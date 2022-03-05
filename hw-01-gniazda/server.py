import socket
from threading import Thread

server_ip = "127.0.0.1"
server_port = 9002

clients = []
current_id = 0

tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcp_socket.bind(('', server_port))


class Client(Thread):
    def __init__(self, socket):
        global current_id
        super().__init__(daemon=True)
        self.id = current_id
        current_id += 1
        self.socket = socket

    def run(self):
        while True:
            message = self.socket.recv(5)
            if message == b'':
                break
            print(message.decode())
            for client in clients:
                if client != self:
                    client.socket.send(message)


def listen_connections():
    tcp_socket.listen()
    while True:
        client_socket, address = tcp_socket.accept()
        client_thread = Client(client_socket)
        clients.append(client_thread)
        client_thread.start()


if __name__ == '__main__':
    try:
        listen_connections()
    except:
        tcp_socket.close()
        print('closed')
