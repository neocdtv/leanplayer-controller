/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.simpleplayer.player;

import io.neocdtv.simpleplayer.ui.ComboBoxFactory;
import io.neocdtv.simpleplayer.ui.CombolistEntry;

/**
 * @author xix
 */
public class PlayerFactory {

  private PlayerFactory() {
  }

  public static Player getCurrentPlayer() {
    final CombolistEntry entry = (CombolistEntry) ComboBoxFactory.instance().getSelectedItem();
    return entry.getPlayer();
  }
}
