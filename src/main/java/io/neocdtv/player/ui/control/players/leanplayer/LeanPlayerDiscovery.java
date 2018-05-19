package io.neocdtv.player.ui.control.players.leanplayer;

import io.neocdtv.commons.network.NetworkUtil;
import io.neocdtv.player.ui.control.TrackEndedEvent;
import io.neocdtv.player.ui.discovery.RendererDiscovery;
import io.neocdtv.player.ui.discovery.RendererDiscoveryEvent;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.SocketException;
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
  private NetworkUtil networkUtil;

  @Inject
  private Event<RendererDiscoveryEvent> rendererDiscoveryEventEvent;

  @Inject
  private Event<TrackEndedEvent> trackEndedEventEvent;


  @Override
  public void start() {
    try {
      networkUtil.findIpv4AddressForActiveInterfaces().stream().forEach(inetAddress -> {
        try {
          JmDNS jmdns = JmDNS.create(inetAddress);
          jmdns.addServiceListener(SERVICE_TYPE, this);
        } catch (IOException e) {
          LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
      });
    } catch (SocketException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }
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
    LOGGER.info(event.toString());
    final LeanPlayer leanPlayer = new LeanPlayer();
    leanPlayer.setControlLocation(event.getInfo().getPropertyString("control-location"));

    final LeanPlayerEventsHandler leanPlayerEventsHandler = new LeanPlayerEventsHandler();
    leanPlayerEventsHandler.setEventsLocation(event.getInfo().getPropertyString("events-location"));
    leanPlayerEventsHandler.setTrackEndedEventEvent(trackEndedEventEvent);
    leanPlayerEventsHandler.setupEventsConnection();
    rendererDiscoveryEventEvent.fire(
        new RendererDiscoveryEvent(
            "Leanplayer Renderer",
            event.getInfo().getPropertyString("uuid"),
            leanPlayer));
  }
}
