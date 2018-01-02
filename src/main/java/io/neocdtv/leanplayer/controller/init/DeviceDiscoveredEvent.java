package io.neocdtv.leanplayer.controller.init;

/**
 * DeviceDiscoveredEvent.
 *
 * @author xix
 * @since 22.12.17
 */
public class DeviceDiscoveredEvent {

  private String deviceAddress = "localhost:8081";
  private String deviceName = "LeanPlayer";
  private String controlPath = "";
  private String eventsPath = "";

  public DeviceDiscoveredEvent(final String deviceAddress, final String deviceName) {
    this.deviceAddress = deviceAddress;
    this.deviceName = deviceName;
  }

  public String getDeviceAddress() {
    return deviceAddress;
  }

  public String getDeviceName() {
    return deviceName;
  }
}
