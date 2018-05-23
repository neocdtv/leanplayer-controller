package io.neocdtv.player.ui.control.players.leanplayer;

import io.neocdtv.player.ui.control.Player;
import org.glassfish.jersey.filter.LoggingFilter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import java.net.InetAddress;
import java.util.logging.Logger;

/**
 * LeanPlayer.
 *
 * @author xix
 * @since 22.12.17
 */
public class LeanPlayer implements Player {

  private final static Logger LOGGER = Logger.getLogger(LeanPlayer.class.getName());
  private String controlLocation;
  private InetAddress address;

  public void setControlLocation(String controlLocation) {
    this.controlLocation = controlLocation;
  }

  @Override
  public void play(String url) {
    Client client = configureClient();
    final Invocation.Builder request = client.target(controlLocation + "/play?url=" + url)
        .request(MediaType.APPLICATION_JSON);
    request.get();
  }

  private Client configureClient() {
    Client client = ClientBuilder.newClient();
    client.register(LoggingFilter.class);
    return client;
  }

  @Override
  public void pause() {
    Client client = configureClient();
    final Invocation.Builder request = client.target(controlLocation + "/pause")
        .request(MediaType.APPLICATION_JSON);
    request.get();
  }

  @Override
  public void volumeUp() {

  }

  @Override
  public void volumeDown() {

  }

  @Override
  public InetAddress getAddress() {
    return address;
  }

  public void setAddress(InetAddress address) {
    this.address = address;
  }
}
