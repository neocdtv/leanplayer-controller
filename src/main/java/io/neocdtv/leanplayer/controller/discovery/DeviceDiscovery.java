package io.neocdtv.leanplayer.controller.discovery;

import io.neocdtv.UpnpDiscoveryLite;
import io.neocdtv.UpnpHelper;
import io.neocdtv.leanplayer.controller.player.LeanPlayer;
import io.neocdtv.leanplayer.controller.ui.PlayerSelectionEntry;
import io.neocdtv.leanplayer.controller.ui.PlayerSelectionList;

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

  @Inject
  private PlayerSelectionList playerSelectionList;

  @Inject
  private LeanPlayer leanPlayer;

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
    final DefaultComboBoxModel<PlayerSelectionEntry> comboBoxModel = playerSelectionList.getModel();
    leanPlayer.setEventsLocation(eventsLocation);
    leanPlayer.setControlLocation(controlLocation);
    leanPlayer.openWebSocketConnection();
    comboBoxModel.addElement(new PlayerSelectionEntry(deviceName, leanPlayer));
  }
}