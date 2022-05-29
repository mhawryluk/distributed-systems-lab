package sr.ice.server;

import SmartHome.SpeakerI;
import com.zeroc.Ice.Current;

public class Speaker implements SpeakerI {

  protected final String deviceName;
  private int volume;
  private final int maxVolume = 10;
  private final int minVolume = 0;

  public Speaker(String deviceName) {
    this.deviceName = deviceName;
  }

  @Override
  public int getVolume(Current current) {
    return volume;
  }

  @Override
  public void volumeChange(int delta, Current current) {
    volume += delta;
    volume = Math.max(volume, minVolume);
    volume = Math.min(volume, maxVolume);

    System.out.println(deviceName + " | " + "New volume: " + volume);
  }
}
