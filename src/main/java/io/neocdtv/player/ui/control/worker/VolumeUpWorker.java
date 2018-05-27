/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.player.ui.control.worker;

import io.neocdtv.player.ui.control.Player;

import javax.swing.*;
import java.util.logging.Logger;

/**
 * PlayWorker.
 *
 * @author xix
 * @since 22.12.17
 */
public class VolumeUpWorker extends SwingWorker<Void, Void> {

  private final static Logger LOGGER = Logger.getLogger(VolumeUpWorker.class.getName());

  private Player player;

  @Override
  protected Void doInBackground() {
    LOGGER.info("Volume up");
    player.volumeUp();
    return null;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }
}