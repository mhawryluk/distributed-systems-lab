import socket
from threading import Thread

from common import server_ip, server_port
from client_thread import Client

clients = []

tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcp_socket.bind((server_ip, server_port))

udp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
udp_socket.bind((server_ip, server_port))

running = True


def listen_connections():
    global running
    tcp_socket.listen()
    try:
        while running:
            client_socket, address = tcp_socket.accept()
            client_thread = Client(client_socket, address, clients)
            clients.append(client_thread)
            client_thread.start()
    except KeyboardInterrupt:
        print('server, listen_connections: interrupt')
        running = False


def listen_udp():
    global running
    try:
        while running:
            message, address = udp_socket.recvfrom(1024)
            print(message.decode(), address)
            for client in clients:
                if client.address != address:
                    udp_socket.sendto(message, client.address)
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
        for client in clients:
            client.running = False
        for client in clients:
            client.join()
    finally:
        tcp_socket.close()
        udp_socket.close()
        print('main closed')
