package io.neocdtv.player.ui.discovery;


import io.neocdtv.player.ui.control.players.chromecast.ChromeCastDiscovery;
import io.neocdtv.player.ui.control.players.leanplayer.LeanPlayerDiscovery;

import javax.inject.Inject;

/**
 * RendererDiscoveryProducer.
 *
 * @author xix
 * @since 25.03.18
 */

public class RendererDiscoveryConfigurator {

  @Inject
  private ChromeCastDiscovery chromeCastDiscovery;


  @Inject
  private LeanPlayerDiscovery leanPlayerDiscovery;

  public RendererDiscovery configurePlayerType() {
    String type = System.getProperty("type");
    if (RendererType.CHROMECAST.toString().equals(type)) {
      return chromeCastDiscovery;
    } else {
      return leanPlayerDiscovery;
    }
  }
}