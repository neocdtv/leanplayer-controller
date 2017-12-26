/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.simpleplayer.ui;

import io.neocdtv.simpleplayer.player.Player;

/**
 * @author xix
 */
public class CombolistEntry {
  private final String value;
  private final Player player;

  public CombolistEntry(String value, Player player) {
    this.value = value;
    this.player = player;
  }

  public String getValue() {
    return value;
  }

  public Player getPlayer() {
    return player;
  }

  @Override
  public String toString() {
    return value;
  }
}
