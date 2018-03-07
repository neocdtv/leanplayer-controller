/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.leanplayer.controller.player;

import io.neocdtv.leanplayer.controller.ui.PlayerSelectionListFactory;
import io.neocdtv.leanplayer.controller.ui.PlayerSelectionEntry;

/**
 * PlayerFactory.
 *
 * @author xix
 * @since 22.12.17
 */
public class PlayerFactory {

  private PlayerFactory() {
  }

  public static Player getCurrentPlayer() {
    final PlayerSelectionEntry entry = (PlayerSelectionEntry) PlayerSelectionListFactory.instance().getSelectedItem();
    return entry.getPlayer();
  }
}
