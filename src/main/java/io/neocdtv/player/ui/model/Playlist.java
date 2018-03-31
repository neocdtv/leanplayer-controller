package io.neocdtv.player.ui.model;

import io.neocdtv.service.UrlBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Playlist.
 *
 * @author xix
 * @since 09.03.18
 */

@ApplicationScoped
public class Playlist extends DefaultListModel<PlaylistEntry> {

  private final static Logger LOGGER = Logger.getLogger(Playlist.class.getName());

  @Inject
  private PlaylistSelection playlistSelection;

  public Playlist() {
    justForTests();
  }

  private void justForTests() {
    add(0, PlaylistEntry.create("272.mp3", UrlBuilder.build("/music/272.mp3")));
    add(1, PlaylistEntry.create("273.mp3", UrlBuilder.build("/music/273.mp3")));
    add(2, PlaylistEntry.create("1.mp4", UrlBuilder.build("/music/1.mp4")));
  }

  public PlaylistEntry getSelectedPlaylistEntry() {
    LOGGER.log(Level.INFO, "getting track url for current playlist entry...");
    PlaylistEntry selectedPlaylistEntry = null;
    final int selectedIndex = playlistSelection.getMinSelectionIndex();
    LOGGER.log(Level.INFO, "selected index {0} ", selectedIndex);
    selectedPlaylistEntry = get(selectedIndex);
    LOGGER.log(Level.INFO, "selected playlist track {0}", selectedPlaylistEntry.getPath());
    return selectedPlaylistEntry;
  }

  public PlaylistEntry getNextPlaylistEntry() {
    int currentPlayingIndex = playlistSelection.getMinSelectionIndex();
    LOGGER.log(Level.INFO, "getting track url for next playlist entry...");
    PlaylistEntry nextPlaylistEntry = null;
    if (getSize() > 0 && currentPlayingIndex >= 0) {

      currentPlayingIndex++;
      // no next entry, so start from the beginning
      if (currentPlayingIndex >= getSize()) {
        currentPlayingIndex = 0;
      }
      playlistSelection.setSelectionInterval(currentPlayingIndex, currentPlayingIndex);
      nextPlaylistEntry = get(currentPlayingIndex);
    }
    return nextPlaylistEntry;
  }
}