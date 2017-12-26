/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.simpleplayer.ui;

import javax.swing.*;

/**
 * @author xix
 */
public class PlayerStateFieldFactory {

  private static JTextField INSTANCE;

  private PlayerStateFieldFactory() {
  }

  public static JTextField instance() {
    if (INSTANCE == null) {
      INSTANCE = new JTextField();
      INSTANCE.setEnabled(false);
    }
    return INSTANCE;
  }
}
