package sr.ice.server;

import SmartHome.RadioSpeakerI;
import SmartHome.RadioStation;
import com.zeroc.Ice.Current;

public class RadioSpeaker extends Speaker implements RadioSpeakerI {

  private RadioStation station;

  public RadioSpeaker(String deviceName) {
    super(deviceName);
  }

  @Override
  public void setStation(RadioStation station, Current current) {
    this.station = station;
    System.out.println(deviceName + " | " + "Set station: " + this.station.name());
  }
}
