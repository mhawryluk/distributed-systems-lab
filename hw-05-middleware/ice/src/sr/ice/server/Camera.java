package sr.ice.server;

import SmartHome.CameraI;
import SmartHome.InvalidResolutionException;
import SmartHome.Resolution;
import com.zeroc.Ice.Current;

public class Camera implements CameraI {

  @Override
  public int[][] getSnapshot(Current current) {
    return new int[0][];
  }

  @Override
  public void setResolution(Resolution resolution, Current current) throws InvalidResolutionException {

  }
}
