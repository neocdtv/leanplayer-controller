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

  public PlaylistEntry getCurrentPlaylistEntry() {
    LOGGER.log(Level.INFO, "getting track url for current playlist entry...");
    if (getSize() > 0) {
      if (isPlaylistEntrySelected()) {
        final int selectedIndex = playlistSelection.getMinSelectionIndex();
        PlaylistEntry currentPlaylistEntry = get(selectedIndex);
        playlistSelection.setCurrentPlayingUuid(currentPlaylistEntry.getUuid());
        return currentPlaylistEntry;
      } else {
        return initCurrentPlayingUuidIfNotAvailable();
      }
    }
    throw new RuntimeException("Cannot determine current playlist entry!");
  }

  private boolean isPlaylistEntrySelected() {
    return playlistSelection.getMinSelectionIndex() >= 0;
  }

  private PlaylistEntry initCurrentPlayingUuidIfNotAvailable() {
    PlaylistEntry playlistEntry = null;
    if (playlistSelection.getCurrentPlayingUUID() == null) {
      final int firstPlaylistEntry = 0;
      playlistEntry = get(firstPlaylistEntry);
      playlistSelection.setCurrentPlayingUuid(playlistEntry.getUuid());
      playlistSelection.setSelectionInterval(firstPlaylistEntry, firstPlaylistEntry);
    }
    return playlistEntry;
  }

  /*
    Calculates next playlist entry. The starting point for the calculation is the current playing entry
    , which preserved in playlistSelection.getCurrentPlayingUUID.
   */
  public PlaylistEntry getNextPlaylistEntry() {
    LOGGER.log(Level.INFO, "getting track url for next playlist entry...");

    if (getSize() > 0) {
      final PlaylistEntry newlyInitialized = initCurrentPlayingUuidIfNotAvailable();
      if (newlyInitialized != null) {
        return newlyInitialized;
      }

      int currentPlayingIndex = findEntryIndexByUuid(playlistSelection.getCurrentPlayingUUID());
      currentPlayingIndex++;
      // no next entry, so start from the beginning
      if (currentPlayingIndex >= getSize()) {
        currentPlayingIndex = 0;
      }
      playlistSelection.setSelectionInterval(currentPlayingIndex, currentPlayingIndex);
      final PlaylistEntry nextPlaylistEntry = get(currentPlayingIndex);
      playlistSelection.setCurrentPlayingUuid(nextPlaylistEntry.getUuid());
      LOGGER.log(Level.INFO, "next playlist track {0}", nextPlaylistEntry.getPath());

      return nextPlaylistEntry;
    }
    throw new RuntimeException("Cannot determine next playlist entry!");
  }

  public int findEntryIndexByUuid(final UUID uuid) {
    for (int i = 0; i < getSize(); i++) {
      final PlaylistEntry playlistEntry = get(i);
      if (playlistEntry.getUuid().equals(uuid)) {
        return i;
      }
    }
    throw new RuntimeException("Couldn't find playlist entry with UUID: " + uuid.toString());
  }

  @Override
  public boolean removeElement(final Object object) {
    final PlaylistEntry playlistEntry = (PlaylistEntry) object;
    if (playlistEntry.getUuid().equals(playlistSelection.getCurrentPlayingUUID())) {
      // TODO: define an algorithm, which will selected the next best entry.
      // The current solution will select first entry from the playlist.
      playlistSelection.setCurrentPlayingUuid(null);
    }


    final boolean removed = super.removeElement(object);
    return removed;
  }
}