import Ice
import sys
from SmartHome import HomeInfoIPrx, Resolution, Song, CameraI, LampIPrx, Color, RadioSpeakerIPrx, RadioSpeakerI, CameraIPrx, BTSpeakerIPrx, LampI, InvalidColorException, InvalidResolutionException, RadioStation
from config import server_config

stubs = {}
devices = {}

def get_device_lists(communicator):
    for port, server in server_config.items():
        base = communicator.stringToProxy(f"home_info/home_info: {server}")
        stub = HomeInfoIPrx.checkedCast(base)
        listed_devices = stub.listDevices()
        print(f"--- server: {port} ---")
        print("available devices", listed_devices)

        for device in listed_devices:
            devices[device.name] = {
                "category": device.category,
                "port": device.serverPort
            }

def get_stub(communicator, name):
    if name in stubs:
        return stubs[name]

    server = server_config[devices[name]["port"]]
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
        case _:
            raise Exception(f"Unsupported category: {category}")


def lamp_handle(communicator, name):
    stub: LampI = get_stub(communicator, name)
    command = input("command (setColor, getColor): ")

    match command:
        case 'setColor':
            try:
                r, g, b = input("r, g, b: ").split(', ')
                r = int(r)
                g = int(g)
                b = int(b)

                stub.setColor(Color(r, g, b))
            except InvalidColorException as e:
                print(e)
            except Exception:
                print("Invalid color format, must be r, g, b")

        case 'getColor':
            print(stub.getColor())
        case _:
            print('Invalid command')


def radio_speaker_handle(communicator, name):
    stub: RadioSpeakerI = get_stub(communicator, name)
    command = input("command (setStation, volumeChange, getVolume): ")

    match command:
        case 'setStation':
            try:
                station = input("station: ")
                stub.setStation(getattr(RadioStation, station))
            except Exception as e:
                print("Invalid station")
                print(e)
        case 'getVolume':
            print(stub.getVolume())
        case 'volumeChange':
            delta = int(input("delta: "))
            stub.volumeChange(delta)
        case _:
            print('Invalid command')


def bt_speaker_handle(communicator, name):
    stub: RadioSpeakerI = get_stub(communicator, name)
    command = input("command (setSong, volumeChange, getVolume): ")

    match command:
        case 'setSong':
            artist = input("artist: ")
            title = input("title: ")
            stub.setSong(Song(artist, title))
        case 'getVolume':
            print(stub.getVolume())
        case 'volumeChange':
            delta = int(input("delta: "))
            stub.volumeChange(delta)
        case _:
            print('Invalid command')


def camera_handle(communicator, name):
    stub: CameraI = get_stub(communicator, name)
    command = input("command (getSnapshot, setResolution): ")

    match command:
        case 'setResolution':
            width, height = input("resolution: width, height: ").split(", ")
            width = int(width)
            height = int(height)
            stub.setResolution(Resolution(width, height))
        case 'getSnapshot':
            print(stub.getSnapshot())
        case _:
            print('Invalid command')


def main():
    with Ice.initialize(sys.argv) as communicator:
        get_device_lists(communicator)

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
