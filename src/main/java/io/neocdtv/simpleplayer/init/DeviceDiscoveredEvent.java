package io.neocdtv.simpleplayer.init;

/**
 * Created by xix on 05.12.17.
 */
public class DeviceDiscoveredEvent {

  private String deviceAddress = "localhost:8081";
  // TODO: decide what data to transport:
  // deviceDescription? or extracted deviceName,
  // deviceAddress is not acurate, you are missing ports for rest and websockets
  private String deviceName = "Zenplayer";

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
