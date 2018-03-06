package io.neocdtv.leanplayer.controller.init;

import io.neocdtv.EventsHandler;
import io.neocdtv.HeaderHelper;
import io.neocdtv.HttpConstants;
import io.neocdtv.LeanPlayerConstants;
import io.neocdtv.UpnpHelper;

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
      final String payload,
      final String deviceAddress) {

    if (payload.contains(UpnpHelper.MEDIA_RENDERER) &&
        payload.contains(LeanPlayerConstants.HTTP_HEADER_NAME_CONTROL_LOCATION) &&
        payload.contains(LeanPlayerConstants.HTTP_HEADER_NAME_EVENTS_LOCATION)) {

      final String deviceName = extractDeviceName(payload);
      final String controlLocation = extractControlLocation(payload);
      final String eventsLocation = extractEventsLocation(payload);

      deviceDiscoveredEvent.fire(
          new DeviceDiscoveredEvent(
              deviceName,
              controlLocation,
              eventsLocation));
    }
  }

  private String extractDeviceName(final String receivedMessage) {
    return HeaderHelper.extractHeader(HttpConstants.HTTP_HEADER_NAME_SERVER, receivedMessage);
  }

  private String extractControlLocation(final String receivedMessage) {
    return HeaderHelper.extractHeader(LeanPlayerConstants.HTTP_HEADER_NAME_CONTROL_LOCATION, receivedMessage);
  }

  private String extractEventsLocation(final String receivedMessage) {
    return HeaderHelper.extractHeader(LeanPlayerConstants.HTTP_HEADER_NAME_EVENTS_LOCATION, receivedMessage);
  }
}
