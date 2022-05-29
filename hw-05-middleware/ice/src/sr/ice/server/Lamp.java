package sr.ice.server;

import SmartHome.Color;
import SmartHome.InvalidColorException;
import SmartHome.LampI;
import com.zeroc.Ice.Current;

public class Lamp implements LampI {

  private Color color;
  private final String deviceName;

  public Lamp(String deviceName) {
    this.deviceName = deviceName;
  }

  @Override
  public Color getColor(Current current) {
    return color;
  }

  @Override
  public void setColor(Color color, Current current) throws InvalidColorException {
    if (color.R > 255 || color.R < 0 || color.G < 0 || color.G > 255 || color.B < 0 || color.B > 255)
      throw new InvalidColorException();

    this.color = color;
    System.out.println(deviceName + " | " + "Color set: " + colorToString(color));
  }

  private String colorToString(Color color) {
    return "(" + color.R + ", " + color.G + ", " + color.B + ")";
  }
}
