/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.simpleplayer.worker;

import io.neocdtv.simpleplayer.player.Player;
import io.neocdtv.simpleplayer.player.PlayerException;
import io.neocdtv.simpleplayer.player.PlayerFactory;
import io.neocdtv.simpleplayer.ui.PlaylistUI;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author xix
 */
public class PauseWorker extends SwingWorker<Void, Void> {

  private final static Logger LOGGER = Logger.getLogger(PauseWorker.class.getName());

  @Override
  protected Void doInBackground() {
    try {
      final Player currentPlayer = PlayerFactory.getCurrentPlayer();
      currentPlayer.pause();

    } catch (PlayerException ex) {
      LOGGER.log(Level.SEVERE, null, ex);
    } finally {
      return null;
    }
  }
}
