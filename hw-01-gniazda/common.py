import socket

server_ip = '127.0.0.1'
server_port = 9033

multicast_ip = '224.1.2.3'
multicast_port = 9000


def tcp_receive(tcp_socket: socket.socket, length: int):
    data = bytearray()
    while len(data) < length:
        received = tcp_socket.recv(length - len(data))
        if received == b'':
            return b''
        data.extend(received)
    return data.decode()
