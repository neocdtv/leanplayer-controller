package io.neocdtv.player.ui.control.players.chromecast;

import io.neocdtv.player.ui.control.Player;
import io.neocdtv.player.ui.control.TrackEndedEvent;
import su.litvak.chromecast.api.v2.Application;
import su.litvak.chromecast.api.v2.ChromeCast;
import su.litvak.chromecast.api.v2.ChromeCastConnectionEvent;
import su.litvak.chromecast.api.v2.ChromeCastConnectionEventListener;
import su.litvak.chromecast.api.v2.ChromeCastSpontaneousEvent;
import su.litvak.chromecast.api.v2.ChromeCastSpontaneousEventListener;
import su.litvak.chromecast.api.v2.Media;
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
  private ChromeCast chromeCast;
  private MediaStatus lastMediaStatus;
  private Application application;

  @Inject
  private Event<TrackEndedEvent> trackEndedEventEvent;

  public void start(final String ip) {
    chromeCast = new ChromeCast(ip);
    start(chromeCast);
  }

  public void start(final ChromeCast chromeCast) {
    this.chromeCast = chromeCast;

    connect(true);

    //TODO: chromeCast.setVolumeByIncrement(0.01f);
    chromeCast.registerListener(new ChromeCastSpontaneousEventListener() {
      public void spontaneousEventReceived(ChromeCastSpontaneousEvent chromeCastSpontaneousEvent) {
        LOGGER.info("Data: " + chromeCastSpontaneousEvent.getData() + ", \nType: " + chromeCastSpontaneousEvent.getType());
        if (chromeCastSpontaneousEvent.getType().equals(ChromeCastSpontaneousEvent.SpontaneousEventType.MEDIA_STATUS)) {
          lastMediaStatus = chromeCastSpontaneousEvent.getData(MediaStatus.class);
        }
        if (chromeCastSpontaneousEvent.getType().equals(ChromeCastSpontaneousEvent.SpontaneousEventType.STATUS)) {
          final Status status = chromeCastSpontaneousEvent.getData(Status.class);
          if (lastMediaStatus.idleReason.equals(MediaStatus.IdleReason.FINISHED) && status.applications.get(0).statusText.equals("Ready To Cast")) {
            trackEndedEventEvent.fire(new TrackEndedEvent());
          }
        }
      }
    });
    chromeCast.registerConnectionListener(new ChromeCastConnectionEventListener() {
      @Override
      public void connectionEventReceived(ChromeCastConnectionEvent chromeCastConnectionEvent) {
        LOGGER.info("is connected: " + chromeCastConnectionEvent.isConnected());
      }
    });
  }

  private void connect(final boolean force) {
    LOGGER.info("Trying to start APP: " + DEFAULT_MEDIA_RENDERER_APP_ID);
    try {
      if (force || chromeCast.isAppAvailable(DEFAULT_MEDIA_RENDERER_APP_ID) && !chromeCast.getStatus().isAppRunning(DEFAULT_MEDIA_RENDERER_APP_ID)) {
        application = chromeCast.launchApp(DEFAULT_MEDIA_RENDERER_APP_ID);
      }
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
      connect(force);
    }
  }

  public void play(String url) {
    try {
      if (chromeCast.isConnected()) {
        chromeCast.disconnect();
        chromeCast.connect();
      }
      chromeCast.load(url);
      chromeCast.play();
    } catch (Throwable e) {
      handleIOException(e);
    }
  }

  public void pause() {
    try {
      chromeCast.pause();
    } catch (IOException e) {
      handleIOException(e);
    }
  }

  public void volumeUp() {
    try {
      final Status status = chromeCast.getStatus();
      chromeCast.setVolume(status.volume.level + 0.01f);
    } catch (IOException e) {
      handleIOException(e);
    }
  }

  public void volumeDown() {
    try {
      final Status status = chromeCast.getStatus();
      chromeCast.setVolume(status.volume.level - 0.01f);
    } catch (IOException e) {
      handleIOException(e);
    }
  }

  private void handleIOException(Throwable e) {
    LOGGER.log(Level.INFO, e.getMessage(), e);
    throw new RuntimeException(e);
  }
}
