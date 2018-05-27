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

  public boolean contains(final String id) {
    final int size = model.getSize();
    for (int index = 0; index < size; index++) {
      final RendererListEntry elementAt = model.getElementAt(index);
      if (elementAt.getId().equals(id)) {
        return true;
      }
    }
    return false;
  }

  public void remove(final String id) {
    final int size = model.getSize();
    for (int index = 0; index < size; index++) {
      final RendererListEntry elementAt = model.getElementAt(index);
      if (elementAt.getId().equals(id)) {
        model.removeElementAt(index);
      }
    }
  }
}
