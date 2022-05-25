package sr.ice.server;

import SmartHome.CameraI;
import SmartHome.InvalidResolutionException;
import SmartHome.Resolution;
import com.zeroc.Ice.Current;

import java.util.Arrays;

public class Camera implements CameraI {

  private int width = 32;
  private int height = 16;


  @Override
  public int[][] getSnapshot(Current current) {
    int[][] image = new int[height][width];

    for (int[] row: image)
      Arrays.fill(row, 1);

    return image;
  }

  @Override
  public void setResolution(Resolution resolution, Current current) throws InvalidResolutionException {
    if (resolution.width < 0 || resolution.height < 0) throw new InvalidResolutionException();

    this.width = resolution.width;
    this.height = resolution.height;

    System.out.println("Set resolution: " + this.width + " x " + this.height);
  }
}
