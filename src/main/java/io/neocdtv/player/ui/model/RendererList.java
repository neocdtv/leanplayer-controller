package io.neocdtv.player.ui.model;

import javax.enterprise.context.ApplicationScoped;
import javax.swing.*;

/**
 * PlayerSelectionList.
 *
 * @author xix
 * @since 07.03.18
 */

@ApplicationScoped
public class RendererList {

  private DefaultComboBoxModel<RendererListEntry> model = new DefaultComboBoxModel<>();

  public DefaultComboBoxModel<RendererListEntry> getModel() {
    return model;
  }
}
