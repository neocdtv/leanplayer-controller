package io.neocdtv.player.ui.control;

import io.neocdtv.player.ui.model.Playlist;
import io.neocdtv.player.ui.model.PlaylistEntry;
import io.neocdtv.player.ui.model.PlaylistSelection;
import io.neocdtv.player.ui.model.RendererList;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * PlaylistController.
 *
 * @author xix
 * @since 11.03.18
 */

@ApplicationScoped
public class PlaylistController {

  @Inject
  private Playlist playlist;

  @Inject
  private PlaylistSelection playlistSelection;

  @Inject
  private RendererList rendererList;

  @Inject
  private Event<NextPlaylistTrack> nextPlaylistTrackEvent;

  public void onTrackEndedThrownByRenderer(@Observes TrackEndedEvent trackEndedEvent) {
    PlaylistEntry nextPlaylistEntry = playlist.getNextPlaylistEntry();
    nextPlaylistTrackEvent.fire(
        NextPlaylistTrack.create(
            nextPlaylistEntry.getPath()));
  }
}