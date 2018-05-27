package io.neocdtv.player.ui.control;

/**
 * NextPlaylistTrack.
 *
 * @author xix
 * @since 19.03.18
 */
public class NextPlaylistTrack {
  private String path;

  private NextPlaylistTrack(final String path) {
    this.path = path;
  }

  public static NextPlaylistTrack create(final String path) {
    return new NextPlaylistTrack(path);
  }

  public String getPath() {
    return path;
  }
}
