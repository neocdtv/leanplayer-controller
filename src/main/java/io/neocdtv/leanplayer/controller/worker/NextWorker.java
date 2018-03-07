/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.leanplayer.controller.worker;

import io.neocdtv.leanplayer.controller.player.Player;
import io.neocdtv.leanplayer.controller.ui.PlaylistUI;

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

  private PlaylistUI playlistUI;
  private Player player;

  @Override
  protected Void doInBackground() {
    final String url = playlistUI.getNextTrackUrl();
    player.play(url);
    return null;
  }

  public void setPlaylistUI(PlaylistUI playlistUI) {
    this.playlistUI = playlistUI;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }
}