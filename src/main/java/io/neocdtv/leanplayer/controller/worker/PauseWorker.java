/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.leanplayer.controller.worker;

import io.neocdtv.leanplayer.controller.player.Player;

import javax.swing.*;
import java.util.logging.Logger;

/**
 * PauseWorker.
 *
 * @author xix
 * @since 22.12.17
 */
public class PauseWorker extends SwingWorker<Void, Void> {

  private final static Logger LOGGER = Logger.getLogger(PauseWorker.class.getName());

  private Player player;

  @Override
  protected Void doInBackground() {
    player.pause();
    return null;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }
}
