package io.neocdtv.player.ui.control.players.chromecast;

import io.neocdtv.player.ui.control.ActiveAddresses;
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
import java.net.InetAddress;
import java.net.UnknownHostException;
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
  private ActiveAddresses activeAddresses;

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
    RendererDiscoveryEvent manualDiscoveryEvent = new RendererDiscoveryEvent(name, UUID.randomUUID().toString(), player);
    rendererDiscoveryEvent.fire(manualDiscoveryEvent);
  }

  private void autoConfiguration() {
    LOGGER.info("Auto Device Configuration");
    ChromeCasts.registerListener(new ChromeCastsListener() {
      @Override
      public void newChromeCastDiscovered(ChromeCast chromeCast) {
        try {
          final ChromeCastPlayer player = createPlayer();
          LOGGER.info("Device discovered: " + chromeCast.getName());
          // TODO: test if InetAddress.getByName is the correct method to get the inetaddress
          player.start(chromeCast);
          RendererDiscoveryEvent autoDiscoveryEvent = new RendererDiscoveryEvent(chromeCast.getTitle() + " (" + chromeCast.getModel() + ")", chromeCast.getName(), player);
          rendererDiscoveryEvent.fire(autoDiscoveryEvent);
          player.setAddress(InetAddress.getByName(chromeCast.getAddress()));
        } catch (UnknownHostException e) {
          LOGGER.log(Level.SEVERE, e.getMessage(), e);
          throw new RuntimeException(e);
        }
      }

      @Override
      public void chromeCastRemoved(ChromeCast chromeCast) {
        LOGGER.info("Device lost: " + chromeCast.getName());
        // chromeCastRemoved is called even the device seems to work correctly; idea try open socket to device and if not possible throw event;
        // or maybe open every 1,2 or 3s a socket to check if device works and if not throw event
        // rendererLostEvent.fire(RendererLostEvent.create(chromeCast.getName()));
      }
    });
    activeAddresses.getAddresses().stream().forEach(address -> {
      try {
        ChromeCasts.startDiscovery(address);
      } catch (IOException e) {
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
      }
    });
  }

  private ChromeCastPlayer createPlayer() {
    return CDI.current().select(ChromeCastPlayer.class).get();
  }
}
