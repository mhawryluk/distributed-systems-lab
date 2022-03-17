import os
import socket
from threading import Thread
from ascii_art import art
from common import server_ip, server_port, multicast_ip, multicast_port, tcp_receive

tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
udp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
multicast_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

nick: str = ''
running: bool = True


def listen_tcp():
    try:
        for message in tcp_receive(tcp_socket):
            if message is None:
                dispose()
                return
            print(message)
            print('>> ', end='')
    except (KeyboardInterrupt, OSError):
        pass
    finally:
        dispose()


def listen_udp():
    try:
        while running:
            message, address = udp_socket.recvfrom(1000)
            print(message.decode())
            print('>> ', end='')

    except (KeyboardInterrupt, OSError):
        pass
    finally:
        dispose()


def listen_multicast():
    global running
    try:
        while running:
            message, address = multicast_socket.recvfrom(1000)
            if address[1] != udp_socket.getsockname()[1]:
                print(message.decode())
                print('>> ', end='')
    except (KeyboardInterrupt, OSError):
        pass
    finally:
        dispose()


def send_tcp(message: str):
    tcp_socket.sendall(message.encode())


def send_udp(message: str):
    udp_socket.sendto(message.encode(), (server_ip, server_port))


def send_multicast(message: str):
    udp_socket.sendto(message.encode(), (multicast_ip, multicast_port))


def send_message():
    global running
    try:
        while running:
            command = input('>> ').strip().lower()

            if command == 't':
                message = input('message: ')
                send_tcp(message + '\n')

            elif command == 'u':
                image = input('image title: ')
                if image in art:
                    send_udp(art[image])
                else:
                    print('image not found:', image)

            elif command == 'm':
                image = input('image title: ')
                if image in art:
                    send_multicast(f'({nick}):\n{art[image]}')
                else:
                    print('image not found:', image)

            else:
                print('Unrecognized command: ', command)
    except KeyboardInterrupt:
        dispose()


def dispose():
    global running
    running = False
    tcp_socket.close()
    udp_socket.close()
    multicast_socket.close()
    os._exit(0)


if __name__ == '__main__':
    try:
        # connect to tcp server
        tcp_socket.connect((server_ip, server_port))
        _, port = tcp_socket.getsockname()
        udp_socket.bind(('', port))

        # choose nick
        nick = input('nick: ')
        tcp_socket.sendall((nick + '\n').encode())

        # initialize multicast channel
        multicast_socket.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, socket.inet_aton(multicast_ip) + socket.inet_aton('0.0.0.0'))
        multicast_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        multicast_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEPORT, 1)
        multicast_socket.bind(('', multicast_port))

        # start threads
        tcp_thread = Thread(target=listen_tcp, daemon=True)
        udp_thread = Thread(target=listen_udp, daemon=True)
        multicast_thread = Thread(target=listen_multicast, daemon=True)
        tcp_thread.start()
        udp_thread.start()
        multicast_thread.start()

        # enter send message prompt
        send_message()

    except KeyboardInterrupt:
        pass
    finally:
        dispose()
