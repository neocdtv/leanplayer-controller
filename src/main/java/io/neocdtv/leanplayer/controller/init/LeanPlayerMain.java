/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.leanplayer.controller.init;

import io.neocdtv.leanplayer.controller.ui.ComboBoxFactory;
import io.neocdtv.leanplayer.controller.ui.PlayerUI;
import io.neocdtv.service.StreamingService;
import io.neocdtv.leanplayer.controller.player.LeanPlayer;
import io.neocdtv.leanplayer.controller.ui.ComboListEntry;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.events.ContainerInitialized;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.swing.*;
import java.util.logging.Logger;

import static io.neocdtv.service.StreamingServiceConstants.DEFAULT_SERVICE_PORT;

/**
 * ControllerUI.
 *
 * @author xix
 * @since 22.12.17
 */
public class LeanPlayerMain {
  private final static Logger LOGGER = Logger.getLogger(LeanPlayerMain.class.getName());

  @Inject
  private io.neocdtv.leanplayer.controller.init.LookAndFeel lookAndFeel;

  @Inject
  private PlayerUI playerUI;

  @Inject
  private DeviceDiscovery deviceDiscovery;

  public static void main(String[] args) throws Exception {
    configureCdi();
  }

  public void main(@Observes ContainerInitialized event) throws Exception {
    LeanPlayerMain leanPlayerMain = new LeanPlayerMain();
    lookAndFeel.init();
    playerUI.init();
    leanPlayerMain.configureAndStartStreamingService();
    deviceDiscovery.init();
  }

  public void onDeviceDiscovery(@Observes DeviceDiscoveredEvent event) throws Exception {
    final DefaultComboBoxModel<ComboListEntry> comboBoxModel = ComboBoxFactory.instance();
    //
    comboBoxModel.addElement(new ComboListEntry(event.getDeviceName(), new LeanPlayer(event.getDeviceAddress())));
  }

  private static void configureCdi() {
    Weld weld = new Weld();
    weld.initialize();
  }
  private void configureAndStartStreamingService() throws Exception {
    StreamingService.start(DEFAULT_SERVICE_PORT);
  }
}
