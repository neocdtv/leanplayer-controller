package io.neocdtv.leanplayer.controller.worker;

import io.neocdtv.leanplayer.controller.player.PlayerFactory;

/**
 * Pause.
 *
 * @author xix
 * @since 08.03.18
 */
public class Pause {

  public void execute() {
    final PauseWorker pauseWorker = new PauseWorker();
    pauseWorker.setPlayer(PlayerFactory.getCurrentPlayer());
    pauseWorker.execute();
  }
}
