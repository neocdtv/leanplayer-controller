/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.leanplayer.controller.ui;

import io.neocdtv.leanplayer.controller.player.Player;

/**
 * CombolistEntry.
 *
 * @author xix
 * @since 22.12.17
 */
public class ComboListEntry {
  private final String value;
  private final Player player;

  public ComboListEntry(String value, Player player) {
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
