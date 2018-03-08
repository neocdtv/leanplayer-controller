package io.neocdtv.leanplayer.controller.worker;

import io.neocdtv.leanplayer.controller.player.PlayerFactory;
import io.neocdtv.leanplayer.controller.ui.Playlist;

import javax.inject.Inject;

/**
 * Next.
 *
 * @author xix
 * @since 07.03.18
 */
public class Play {

  @Inject
  private Playlist playlist;

  public void execute() {
    final PlayWorker playWorker = new PlayWorker();
    playWorker.setPlayer(PlayerFactory.getCurrentPlayer());
    playWorker.setPlaylist(playlist);
    playWorker.execute();
  }
}
