package io.neocdtv.player.ui.control.players.leanplayer;

import io.neocdtv.player.ui.control.TrackEndedEvent;
import org.glassfish.tyrus.client.ClientManager;

import javax.enterprise.event.Event;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * LeanPlayerEventsHandler. Handles renderer-events received over websocket connection.
 *
 * @author xix
 * @since 27.03.18
 */
public class LeanPlayerEventsHandler {

  private final static Logger LOGGER = Logger.getLogger(LeanPlayerEventsHandler.class.getName());

  private String eventsLocation;
  private Event<TrackEndedEvent> trackEndedEventEvent;

  public void setupEventsConnection() {
    final ClientEndpointConfig cec = ClientEndpointConfig.Builder.create().build();
    ClientManager client = ClientManager.createClient();
    try {
      LOGGER.info(eventsLocation);
      client.connectToServer(new Endpoint() {

        @Override
        public void onOpen(Session session, EndpointConfig config) {
          LOGGER.info("opening session: " + session.getId());
          session.addMessageHandler(new MessageHandler.Whole<String>() {

            @Override
            public void onMessage(String message) {
              LOGGER.info("Received message: " + message);
              if (message.contains("TrackEndedRendererEvent")) {
                trackEndedEventEvent.fire(new TrackEndedEvent());
              }
            }
          });
        }

        @Override
        public void onClose(Session session, CloseReason closeReason) {
          LOGGER.info(session.getId() + ", reason: " + closeReason.toString());
        }

        @Override
        public void onError(Session session, Throwable thr) {
          LOGGER.info(session.getId() + ", exceptionMessage: " + thr.getMessage());

        }
      }, cec, new URI(eventsLocation));
    } catch (DeploymentException | URISyntaxException | IOException e) {
      e.printStackTrace();
    }
  }

  public void setEventsLocation(String eventsLocation) {
    this.eventsLocation = eventsLocation;
  }

  public void setTrackEndedEventEvent(Event<TrackEndedEvent> trackEndedEventEvent) {
    this.trackEndedEventEvent = trackEndedEventEvent;
  }
}
