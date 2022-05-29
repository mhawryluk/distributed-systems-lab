package sr.ice.server;

import SmartHome.BTSpeakerI;
import SmartHome.Song;
import com.zeroc.Ice.Current;

public class BTSpeaker extends Speaker implements BTSpeakerI {

  private Song song;

  public BTSpeaker(String deviceName) {
    super(deviceName);
  }

  @Override
  public void setSong(Song song, Current current) {
    this.song = song;
    System.out.println(deviceName + " | " + "Song set: " + songToString(this.song));

  }

  private String songToString(Song song) {
    return song.artist + " - " + song.title;
  }
}
