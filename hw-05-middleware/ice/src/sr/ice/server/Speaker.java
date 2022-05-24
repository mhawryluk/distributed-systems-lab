package sr.ice.server;

import SmartHome.SpeakerI;
import com.zeroc.Ice.Current;

public class Speaker implements SpeakerI {
  @Override
  public int getVolume(Current current) {
    return 0;
  }

  @Override
  public void volumeUp(int step, Current current) {

  }

  @Override
  public void volumeDown(int step, Current current) {

  }
}
