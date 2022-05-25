import Ice
import sys
from SmartHome import LampIPrx, Color, RadioSpeakerIPrx, CameraIPrx, BTSpeakerIPrx, LampI
from config import server_config, devices

stubs = {}


def get_stub(communicator, name):
    if name in stubs:
        return stubs[name]

    server = server_config[devices[name]["server"]]
    category = devices[name]["category"]

    base = communicator.stringToProxy(f"{category}/{name}: {server}")

    match category:
        case 'lamp':
            return LampIPrx.checkedCast(base)
        case 'radio_speaker':
            return RadioSpeakerIPrx.checkedCast(base)
        case 'bt_speaker':
            return BTSpeakerIPrx.checkedCast(base)
        case 'camera':
            return CameraIPrx.checkedCast(base)


def lamp_handle(communicator, name):
    stub: LampI = get_stub(communicator, name)
    stub.setColor(Color(255, 128, 5))
    print(stub.getColor())


def radio_speaker_handle(communicator, name):
    pass


def bt_speaker_handle(communicator, name):
    pass

def camera_handle(communicator, name):
    pass


def main():
    with Ice.initialize(sys.argv) as communicator:
        while True:
            try:
                device_name = input("device: ")
            except KeyboardInterrupt:
                break

            if device_name not in devices:
                print(f"\"{device_name}\" is unavailable")
                continue

            match devices[device_name]["category"]:
                case 'lamp':
                    lamp_handle(communicator, device_name)
                case 'radio_speaker':
                    radio_speaker_handle(communicator, device_name)
                case 'bt_speaker':
                    bt_speaker_handle(communicator, device_name)
                case 'camera':
                    camera_handle(communicator, device_name)


if __name__ == '__main__':
    main()
