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

  public PlaylistEntry getSelectedPlaylistEntry() {
    LOGGER.log(Level.INFO, "getting track url for current playlist entry...");
    final int selectedIndex = playlistSelection.getMinSelectionIndex();
    LOGGER.log(Level.FINE, "selected index {0} ", selectedIndex);
    PlaylistEntry selectedPlaylistEntry = selectedPlaylistEntry = get(selectedIndex);
    LOGGER.log(Level.FINE, "selected playlist track {0}", selectedPlaylistEntry.getPath());
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