package io.neocdtv.player.ui.control;

import io.neocdtv.player.ui.discovery.RendererDiscoveryEvent;
import io.neocdtv.player.ui.model.RendererList;
import io.neocdtv.player.ui.model.RendererListEntry;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * RendererListController.
 *
 * @author xix
 * @since 11.03.18
 */
@ApplicationScoped
public class RendererListController {

  @Inject
  private RendererList rendererList;

  public void onRendererDiscovered(@Observes RendererDiscoveryEvent rendererDiscoveryEvent) {
    rendererList.getModel().addElement(RendererListEntry.fromEvent(rendererDiscoveryEvent));
  }
}
