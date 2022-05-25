package sr.ice.server;

import SmartHome.SpeakerI;
import com.zeroc.Ice.Current;

public class Speaker implements SpeakerI {

  private int volume;
  private final int maxVolume = 10;
  private final int minVolume = 0;

  @Override
  public int getVolume(Current current) {
    return 0;
  }

  @Override
  public void volumeChange(int delta, Current current) {
    volume += delta;
    volume = Math.max(volume, minVolume);
    volume = Math.min(volume, maxVolume);

    System.out.println("New volume: " + volume);
  }
}
