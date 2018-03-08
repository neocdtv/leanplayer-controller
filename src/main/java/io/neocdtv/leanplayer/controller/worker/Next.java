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
public class Next {

  @Inject
  private Playlist playlist;

  public void execute() {
    final NextWorker nextWorker = new NextWorker();
    nextWorker.setPlayer(PlayerFactory.getCurrentPlayer());
    nextWorker.setPlaylist(playlist);
    nextWorker.execute();
  }
}
