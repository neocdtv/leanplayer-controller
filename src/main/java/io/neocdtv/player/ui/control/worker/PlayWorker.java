/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.player.ui.control.worker;

import io.neocdtv.player.ui.control.Player;
import io.neocdtv.player.ui.model.Playlist;
import io.neocdtv.service.UrlBuilder;

import javax.swing.*;
import java.util.logging.Logger;

/**
 * PlayWorker.
 *
 * @author xix
 * @since 22.12.17
 */
public class PlayWorker extends SwingWorker<Void, Void> {

  private final static Logger LOGGER = Logger.getLogger(PlayWorker.class.getName());

  private Playlist playlist;
  private Player player;

  @Override
  protected Void doInBackground() {
    final String path = playlist.getCurrentPlaylistEntry().getPath();
    String url = UrlBuilder.build(path, player.getAddress());
    LOGGER.info("Start playing " + url);
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