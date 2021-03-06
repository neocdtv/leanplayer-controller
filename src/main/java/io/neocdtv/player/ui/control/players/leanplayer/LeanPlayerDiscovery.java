package io.neocdtv.player.ui.control.players.leanplayer;

import io.neocdtv.commons.network.NetworkUtil;
import io.neocdtv.player.ui.control.ActiveAddresses;
import io.neocdtv.player.ui.control.TrackEndedEvent;
import io.neocdtv.player.ui.discovery.RendererDiscovery;
import io.neocdtv.player.ui.discovery.RendererDiscoveryEvent;
import io.neocdtv.player.ui.discovery.RendererLostEvent;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.Inet4Address;
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

  private static final String PROPERTY_UUID = "uuid";
  private static final String PROPERTY_NAME = "name";
  private static final String PROPERTY_CONTROL_LOCATION = "control-location";
  private static final String PROPERTY_EVENTS_LOCATION = "events-location";

  @Inject
  private ActiveAddresses activeAddresses;

  @Inject
  private Event<RendererLostEvent> rendererLostEvent;

  @Inject
  private Event<RendererDiscoveryEvent> rendererDiscoveryEvent;

  @Inject
  private Event<TrackEndedEvent> trackEndedEventEvent;

  @Inject
  private NetworkUtil networkUtil;

  @Override
  public void start() {
    String interfaceName = System.getProperty("if");
    try {
      if (interfaceName == null) {
        registerOnAllActiveAddresses();
      } else {
        Inet4Address address = networkUtil.getIpv4AddressForInterface(interfaceName);
        JmDNS jmdns = JmDNS.create(address);
        jmdns.addServiceListener(SERVICE_TYPE, this);
        LOGGER.log(Level.INFO,
            String.format("starting discovery service on address %s",
                address.getHostAddress()));
      }
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }
  }

  private void registerOnAllActiveAddresses() {
    activeAddresses.getAddresses().stream().forEach(address -> {
      try {
        JmDNS jmdns = JmDNS.create(address);
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
    //rendererLostEvent.fire(RendererLostEvent.create(event.getInfo().getPropertyString(PROPERTY_UUID)));
    LOGGER.info("Not implemented yet");
  }

  @Override
  public void serviceResolved(ServiceEvent event) {
    try {
      LOGGER.info(event.toString());
      final LeanPlayer leanPlayer = new LeanPlayer();
      leanPlayer.setControlLocation(event.getInfo().getPropertyString(PROPERTY_CONTROL_LOCATION));
      leanPlayer.setLocalInterfaceAddressToStreamFrom(event.getDNS().getInetAddress());

      final LeanPlayerEventsHandler leanPlayerEventsHandler = new LeanPlayerEventsHandler();
      leanPlayerEventsHandler.setEventsLocation(event.getInfo().getPropertyString(PROPERTY_EVENTS_LOCATION));
      leanPlayerEventsHandler.setTrackEndedEventEvent(trackEndedEventEvent);
      leanPlayerEventsHandler.setupEventsConnection();

      rendererDiscoveryEvent.fire(
          new RendererDiscoveryEvent(
              event.getInfo().getPropertyString(PROPERTY_NAME),
              event.getInfo().getPropertyString(PROPERTY_UUID),
              leanPlayer));
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }
  }
}
