package io.neocdtv.player.ui.control.worker;

import io.neocdtv.player.ui.model.Playlist;
import io.neocdtv.player.ui.model.RendererList;
import io.neocdtv.player.ui.model.RendererListEntry;

import javax.inject.Inject;

/**
 * Next.
 *
 * @author xix
 * @since 07.03.18
 */
public class SavePlaylist {

  @Inject
  private Playlist playlist;

  public void execute() {
    final SavePlaylistWorker worker = new SavePlaylistWorker();
    worker.setPlaylist(playlist);
    worker.execute();
  }
}
