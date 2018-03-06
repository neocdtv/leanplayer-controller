package io.neocdtv.leanplayer.controller.init;

import io.neocdtv.UpnpDiscoveryLite;
import io.neocdtv.UpnpHelper;
import io.neocdtv.leanplayer.controller.player.LeanPlayer;
import io.neocdtv.leanplayer.controller.ui.ComboBoxFactory;
import io.neocdtv.leanplayer.controller.ui.ComboListEntry;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.swing.*;

/**
 * DeviceDiscovery.
 *
 * @author xix
 * @since 22.12.17
 */
public class DeviceDiscovery {

  @Inject
  private DeviceDiscoveryEventsHandler deviceDiscoveryEventsHandler;

  public void startDiscovery() {
    final String uuid = UpnpHelper.buildUuid();
    UpnpDiscoveryLite.startIt(deviceDiscoveryEventsHandler, uuid);
  }

  public void onDeviceDiscovery(@Observes DeviceDiscoveredEvent event) throws Exception {
    addDevice(
        event.getDeviceName(),
        event.getControlLocation(),
        event.getEventsLocation());
  }

  void addDevice(
      final String deviceName,
      final String controlLocation,
      final String eventsLocation) {
    final DefaultComboBoxModel<ComboListEntry> comboBoxModel = ComboBoxFactory.instance();
    comboBoxModel.addElement(new ComboListEntry(deviceName, new LeanPlayer(controlLocation, eventsLocation)));
  }
}