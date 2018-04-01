package io.neocdtv.player.ui.control.players.chromecast;

import io.neocdtv.player.ui.control.NetworkUtil;
import io.neocdtv.player.ui.discovery.RendererDiscovery;
import io.neocdtv.player.ui.discovery.RendererDiscoveryEvent;
import io.neocdtv.player.ui.discovery.RendererLostEvent;
import su.litvak.chromecast.api.v2.ChromeCast;
import su.litvak.chromecast.api.v2.ChromeCasts;
import su.litvak.chromecast.api.v2.ChromeCastsListener;

import javax.enterprise.event.Event;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ChromeCastDiscovery.
 *
 * @author xix
 * @since 24.03.18
 */

public class ChromeCastDiscovery implements RendererDiscovery {

  private final static Logger LOGGER = Logger.getLogger(ChromeCastDiscovery.class.getName());
  private static final String MANUAL_NAME = "Chromecast Audio Device";

  @Inject
  private NetworkUtil networkUtil;

  @Inject
  private Event<RendererDiscoveryEvent> rendererDiscoveryEvent;

  @Inject
  private Event<RendererLostEvent> rendererLostEvent;

  @Override
  public void start() {
    String ip = System.getProperty("ip");
    if (ip != null) {
      manualConfiguration(ip);
    } else {
      autoConfiguration();
    }
  }

  private void manualConfiguration(String ip) {
    LOGGER.info("Manual Device Configuration: " + ip);
    final String name = MANUAL_NAME;
    final ChromeCastPlayer player = createPlayer();
    player.start(ip);
    RendererDiscoveryEvent manualDiscoveryEvent = new RendererDiscoveryEvent(name, UUID.randomUUID().toString() ,player);
    rendererDiscoveryEvent.fire(manualDiscoveryEvent);
  }

  private void autoConfiguration() {
    LOGGER.info("Auto Device Configuration");
    try {
      ChromeCasts.registerListener(new ChromeCastsListener() {
        @Override
        public void newChromeCastDiscovered(ChromeCast chromeCast) {
          final ChromeCastPlayer player = createPlayer();
          LOGGER.info("Device discovered: " + chromeCast.getName());
          player.start(chromeCast);
          RendererDiscoveryEvent autoDiscoveryEvent = new RendererDiscoveryEvent(chromeCast.getName(), chromeCast.getTitle(), player);
          rendererDiscoveryEvent.fire(autoDiscoveryEvent);
        }
        @Override
        public void chromeCastRemoved(ChromeCast chromeCast) {
          LOGGER.info("Device lost: "+ chromeCast.getName());
          rendererLostEvent.fire(RendererLostEvent.create(chromeCast.getName()));
        }
      });
      networkUtil.findIpv4AddressForActiveInterfaces().stream().forEach(address -> {
        try {
          ChromeCasts.startDiscovery(address);
        } catch (IOException e) {
          LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
      });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  private ChromeCastPlayer createPlayer() {
    return CDI.current().select(ChromeCastPlayer.class).get();
  }
}
