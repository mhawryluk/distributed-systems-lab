import socket
from threading import Thread
from common import server_ip, server_port

tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
udp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

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
                message = input('message: ')
                send_udp(message)

            elif command.lower() == 'm':
                pass

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
    print('listening udp')
    global running
    try:
        while running:
            print('receiving...')
            message, address = udp_socket.recvfrom(1024)
            print('received')
            print(message.decode())
    except KeyboardInterrupt:
        print('client, listen_udp: interrupt')
        running = False

    print('listen udp END')


def send_tcp(message):
    try:
        tcp_socket.sendall(message.encode())
    except KeyboardInterrupt:
        print('client, send_tcp: interrupt')
    # total_sent = 0

    # while total_sent < len(message):
    #     sent = tcp_socket.send(message[total_sent:].encode())
    #     if sent == 0:
    #         print("socket connection broken")
    #         return
    #     total_sent = total_sent + sent


def send_udp(message):
    try:
        udp_socket.sendto(message.encode(), (server_ip, server_port))
    except KeyboardInterrupt:
        print('client, send_udp: interrupt')


if __name__ == '__main__':
    try:
        tcp_socket.connect((server_ip, server_port))
        _, port = tcp_socket.getsockname()

        udp_socket.bind(('', port))

        tcp_thread = Thread(target=listen_tcp, daemon=True)
        udp_thread = Thread(target=listen_udp, daemon=True)

        tcp_thread.start()
        udp_thread.start()

        send_message()

    except KeyboardInterrupt:
        print('client, main: interrupt')
        running = False

    finally:
        tcp_socket.close()
        udp_socket.close()
