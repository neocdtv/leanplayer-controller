package io.neocdtv.leanplayer.controller.ui;

import javax.enterprise.context.ApplicationScoped;
import javax.swing.*;

/**
 * PlayerSelectionList.
 *
 * @author xix
 * @since 07.03.18
 */

@ApplicationScoped
public class PlayerSelectionList {

  private DefaultComboBoxModel<PlayerSelectionEntry> model;

  public PlayerSelectionList() {
    this.model = PlayerSelectionListFactory.instance();
  }

  public DefaultComboBoxModel<PlayerSelectionEntry> getModel() {
    return model;
  }
}
