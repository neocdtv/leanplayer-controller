/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.leanplayer.controller.worker;

import io.neocdtv.leanplayer.controller.player.Player;
import io.neocdtv.leanplayer.controller.player.PlayerException;
import io.neocdtv.leanplayer.controller.player.PlayerFactory;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PauseWorker.
 *
 * @author xix
 * @since 22.12.17
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
