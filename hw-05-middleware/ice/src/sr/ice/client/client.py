import Ice
import sys
from SmartHome import LampIPrx, Color

if __name__ == '__main__':
    with Ice.initialize(sys.argv) as communicator:
        base = communicator.stringToProxy("{}tcp -h 127.0.0.1 -p 10000 -z : udp -h 127.0.0.1 -p 10000 -z".format("lamp/lamp1"))
        stub = LampIPrx.checkedCast(base)
        stub.setColor(Color(255, 255, 255))
