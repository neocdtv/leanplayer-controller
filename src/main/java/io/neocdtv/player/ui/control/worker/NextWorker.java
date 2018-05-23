/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.player.ui.control.worker;

import io.neocdtv.player.ui.control.Player;
import io.neocdtv.player.ui.model.Playlist;
import io.neocdtv.player.ui.model.PlaylistEntry;
import io.neocdtv.service.UrlBuilder;

import javax.swing.*;
import java.util.logging.Logger;

/**
 * NextWorker.
 *
 * @author xix
 * @since 22.12.17
 */
public class NextWorker extends SwingWorker<Void, Void> {

  private final static Logger LOGGER = Logger.getLogger(NextWorker.class.getName());

  private Playlist playlist;
  private Player player;

  @Override
  protected Void doInBackground() {
    final PlaylistEntry nextPlaylistEntry = playlist.getNextPlaylistEntry();
    String url = UrlBuilder.build(nextPlaylistEntry.getPath(), player.getAddress());
    player.play(url);
    return null;
  }

  public void setPlaylist(Playlist playlist) {
    this.playlist = playlist;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }
}