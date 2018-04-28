package io.neocdtv.player.ui.model;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.swing.*;
import java.util.UUID;
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
    if (getSize() > 0) {
      if (playlistSelection.getCurrentPlayingUUID() == null) {
        final PlaylistEntry playlistEntry = get(0);
        playlistSelection.setCurrentPlayingUuid(playlistEntry.getUuid());
      }
      final int currentPlayingIndex = findEntryByUuid(playlistSelection.getCurrentPlayingUUID());
      playlistSelection.setSelectionInterval(currentPlayingIndex, currentPlayingIndex);
      LOGGER.log(Level.FINE, "selected index {0} ", currentPlayingIndex);
      PlaylistEntry selectedPlaylistEntry = selectedPlaylistEntry = get(currentPlayingIndex);
      LOGGER.log(Level.FINE, "selected playlist track {0}", selectedPlaylistEntry.getPath());
      return selectedPlaylistEntry;
    }
    return null;
  }

  public PlaylistEntry getNextPlaylistEntry() {
    LOGGER.log(Level.INFO, "getting track url for next playlist entry...");
    PlaylistEntry nextPlaylistEntry = null;
    int currentPlayingIndex = findEntryByUuid(playlistSelection.getCurrentPlayingUUID());
    if (getSize() > 0 && currentPlayingIndex >= 0) {
      currentPlayingIndex++;
      // no next entry, so start from the beginning
      if (currentPlayingIndex >= getSize()) {
        currentPlayingIndex = 0;
      }
      playlistSelection.setSelectionInterval(currentPlayingIndex, currentPlayingIndex);
      nextPlaylistEntry = get(currentPlayingIndex);
      playlistSelection.setCurrentPlayingUuid(nextPlaylistEntry.getUuid());
    }
    return nextPlaylistEntry;
  }

  public int findEntryByUuid(final UUID uuid) {
    for (int i = 0; i < getSize(); i++) {
      final PlaylistEntry playlistEntry = get(i);
      if (playlistEntry.getUuid().equals(uuid)) {
        return i;
      }
    }
    throw new RuntimeException("Couldn't find playlist entry with UUID: " + uuid.toString());
  }
}