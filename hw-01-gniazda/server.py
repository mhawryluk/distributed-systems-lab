import socket
from threading import Thread

from common import server_ip, server_port
from client_thread import Client

tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcp_socket.bind((server_ip, server_port))

udp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
udp_socket.bind((server_ip, server_port))

running = True

clients = {}


def listen_connections():
    global running
    tcp_socket.listen()
    try:
        while running:
            client_socket, address = tcp_socket.accept()
            client_thread = Client(client_socket, address, clients)
            clients[address] = client_thread
            client_thread.start()
    except KeyboardInterrupt:
        print('server, listen_connections: interrupt')
        running = False


def listen_udp():
    global running
    try:
        while running:
            message, address = udp_socket.recvfrom(1024)
            sender_id = clients[address].id if address in clients else 'unknown'
            message = f'#{sender_id}: {message.decode()}\n'.encode()
            for client_address in clients:
                if client_address != address:
                    udp_socket.sendto(message, client_address)
    except KeyboardInterrupt:
        print('server, listen_udp: interrupt')
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
        print('server, main: interrupt')
        for client in clients.values():
            client.running = False
        for client in clients.values():
            client.join()
    finally:
        tcp_socket.close()
        udp_socket.close()
        print('main closed')
