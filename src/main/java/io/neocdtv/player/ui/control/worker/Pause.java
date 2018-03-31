package io.neocdtv.player.ui.control.worker;


import io.neocdtv.player.ui.model.RendererList;
import io.neocdtv.player.ui.model.RendererListEntry;

import javax.inject.Inject;

/**
 * Pause.
 *
 * @author xix
 * @since 08.03.18
 */
public class Pause {

  @Inject
  private RendererList rendererList;

  public void execute() {
    final PauseWorker pauseWorker = new PauseWorker();
    pauseWorker.setPlayer(((RendererListEntry) rendererList.getModel().getSelectedItem()).getPlayer());
    pauseWorker.execute();
  }
}
