import socket
from threading import Thread


server_ip = "127.0.0.1"
client_ip = "127.0.0.1"
server_port = 9002

tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)


def send_message():
    while True:
        command = input('>> ').strip()

        if command.lower() == 't':
            message = input('message: ')
            send_tcp(message)

        elif command.lower() == 'u':
            pass
        elif command.lower() == 'm':
            pass
        else:
            print('Unrecognized command: ', command)


def send_tcp(message):
    total_sent = 0

    while total_sent < len(message):
        sent = tcp_socket.send(message[total_sent:].encode())
        if sent == 0:
            raise RuntimeError("socket connection broken")
        total_sent = total_sent + sent


def listen_tcp():

    while True:
        message = tcp_socket.recv(1024)

        if message == b'':
            raise RuntimeError("socket connection broken")

        print(message.decode())


if __name__ == '__main__':
    tcp_socket.connect((server_ip, server_port))

    listen_thread = Thread(target=listen_tcp, daemon=True)
    listen_thread.start()

    send_thread = Thread(target=send_message, daemon=True)
    send_thread.start()

    listen_thread.join()
    send_thread.join()
