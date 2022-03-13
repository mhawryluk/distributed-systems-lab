from threading import Thread
import socket
from common import tcp_receive


class Client(Thread):
    current_id = 0

    def __init__(self, tcp_socket: socket.socket, address: tuple, clients: dict):
        super().__init__(daemon=True)
        self.id = Client.current_id
        Client.current_id += 1
        self.socket = tcp_socket
        self.address = address
        self.clients = clients

    def run(self):
        try:
            for message in tcp_receive(self.socket):
                if message is None:
                    self.socket.close()
                    print(f'client #{self.id} disconnected')
                    del self.clients[self.address]
                    return

                for client in self.clients.values():
                    if client != self:
                        client.socket.sendall(
                            f'#{client.id}: {message}\n'.encode())

        except (KeyboardInterrupt, OSError):
            pass
        finally:
            self.socket.close()
