/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.simpleplayer.ui;

import javax.swing.DefaultComboBoxModel;

/**
 * @author xix
 */
public class ComboBoxFactory {

  private static DefaultComboBoxModel<CombolistEntry> INSTANCE;

  private ComboBoxFactory() {
  }

  public static DefaultComboBoxModel<CombolistEntry> instance() {
    if (INSTANCE == null) {
      INSTANCE = new DefaultComboBoxModel<>();
    }
    return INSTANCE;
  }
}
