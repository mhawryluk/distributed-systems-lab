package sr.ice.server;

import SmartHome.Color;
import SmartHome.InvaildColorException;
import SmartHome.LampI;
import com.zeroc.Ice.Current;

public class Lamp implements LampI {
  @Override
  public Color getColor(Current current) {
    return null;
  }

  @Override
  public void setColor(Color color, Current current) throws InvaildColorException {

  }
}
