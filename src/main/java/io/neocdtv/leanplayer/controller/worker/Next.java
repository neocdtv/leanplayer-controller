package io.neocdtv.leanplayer.controller.worker;

import io.neocdtv.leanplayer.controller.player.PlayerFactory;
import io.neocdtv.leanplayer.controller.ui.PlaylistUI;

/**
 * Next.
 *
 * @author xix
 * @since 07.03.18
 */
public class Next {

  public void execute() {
    final NextWorker nextWorker = new NextWorker();
    nextWorker.setPlayer(PlayerFactory.getCurrentPlayer());
    nextWorker.setPlaylistUI(PlaylistUI.getInstance());
    nextWorker.execute();
  }
}
