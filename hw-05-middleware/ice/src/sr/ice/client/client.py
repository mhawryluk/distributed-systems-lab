import ICE

if __name__ == '__main__':
    with Ice.initialize(sys.argv) as communicator:
        while True:
            command = input('==> ')