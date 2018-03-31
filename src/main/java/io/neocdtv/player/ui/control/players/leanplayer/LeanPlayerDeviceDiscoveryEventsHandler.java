package io.neocdtv.player.ui.control.players.leanplayer;

import io.neocdtv.player.ui.control.TrackEndedEvent;
import io.neocdtv.player.ui.discovery.RendererDiscoveryEvent;
import io.neocdtv.upnp.discovery.DeviceDiscoveryEventsHandler;
import io.neocdtv.upnp.discovery.HeaderHelper;
import io.neocdtv.upnp.discovery.HttpConstants;
import io.neocdtv.upnp.discovery.LeanPlayerConstants;
import io.neocdtv.upnp.discovery.UpnpHelper;

import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * DeviceDiscoveryEventsHandler. Plugs into upnp-lite to be notified if a device was discovered.
 * If the found device is a leanplayer-renderer notify the controller with RendererDiscoveryEvent.
 *
 * @author xix
 * @since 22.12.17
 */
public class LeanPlayerDeviceDiscoveryEventsHandler implements DeviceDiscoveryEventsHandler {

  @Inject
  private Event<RendererDiscoveryEvent> rendererDiscoveryEventEvent;

  @Inject
  private Event<TrackEndedEvent> trackEndedEventEvent;

  @Override
  public void onDeviceDiscovery(
      final String payload,
      final String deviceAddress) {

    if (isLeanPlayerCompatibleResponse(payload)) {

      final String deviceName = extractDeviceName(payload);
      final String controlLocation = extractControlLocation(payload);
      final String eventsLocation = extractEventsLocation(payload);

      final LeanPlayer leanPlayer = new LeanPlayer();
      leanPlayer.setControlLocation(controlLocation);

      final LeanPlayerEventsHandler leanPlayerEventsHandler = new LeanPlayerEventsHandler();
      leanPlayerEventsHandler.setEventsLocation(eventsLocation);
      leanPlayerEventsHandler.setTrackEndedEventEvent(trackEndedEventEvent);
      leanPlayerEventsHandler.connect();

      rendererDiscoveryEventEvent.fire(new RendererDiscoveryEvent(deviceName, leanPlayer));
    }
  }

  private boolean isLeanPlayerCompatibleResponse(String payload) {
    return payload.contains(UpnpHelper.MEDIA_RENDERER) &&
        payload.contains(LeanPlayerConstants.HTTP_HEADER_NAME_CONTROL_LOCATION) &&
        payload.contains(LeanPlayerConstants.HTTP_HEADER_NAME_EVENTS_LOCATION);
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
