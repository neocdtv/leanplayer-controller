package io.neocdtv.player.ui.control.players.chromecast;

import io.neocdtv.player.ui.control.Player;
import io.neocdtv.player.ui.control.TrackEndedEvent;
import su.litvak.chromecast.api.v2.ChromeCast;
import su.litvak.chromecast.api.v2.ChromeCastConnectionEvent;
import su.litvak.chromecast.api.v2.ChromeCastConnectionEventListener;
import su.litvak.chromecast.api.v2.ChromeCastException;
import su.litvak.chromecast.api.v2.ChromeCastSpontaneousEvent;
import su.litvak.chromecast.api.v2.ChromeCastSpontaneousEventListener;
import su.litvak.chromecast.api.v2.MediaStatus;
import su.litvak.chromecast.api.v2.Status;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ChromeCastPlayer.
 *
 * @author xix
 * @since 24.03.18
 */
public class ChromeCastPlayer implements Player {

  private final static Logger LOGGER = Logger.getLogger(ChromeCastPlayer.class.getName());
  private static final String DEFAULT_MEDIA_RENDERER_APP_ID = "CC1AD845";
  private static final String STATUS_TEXT_READY_TO_CAST = "Ready To Cast";
  private ChromeCast chromeCast;
  private MediaStatus lastMediaStatus;
  private Status lastStatus;
  private boolean isAppInitialized = false;

  @Inject
  private Event<TrackEndedEvent> trackEndedEventEvent;

  public void start(final String ip) {
    chromeCast = new ChromeCast(ip);
    start(chromeCast);
  }

  public void start(final ChromeCast chromeCast) {
    this.chromeCast = chromeCast;
    chromeCast.registerListener(new ChromeCastSpontaneousEventListener() {
      public void spontaneousEventReceived(ChromeCastSpontaneousEvent chromeCastSpontaneousEvent) {
        LOGGER.info("Type: " + chromeCastSpontaneousEvent.getType() + "\nData: " + chromeCastSpontaneousEvent.getData());
        if (chromeCastSpontaneousEvent.getType().equals(ChromeCastSpontaneousEvent.SpontaneousEventType.MEDIA_STATUS)) {
          lastMediaStatus = chromeCastSpontaneousEvent.getData(MediaStatus.class);
        } else if (chromeCastSpontaneousEvent.getType().equals(ChromeCastSpontaneousEvent.SpontaneousEventType.STATUS)) {
          lastStatus = chromeCastSpontaneousEvent.getData(Status.class);
          if (lastMediaStatus.idleReason.equals(MediaStatus.IdleReason.FINISHED) && lastStatus.applications.get(0).statusText.equals(STATUS_TEXT_READY_TO_CAST)) {
            trackEndedEventEvent.fire(new TrackEndedEvent());
          }
        }
      }
    });
    chromeCast.registerConnectionListener(new ChromeCastConnectionEventListener() {
      @Override
      public void connectionEventReceived(ChromeCastConnectionEvent chromeCastConnectionEvent) {
        LOGGER.info("is connected: " + chromeCastConnectionEvent.isConnected());
        //isAppInitialized = chromeCastConnectionEvent.isConnected();
      }
    });
  }


  // TODO: still work in progress on trying to improve stability
  private void ensureDefaultMediaRendererIsRunningAndChromeCastIsConnected() throws IOException, GeneralSecurityException {
    if (chromeCast.isConnected()) {
      LOGGER.info("reconnecting...");
      chromeCast.disconnect();
      chromeCast.connect();
    }
    LOGGER.info("isAppInitialized: " + isAppInitialized);
    if (!isAppInitialized) {
      try {
        LOGGER.info("starting APP " + DEFAULT_MEDIA_RENDERER_APP_ID);
        chromeCast.launchApp(DEFAULT_MEDIA_RENDERER_APP_ID);
        isAppInitialized = true;
      } catch (IOException e) {
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
      }
    }
  }

  /* TODO: stability: catch all occuring exceptions in a separate block and try to handle them accordingly
    - NullPointer means ChromeCast (API) is broken, so it needs to be initialized again
    - ConnectionExceptions, check if the socket is open and if yes retry - what connecting, initializing the app again
    - SSLException/Security
  */
  public void play(String url) {
    try {
      ensureDefaultMediaRendererIsRunningAndChromeCastIsConnected();
      chromeCast.load(url);
      chromeCast.play();
    } catch (ChromeCastException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
      isAppInitialized = false;
      play(url);
    } catch (GeneralSecurityException e) {
      // TODO: implement specific handling
      handleExceptions(e);
    } catch (NullPointerException e) {
      // TODO: implement specific handling
      handleExceptions(e);
    } catch (IOException e) {
      handleExceptions(e);
    }
  }

  public void pause() {
    try {
      ensureDefaultMediaRendererIsRunningAndChromeCastIsConnected();
      chromeCast.pause();
    } catch (Throwable e) {
      handleExceptions(e);
    }
  }

  public void volumeUp() {
    try {
      ensureDefaultMediaRendererIsRunningAndChromeCastIsConnected();
      final Status status = chromeCast.getStatus();
      chromeCast.setVolume(status.volume.level + 0.01f);
    } catch (Throwable e) {
      handleExceptions(e);
    }
  }

  public void volumeDown() {
    try {
      ensureDefaultMediaRendererIsRunningAndChromeCastIsConnected();
      final Status status = chromeCast.getStatus();
      chromeCast.setVolume(status.volume.level - 0.01f);
    } catch (Throwable e) {
      handleExceptions(e);
    }
  }

  private void handleExceptions(Throwable e) {
    LOGGER.log(Level.INFO, e.getMessage(), e);
    throw new RuntimeException(e);
  }
}
