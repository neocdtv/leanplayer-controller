/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.leanplayer.controller.ui;

import javax.swing.DefaultComboBoxModel;

/**
 * ComboBoxFactory.
 *
 * @author xix
 * @since 22.12.17
 */
public class ComboBoxFactory {

  private static DefaultComboBoxModel<ComboListEntry> INSTANCE;

  private ComboBoxFactory() {
  }

  public static DefaultComboBoxModel<ComboListEntry> instance() {
    if (INSTANCE == null) {
      INSTANCE = new DefaultComboBoxModel<>();
    }
    return INSTANCE;
  }
}
