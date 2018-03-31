package io.neocdtv.player.ui.control.worker;


import io.neocdtv.player.ui.model.RendererList;
import io.neocdtv.player.ui.model.RendererListEntry;

import javax.inject.Inject;

/**
 * Next.
 *
 * @author xix
 * @since 07.03.18
 */
public class VolumeDown {

  @Inject
  private RendererList rendererList;

  public void execute() {
    final VolumeDownWorker worker = new VolumeDownWorker();
    worker.setPlayer(((RendererListEntry) rendererList.getModel().getSelectedItem()).getPlayer());
    worker.execute();
  }
}
