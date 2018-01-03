package io.neocdtv.leanplayer.controller.init;

/**
 * DeviceDiscoveredEvent.
 *
 * @author xix
 * @since 22.12.17
 */
public class DeviceDiscoveredEvent {

  private final String deviceName;
  private final String location;
  private final String controlLocation;
  private final String eventsLocation;

  public DeviceDiscoveredEvent(final String deviceName, final String location, final String controlLocation, final String eventsLocation) {
    this.deviceName = deviceName;
    this.location = location;
    this.controlLocation = controlLocation;
    this.eventsLocation = eventsLocation;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public String getLocation() {
    return location;
  }

  public String getControlLocation() {
    return controlLocation;
  }

  public String getEventsLocation() {
    return eventsLocation;
  }
}
