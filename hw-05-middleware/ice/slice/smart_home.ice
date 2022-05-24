
#ifndef CALC_ICE
#define CALC_ICE

module SmartHome
{
    exception NoInput {};

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

    interface Speaker {
        int getVolume();
        void volumeUp();
        void volumeDown();
    };

    interface Lamp {
        Color getColor();
        void setColor();
    };
};

#endif
