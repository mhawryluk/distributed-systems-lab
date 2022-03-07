import socket
from threading import Thread

server_ip = "127.0.0.1"
server_port = 9018

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
        self.running = True

    def run(self):
        buffer_message = ''
        while self.running:
            message = self.socket.recv(5)

            if message == b'':
                self.socket.close()
                break

            buffer_message += message.decode()

            while True:
                newline_index = buffer_message.find('\n')

                if newline_index != -1:
                    for client in clients:
                        if client != self:
                            self.send_tcp(
                                client.socket, f'#{client.id}: {buffer_message[:newline_index+1]}\n')

                    buffer_message = '' if newline_index == len(
                        buffer_message) - 1 else buffer_message[newline_index+1:]
                else:
                    break

    def send_tcp(self, socket, message):
        total_sent = 0

        while total_sent < len(message):
            sent = socket.send(message[total_sent:].encode())
            if sent == 0:
                raise Exception("socket connection broken")
            total_sent = total_sent + sent


def listen_connections():
    tcp_socket.listen()
    try:
        while True:
            client_socket, address = tcp_socket.accept()
            client_thread = Client(client_socket)
            clients.append(client_thread)
            client_thread.start()
    except:
        tcp_socket.close()
        print('listen closed')


if __name__ == '__main__':
    try:
        listen_connections()
    except:
        for client in clients:
            client.running = False
        for client in clients:
            client.join()

        tcp_socket.close()
        print('main closed')
