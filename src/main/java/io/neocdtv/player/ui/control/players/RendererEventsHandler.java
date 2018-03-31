package io.neocdtv.player.ui.control.players;

import io.neocdtv.player.ui.control.NextPlaylistTrack;
import io.neocdtv.player.ui.model.RendererList;
import io.neocdtv.player.ui.model.RendererListEntry;
import io.neocdtv.service.UrlBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * RendererEventsHandler.
 *
 * @author xix
 * @since 26.03.18
 */
@ApplicationScoped
public class RendererEventsHandler {

  @Inject
  private RendererList rendererList;

  public void onTrackEnded(@Observes NextPlaylistTrack event) {
    final String url = UrlBuilder.build(event.getPath());
    ((RendererListEntry) rendererList.getModel().getSelectedItem()).getPlayer().play(url);
  }
}