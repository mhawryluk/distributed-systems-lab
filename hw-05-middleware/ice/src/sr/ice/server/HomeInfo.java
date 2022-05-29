package sr.ice.server;

import SmartHome.DeviceId;
import SmartHome.HomeInfoI;
import com.zeroc.Ice.Current;

public class HomeInfo implements HomeInfoI {

  private static DeviceId[] devices;

  public static void setDevices(DeviceId[] devices) {
    HomeInfo.devices = devices;
  }

  @Override
  public DeviceId[] listDevices(Current current) {
    return devices;
  }
}
