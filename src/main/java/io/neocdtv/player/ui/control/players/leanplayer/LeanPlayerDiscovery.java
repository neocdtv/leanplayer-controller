package io.neocdtv.player.ui.control.players.leanplayer;

import io.neocdtv.player.ui.discovery.RendererDiscovery;
import io.neocdtv.upnp.discovery.UpnpDiscoveryLite;
import io.neocdtv.upnp.discovery.UpnpHelper;

import javax.inject.Inject;

/**
 * LeanPlayerDiscovery.
 *
 * @author xix
 * @since 25.03.18
 */
public class LeanPlayerDiscovery implements RendererDiscovery {

  @Inject
  private LeanPlayerDeviceDiscoveryEventsHandler deviceDiscoveryEventsHandler;

  @Override
  public void start() {
    final String uuid = UpnpHelper.buildUuid();
    UpnpDiscoveryLite.startIt(deviceDiscoveryEventsHandler, uuid);
  }
}
