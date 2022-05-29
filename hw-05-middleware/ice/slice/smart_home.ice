module SmartHome {

    interface Device {};

    // SPEAKERS

    enum RadioStation {
        POP,
        ROCK,
        LATIN
    };

    interface SpeakerI extends Device {
        int getVolume();
        void volumeChange(int delta);
    };

    interface RadioSpeakerI extends SpeakerI {
        void setStation(RadioStation station);
    };

    struct Song {
        string artist;
        string title;
    };

    interface BTSpeakerI extends SpeakerI {
        void setSong(Song song);
    };

    // LAMPS

    exception InvalidColorException {};
    struct Color {
        int R;
        int G;
        int B;
    };

    interface LampI extends Device {
        Color getColor();
        void setColor(Color color) throws InvalidColorException;
    };


    // CAMERAS

    sequence <int> Row;
    sequence <Row> Image;

    struct Resolution {
        int width;
        int height;
    };

    exception InvalidResolutionException {};

    interface CameraI extends Device {
        Image getSnapshot();
        void setResolution(Resolution resolution) throws InvalidResolutionException;
    };

    // Home Info

    struct DeviceId {
        string name;
        string category;
        int serverPort;
    };

    sequence <DeviceId> devicesList;

    interface HomeInfoI {
        devicesList listDevices();
    };
};
