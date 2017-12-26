package io.neocdtv.simpleplayer.init;

import io.neocdtv.EventsHandler;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * Created by xix on 09.12.17.
 */
public class DeviceDiscoveryEventsHandler implements EventsHandler {

  @Inject
  private Event<DeviceDiscoveredEvent> deviceDiscoveredEvent;

  @Override
  public void onDeviceDiscovery(String deviceAddress, String deviceDescription) {
    deviceDiscoveredEvent.fire(new DeviceDiscoveredEvent(deviceAddress, deviceDescription));
  }
}
