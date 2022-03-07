import socket
from threading import Thread


server_ip = "127.0.0.1"
server_port = 9018

tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
running = True


def send_message():
    try:
        while running:
            command = input('>> ').strip()

            if command.lower() == 't':
                message = input('message: ')
                send_tcp(message+'\n')

            elif command.lower() == 'u':
                pass
            elif command.lower() == 'm':
                pass
            else:
                print('Unrecognized command: ', command)
    except:
        pass


def listen_tcp():

    while running:
        message = tcp_socket.recv(5)

        if message == b'':
            raise RuntimeError("socket connection broken")

        print(message.decode())


def send_tcp(message):
    total_sent = 0

    while total_sent < len(message):
        sent = tcp_socket.send(message[total_sent:].encode())
        if sent == 0:
            print("socket connection broken")
            return
        total_sent = total_sent + sent


if __name__ == '__main__':
    try:
        tcp_socket.connect((server_ip, server_port))

        listen_thread = Thread(target=listen_tcp, daemon=True)
        listen_thread.start()

        send_message()

    except:
        running = False

    finally:
        tcp_socket.close()
