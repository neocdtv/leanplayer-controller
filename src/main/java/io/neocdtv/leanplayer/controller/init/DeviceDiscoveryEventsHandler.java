package io.neocdtv.leanplayer.controller.init;

import io.neocdtv.EventsHandler;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * DeviceDiscoveryEventsHandler.
 *
 * @author xix
 * @since 22.12.17
 */
public class DeviceDiscoveryEventsHandler implements EventsHandler {

  @Inject
  private Event<DeviceDiscoveredEvent> deviceDiscoveredEvent;

  @Override
  public void onDeviceDiscovery(
      final String deviceName,
      final String location,
      final String controlLocation,
      final String eventsLocation) {
    deviceDiscoveredEvent.fire(
        new DeviceDiscoveredEvent(
            deviceName,
            location,
            controlLocation,
            eventsLocation));
  }
}
