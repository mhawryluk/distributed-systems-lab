package sr.ice.server;

import SmartHome.Color;
import SmartHome.InvalidColorException;
import SmartHome.LampI;
import com.zeroc.Ice.Current;

public class Lamp implements LampI {
  @Override
  public Color getColor(Current current) {
    return null;
  }

  @Override
  public void setColor(Color color, Current current) throws InvalidColorException {
    System.out.println("color set: " + color.R + " " + color.G + " " + color.B);
  }
}
