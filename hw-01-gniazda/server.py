import socket
from threading import Thread
from config import server_ip, server_port

clients = []
current_id = 0

tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcp_socket.bind((server_ip, server_port))

udp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
udp_socket.bind((server_ip, server_port))

running = True


class Client(Thread):
    def __init__(self, socket, address):
        global current_id
        super().__init__(daemon=True)
        self.id = current_id
        current_id += 1
        self.socket = socket
        self.running = True
        self.address = address

    def run(self):
        self.receive_tcp()

    def receive_tcp(self):
        global running
        try:
            buffer_message = ''
            while running:
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
                                client.socket.sendall(
                                    f'#{client.id}: {buffer_message[:newline_index+1]}\n'.encode())

                        buffer_message = '' if newline_index == len(
                            buffer_message) - 1 else buffer_message[newline_index+1:]
                    else:
                        break
        except KeyboardInterrupt:
            running = False

    def send_tcp(self, socket, message):
        total_sent = 0

        while total_sent < len(message):
            sent = socket.send(message[total_sent:].encode())
            if sent == 0:
                raise Exception("socket connection broken")
            total_sent = total_sent + sent


def listen_connections():
    global running
    tcp_socket.listen()
    try:
        while running:
            client_socket, address = tcp_socket.accept()
            client_thread = Client(client_socket, address)
            clients.append(client_thread)
            client_thread.start()
    except KeyboardInterrupt:
        running = False


def listen_udp():
    global running
    print('listening udp')
    try:
        while running:
            message, address = udp_socket.recvfrom(3)
            print(message.decode(), address)
            for client in clients:
                if client.address != address:
                    print(udp_socket.sendto(message, client.address))
    except:
        running = False


if __name__ == '__main__':
    try:
        tcp_thread = Thread(target=listen_connections, daemon=True)
        udp_thread = Thread(target=listen_udp, daemon=True)
        tcp_thread.start()
        udp_thread.start()
        tcp_thread.join()
        udp_thread.join()
    except KeyboardInterrupt:
        for client in clients:
            client.running = False
        for client in clients:
            client.join()
    finally:
        tcp_socket.close()
        udp_socket.close()
        print('main closed')
