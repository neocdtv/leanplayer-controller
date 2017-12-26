package io.neocdtv.leanplayer.controller.init;

import javax.inject.Inject;

/**
 * DeviceDiscovery.
 *
 * @author xix
 * @since 22.12.17
 */
public class DeviceDiscovery {

  @Inject
  private DeviceDiscoveryEventsHandler deviceDiscoveryEventsHandler;

  // check out if this can be moved to a constructor
  public void init() {

    //UpnpDiscover.startIt(deviceDiscoveryEventsHandler);
  }
}