/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.simpleplayer.worker;

import io.neocdtv.simpleplayer.player.Player;
import io.neocdtv.simpleplayer.player.PlayerFactory;
import io.neocdtv.simpleplayer.ui.PlaylistUI;

import javax.swing.*;
import java.util.logging.Logger;

/**
 * @author xix
 */
public class NextWorker extends SwingWorker<Void, Void> {

  private final static Logger LOGGER = Logger.getLogger(NextWorker.class.getName());

  @Override
  protected Void doInBackground() {
    final String url = PlaylistUI.getInstance().getNextTrackUrl();
    final Player currentPlayer = PlayerFactory.getCurrentPlayer();
    currentPlayer.play(url);
    return null;
  }
}
