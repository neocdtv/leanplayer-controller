package io.neocdtv.leanplayer.controller.init;

import io.neocdtv.leanplayer.controller.player.LeanPlayer;
import io.neocdtv.leanplayer.controller.ui.ComboBoxFactory;
import io.neocdtv.leanplayer.controller.ui.ComboListEntry;
import io.neocdtv.leanplayer.controller.ui.PlayerUI;
import io.neocdtv.service.StreamingService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.events.ContainerInitialized;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.swing.*;

/**
 * LeanPlayerControllerMain.
 *
 * @author xix
 * @since 22.12.17
 */
public class LeanPlayerControllerMain {

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
    lookAndFeel.init();
    playerUI.init();
    StreamingService.start();
    deviceDiscovery.init();
    // TODO: add/implement device discovery. The manual call to addDevice will be afterwards removed
    addDevice(
        "LeanPlayer",
        "http://localhost:45152/app/desc.json",
        "http://localhost:45152/app/rs/control",
        "ws://localhost:45152/app/events");
  }

  public void onDeviceDiscovery(@Observes DeviceDiscoveredEvent event) throws Exception {
    addDevice(
        event.getDeviceName(),
        event.getLocation(),
        event.getControlLocation(),
        event.getEventsLocation());
  }

  private void addDevice(
      final String deviceName,
      final String location,
      final String controlLocation,
      final String eventsLocation) {
    final DefaultComboBoxModel<ComboListEntry> comboBoxModel = ComboBoxFactory.instance();
    comboBoxModel.addElement(new ComboListEntry(deviceName, new LeanPlayer(location, controlLocation, eventsLocation)));
  }

  private static void configureCdi() {
    Weld weld = new Weld();
    weld.initialize();
  }
}
