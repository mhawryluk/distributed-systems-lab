from threading import Thread
import socket


class Client(Thread):
    current_id = 0

    def __init__(self, tcp_socket: socket.socket, address: tuple, clients: list):
        super().__init__(daemon=True)
        self.id = Client.current_id
        Client.current_id += 1
        self.socket = tcp_socket
        self.address = address
        self.clients = clients

    def run(self):
        self.receive_tcp()

    def receive_tcp(self):
        try:
            buffer_message = ''
            while True:
                message = self.socket.recv(5)

                if message == b'':
                    self.socket.close()
                    break

                buffer_message += message.decode()

                while True:
                    newline_index = buffer_message.find('\n')

                    if newline_index != -1:
                        for client in self.clients:
                            if client != self:
                                client.socket.sendall(
                                    f'#{client.id}: {buffer_message[:newline_index+1]}\n'.encode())

                        buffer_message = '' if newline_index == len(
                            buffer_message) - 1 else buffer_message[newline_index+1:]
                    else:
                        break
        except KeyboardInterrupt:
            return

    def send_tcp(self, tcp_socket: socket.socket, message: str):
        total_sent = 0

        while total_sent < len(message):
            sent = tcp_socket.send(message[total_sent:].encode())
            if sent == 0:
                raise Exception("socket connection broken")
            total_sent = total_sent + sent