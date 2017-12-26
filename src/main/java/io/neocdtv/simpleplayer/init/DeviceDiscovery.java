package io.neocdtv.simpleplayer.init;

import javax.inject.Inject;

public class DeviceDiscovery {

  @Inject
  private DeviceDiscoveryEventsHandler deviceDiscoveryEventsHandler;

  // check out if this can be moved to a constructor
  public void init() {

    //UpnpDiscover.startIt(deviceDiscoveryEventsHandler);
  }
}