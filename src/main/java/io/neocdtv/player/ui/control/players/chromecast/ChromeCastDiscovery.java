package io.neocdtv.player.ui.control.players.chromecast;

import io.neocdtv.player.ui.control.ActiveAddresses;
import io.neocdtv.player.ui.discovery.RendererDiscovery;
import io.neocdtv.player.ui.discovery.RendererDiscoveryEvent;
import io.neocdtv.player.ui.discovery.RendererLostEvent;
import su.litvak.chromecast.api.v2.ChromeCast;

import javax.enterprise.event.Event;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ChromeCastDiscovery.
 *
 * @author xix
 * @since 24.03.18
 */

public class ChromeCastDiscovery implements RendererDiscovery, ServiceListener {

  private final static Logger LOGGER = Logger.getLogger(ChromeCastDiscovery.class.getName());
  public static final String PROPERTY_NAME_MODEL = "md";
  public static final String PROPERTY_NAME_NAME = "fn";
  public static final String PROPERTY_NAME_ID = "id";

  @Inject
  private ActiveAddresses activeAddresses;

  @Inject
  private Event<RendererDiscoveryEvent> rendererDiscoveryEvent;

  @Inject
  private Event<RendererLostEvent> rendererLostEvent;

  @Override
  public void start() {
    activeAddresses.getAddresses().stream().forEach(address -> {
      try {
        JmDNS jmdns = JmDNS.create(address);
        jmdns.addServiceListener(ChromeCast.SERVICE_TYPE, this);
      } catch (IOException e) {
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
      }
    });
  }

  private ChromeCastPlayer createPlayer() {
    return CDI.current().select(ChromeCastPlayer.class).get();
  }

  @Override
  public void serviceAdded(ServiceEvent event) {
    LOGGER.info(event.toString());
  }

  @Override
  public void serviceRemoved(ServiceEvent event) {
    LOGGER.info(event.toString());
    LOGGER.info("Not implemented yet");
  }

  @Override
  public void serviceResolved(ServiceEvent event) {
    try {
      LOGGER.info(event.toString());
      final ChromeCastPlayer player = createPlayer();
      final String deviceAddress = event.getInfo().getHostAddresses()[0];
      ChromeCast chromeCast = new ChromeCast(deviceAddress);
      // TODO: move the start to first play?
      // Starting chromecast here start the app on all found devices
      //player.start(chromeCast);
      player.setLocalInterfaceAddressToStreamFrom(event.getDNS().getInetAddress());

      final String deviceName = String.format("%s (%s)",
          event.getInfo().getPropertyString(PROPERTY_NAME_NAME),
          event.getInfo().getPropertyString(PROPERTY_NAME_MODEL));
      final String deviceId = event.getInfo().getPropertyString(PROPERTY_NAME_ID);

      RendererDiscoveryEvent autoDiscoveryEvent = new RendererDiscoveryEvent(deviceName, deviceId, player);
      rendererDiscoveryEvent.fire(autoDiscoveryEvent);

    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }
  }
}
