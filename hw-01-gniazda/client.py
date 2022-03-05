import socket

server_ip = "127.0.0.1"
server_port = 9008

tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcp_socket.connect((server_ip, server_port))


def send_message():
    while True:
        command = input('>> ').strip()

        if command.lower() == 't':
            message = input('message: ')
            tcp_socket.send(message)
        elif command.lower() == 'u':
            pass
        elif command.lower() == 'm':
            pass
        else:
            print('Unrecognized command: ', command)
