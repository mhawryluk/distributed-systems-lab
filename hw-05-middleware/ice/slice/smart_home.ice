module SmartHome {

    exception InvaildColorException {};
    struct Color {
        int R;
        int G;
        int B;
    };

    enum RadioStation {
        POP,
        ROCK,
        LATIN
    };

    interface SpeakerI {
        int getVolume();
        void volumeUp(int step);
        void volumeDown(int step);
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

    interface LampI {
        Color getColor();
        void setColor(Color color) throws InvaildColorException;
    };

    sequence <int> Row;
    sequence <Row> Image;

    struct Resolution {
        int width;
        int height;
    };

    exception InvaildResolutionException {};

    interface CameraI {
        Image getSnapshot();
        void setResolution(Resolution resolution) throws InvaildResolutionException;
    };
};
