package io.neocdtv.player.ui.model;

import javax.enterprise.context.ApplicationScoped;
import javax.swing.*;
import java.util.UUID;

/**
 * PlaylistSelection.
 *
 * @author xix
 * @since 19.03.18
 */

@ApplicationScoped
public class PlaylistSelection extends DefaultListSelectionModel {

  // Preserves currently playing track, even if the user browses through the playlist
  private UUID currentPlayingUUID;

  public UUID getCurrentPlayingUUID() {
    return currentPlayingUUID;
  }

  public void setCurrentPlayingUuid(UUID currentPlayingUUID) {
    this.currentPlayingUUID = currentPlayingUUID;
  }
}
