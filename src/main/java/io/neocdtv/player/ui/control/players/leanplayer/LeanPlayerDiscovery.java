package io.neocdtv.player.ui.control.players.leanplayer;

import io.neocdtv.player.ui.control.ActiveAddresses;
import io.neocdtv.player.ui.control.TrackEndedEvent;
import io.neocdtv.player.ui.discovery.RendererDiscovery;
import io.neocdtv.player.ui.discovery.RendererDiscoveryEvent;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * LeanPlayerDiscovery.
 *
 * @author xix
 * @since 25.03.18
 */
public class LeanPlayerDiscovery implements RendererDiscovery, ServiceListener {

  private static final Logger LOGGER = Logger.getLogger(LeanPlayerDiscovery.class.getName());
  private static final String SERVICE_TYPE = "_leanplayer._tcp.local.";

  @Inject
  private ActiveAddresses activeAddresses;

  @Inject
  private Event<RendererDiscoveryEvent> rendererDiscoveryEventEvent;

  @Inject
  private Event<TrackEndedEvent> trackEndedEventEvent;


  @Override
  public void start() {
    activeAddresses.getAddresses().stream().forEach(inetAddress -> {
      try {
        JmDNS jmdns = JmDNS.create(inetAddress);
        jmdns.addServiceListener(SERVICE_TYPE, this);
      } catch (IOException e) {
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
      }
    });
  }

  @Override
  public void serviceAdded(ServiceEvent event) {
    LOGGER.info(event.toString());
  }

  @Override
  public void serviceRemoved(ServiceEvent event) {
    LOGGER.info(event.toString());
  }

  @Override
  public void serviceResolved(ServiceEvent event) {
    try {
      LOGGER.info(event.toString());
      final LeanPlayer leanPlayer = new LeanPlayer();
      leanPlayer.setControlLocation(event.getInfo().getPropertyString("control-location"));
      leanPlayer.setAddress(event.getDNS().getInetAddress());
      final LeanPlayerEventsHandler leanPlayerEventsHandler = new LeanPlayerEventsHandler();
      leanPlayerEventsHandler.setEventsLocation(event.getInfo().getPropertyString("events-location"));
      leanPlayerEventsHandler.setTrackEndedEventEvent(trackEndedEventEvent);
      leanPlayerEventsHandler.setupEventsConnection();
      // TODO: add somewhere a check that if the player is found on loopback and other interface,
      // that the other interface should be preferred and that if multi-homed, that only one
      // on version the player is shown in the drop down
      rendererDiscoveryEventEvent.fire(
          new RendererDiscoveryEvent(
              event.getInfo().getPropertyString("name"),
              event.getInfo().getPropertyString("uuid"),
              leanPlayer));
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }
  }
}
