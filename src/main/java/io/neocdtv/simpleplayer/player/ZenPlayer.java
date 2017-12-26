package io.neocdtv.simpleplayer.player;

import io.neocdtv.simpleplayer.ui.PlaylistUI;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * Created by xix on 03.10.17.
 */
public class ZenPlayer implements Player {

  private final static Logger LOGGER = Logger.getLogger(ZenPlayer.class.getName());

  private final String baseUrl;
  private final String restBaseUrl = "/rs/control";
  private final String webSocketUrl = "/events";

  public ZenPlayer(String baseUrl) {
    this.baseUrl = baseUrl;
    openWebSocketConnection();
  }

  private void openWebSocketConnection() {
    final ClientEndpointConfig cec = ClientEndpointConfig.Builder.create().build();
    ClientManager client = ClientManager.createClient();
    try {
      String webSocketAddress = "ws://" + baseUrl + webSocketUrl;
      LOGGER.info(webSocketAddress);
      client.connectToServer(new Endpoint() {

        @Override
        public void onOpen(Session session, EndpointConfig config) {
          LOGGER.info("opening session: " + session.getId());
          session.addMessageHandler(new MessageHandler.Whole<String>() {

            @Override
            public void onMessage(String message) {
              LOGGER.info("Received message: " + message);
              if (message.contains("TrackEndedRendererEvent")) {
                next();
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
      }, cec, new URI(webSocketAddress));
    } catch (DeploymentException | URISyntaxException | IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void play(String url) {
    Client client = ClientBuilder.newClient();
    final Invocation.Builder request = client.target("http://" + baseUrl + restBaseUrl + "/play")
        .queryParam("url", url)
        .request(MediaType.APPLICATION_JSON);
    request.get();
  }

  @Override
  public void pause() throws PlayerException {
    Client client = ClientBuilder.newClient();
    final Invocation.Builder request = client.target("http://" + baseUrl + restBaseUrl + "/pause")
        .request(MediaType.APPLICATION_JSON);
    request.get();
  }

  @Override
  public void next() {
    final String url = PlaylistUI.getInstance().getNextTrackUrl();
    play(url);
  }

  @Override
  public void volumeUp() throws PlayerException {

  }

  @Override
  public void volumeDown() throws PlayerException {

  }
}
