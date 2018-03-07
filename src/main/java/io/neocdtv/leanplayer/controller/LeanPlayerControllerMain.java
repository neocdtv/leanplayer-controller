package io.neocdtv.leanplayer.controller;

import io.neocdtv.leanplayer.controller.discovery.DeviceDiscovery;
import io.neocdtv.leanplayer.controller.ui.LookAndFeel;
import io.neocdtv.leanplayer.controller.ui.PlayerUI;
import io.neocdtv.service.StreamingService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.events.ContainerInitialized;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * LeanPlayerControllerMain.
 *
 * @author xix
 * @since 22.12.17
 */
public class LeanPlayerControllerMain {

  @Inject
  private LookAndFeel lookAndFeel;

  @Inject
  private PlayerUI playerUI;

  @Inject
  private DeviceDiscovery deviceDiscovery;

  public static void main(String[] args) throws Exception {
    configureCdi();
  }

  public void main(@Observes ContainerInitialized event) throws Exception {
    lookAndFeel.init();
    playerUI.init();
    StreamingService.start();
    deviceDiscovery.startDiscovery();
    // TODO: what about device discovery on multiple interfaces
    // TODO: enable UrlBuilder.build and add to leanplayer-controller, when sending url to leanplayer-renderer
  }

  private static void configureCdi() {
    Weld weld = new Weld();
    weld.initialize();
  }
}
