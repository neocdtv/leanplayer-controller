/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.simpleplayer.init;

import io.neocdtv.service.StreamingService;
import io.neocdtv.simpleplayer.player.ZenPlayer;
import io.neocdtv.simpleplayer.ui.ComboBoxFactory;
import io.neocdtv.simpleplayer.ui.CombolistEntry;
import io.neocdtv.simpleplayer.ui.PlayerUI;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.events.ContainerInitialized;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.swing.*;
import java.util.logging.Logger;

import static io.neocdtv.service.StreamingServiceConstants.DEFAULT_SERVICE_PORT;

/**
 * @author xix
 */
public class ControllerUI {
  private final static Logger LOGGER = Logger.getLogger(ControllerUI.class.getName());

  @Inject
  private LookAndFeel lookAndFeel;

  @Inject
  private PlayerUI playerUI;

  @Inject
  private DeviceDiscovery deviceDiscovery;

  public static void main(String[] args) throws Exception {
    configureCdi();
  }

  public void main(@Observes ContainerInitialized event) throws Exception {
    ControllerUI controllerUI = new ControllerUI();
    lookAndFeel.init();
    playerUI.init();
    controllerUI.configureAndStartStreamingService();
    deviceDiscovery.init();
  }

  public void onDeviceDiscovery(@Observes DeviceDiscoveredEvent event) throws Exception {
    final DefaultComboBoxModel<CombolistEntry> comboBoxModel = ComboBoxFactory.instance();
    //
    comboBoxModel.addElement(new CombolistEntry(event.getDeviceName(), new ZenPlayer(event.getDeviceAddress())));
  }

  private static void configureCdi() {
    Weld weld = new Weld();
    weld.initialize();
  }
  private void configureAndStartStreamingService() throws Exception {
    StreamingService.start(DEFAULT_SERVICE_PORT);
  }
}
