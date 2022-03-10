from email.headerregistry import Address
import socket
from threading import Thread
from config import server_ip, server_port


tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
udp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

running = True


def send_message():
    while running:
        command = input('>> ').strip()

        if command.lower() == 't':
            message = input('message: ')
            send_tcp(message+'\n')

        elif command.lower() == 'u':
            message = input('message: ')
            send_udp(message)

        elif command.lower() == 'm':
            pass

        else:
            print('Unrecognized command: ', command)


def listen_tcp():
    try:
        while running:
            message = tcp_socket.recv(5)

            if message == b'':
                return

            print(message.decode())
    except:
        running = False


def listen_udp():
    print('listening udp')
    try:
        while running:
            message, address = udp_socket.recvfrom(3)
            print(message.decode())
    except:
        running = False


def send_tcp(message):
    tcp_socket.sendall(message.encode())
    # total_sent = 0

    # while total_sent < len(message):
    #     sent = tcp_socket.send(message[total_sent:].encode())
    #     if sent == 0:
    #         print("socket connection broken")
    #         return
    #     total_sent = total_sent + sent


def send_udp(message):
    udp_socket.sendto(message.encode(), (server_ip, server_port))


if __name__ == '__main__':
    try:
        tcp_socket.connect((server_ip, server_port))

        # tcp_thread = Thread(target=listen_tcp, daemon=True)
        udp_thread = Thread(target=listen_udp, daemon=True)
        # tcp_thread.start()
        udp_thread.start()

        send_message()

    except:
        running = False

    finally:
        tcp_socket.close()
        udp_socket.close()
