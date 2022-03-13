import socket
from threading import Thread
from common import server_ip, server_port, multicast_ip, multicast_port
from ascii_art import art

tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
udp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
multicast_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

running = True


def send_message():
    global running
    try:
        while running:
            command = input('>> ').strip()

            if command.lower() == 't':
                message = input('message: ')
                send_tcp(message + '\n')

            elif command.lower() == 'u':
                image = input('image title: ')
                if image in art:
                    send_udp(art[image])
                else:
                    print('image not found:', image)

            elif command.lower() == 'm':
                message = input('message: ')
                send_multicast(message + '\n')

            else:
                print('Unrecognized command: ', command)
    except KeyboardInterrupt:
        print('client, send_message: interrupt')
        running = False


def listen_tcp():
    global running
    try:
        while running:
            message = tcp_socket.recv(1024)

            if message == b'':
                return

            print(message.decode())
    except KeyboardInterrupt:
        print('client, listen_tcp: interrupt')
        running = False


def listen_udp():
    global running
    try:
        while running:
            message, address = udp_socket.recvfrom(1024)
            print(message.decode())
    except KeyboardInterrupt:
        print('client, listen_udp: interrupt')
        running = False


def listen_multicast():
    global running
    try:
        while running:
            message, address = multicast_socket.recvfrom(1024)
            if address[1] != udp_socket.getsockname()[1]:
                print(message.decode())
    except KeyboardInterrupt:
        print('client, listen_multicast: interrupt')
        running = False


def send_tcp(message):
    try:
        tcp_socket.sendall(message.encode())
    except KeyboardInterrupt:
        print('client, send_tcp: interrupt')


def send_udp(message):
    try:
        udp_socket.sendto(message.encode(), (server_ip, server_port))
    except KeyboardInterrupt:
        print('client, send_udp: interrupt')


def send_multicast(message):
    try:
        udp_socket.sendto(message.encode(), (multicast_ip, multicast_port))
    except KeyboardInterrupt:
        print('client, send_multicast: interrupt')


if __name__ == '__main__':
    try:
        tcp_socket.connect((server_ip, server_port))
        _, port = tcp_socket.getsockname()

        udp_socket.bind(('', port))

        multicast_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

        membership = socket.inet_aton(multicast_ip) + socket.inet_aton("0.0.0.0")

        multicast_socket.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, membership)

        multicast_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        multicast_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEPORT, 1)
        multicast_socket.setsockopt(
            socket.IPPROTO_IP, socket.IP_MULTICAST_TTL, (1).to_bytes(1, "little")
        )
        multicast_socket.bind(('', multicast_port))

        tcp_thread = Thread(target=listen_tcp, daemon=True)
        udp_thread = Thread(target=listen_udp, daemon=True)
        multicast_thread = Thread(target=listen_multicast, daemon=True)

        tcp_thread.start()
        udp_thread.start()
        multicast_thread.start()

        send_message()

    except KeyboardInterrupt:
        print('client, main: interrupt')
        running = False

    finally:
        tcp_socket.close()
        udp_socket.close()
