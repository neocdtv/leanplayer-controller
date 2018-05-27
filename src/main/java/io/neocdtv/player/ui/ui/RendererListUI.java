package io.neocdtv.player.ui.ui;

import io.neocdtv.player.ui.model.RendererList;
import io.neocdtv.player.ui.model.RendererListEntry;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.swing.*;

/**
 * RendererListUI.
 *
 * @author xix
 * @since 11.03.18
 */
public class RendererListUI extends JComboBox<RendererListEntry> {

  @Inject
  private RendererList rendererList;

  @PostConstruct
  public void init() {
    setModel(rendererList.getModel());
  }
}
