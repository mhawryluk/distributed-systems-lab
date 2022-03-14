import socket

server_ip = '127.0.0.1'
server_port = 9033

multicast_ip = '224.1.2.3'
multicast_port = 9000


def tcp_receive(tcp_socket: socket.socket):
    buffer_message = ''
    while True:
        message = tcp_socket.recv(1000)

        if message == b'':
            yield None

        buffer_message += message.decode()

        while True:
            newline_index = buffer_message.find('\n')

            if newline_index != -1:
                yield buffer_message[:newline_index]
                buffer_message = '' if newline_index == len(buffer_message) - 1 else buffer_message[newline_index + 1:]
            else:
                break
