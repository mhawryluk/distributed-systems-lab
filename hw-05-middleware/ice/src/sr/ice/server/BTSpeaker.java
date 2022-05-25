package sr.ice.server;

import SmartHome.BTSpeakerI;
import SmartHome.Song;
import com.zeroc.Ice.Current;

public class BTSpeaker extends Speaker implements BTSpeakerI {

  private Song song;

  @Override
  public void setSong(Song song, Current current) {
    this.song = song;
    System.out.println("Song set: " + songToString(this.song));

  }

  private String songToString(Song song) {
    return song.artist + " - " + song.title;
  }
}
