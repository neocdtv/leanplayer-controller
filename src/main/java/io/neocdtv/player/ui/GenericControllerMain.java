package io.neocdtv.player.ui;

import io.neocdtv.player.ui.discovery.RendererDiscoveryConfigurator;
import io.neocdtv.player.ui.ui.ControllerUI;
import io.neocdtv.player.ui.ui.LookAndFeelUI;
import io.neocdtv.service.StreamingService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.events.ContainerInitialized;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * GenericControllerMain.
 */
public class GenericControllerMain {

  private final static Logger LOGGER = Logger.getLogger(GenericControllerMain.class.getName());


  @Inject
  private ControllerUI controllerUI;

  @Inject
  private RendererDiscoveryConfigurator rendererDiscoveryConfigurator;

  public static void main(String[] args) throws Exception {
    try {
      LookAndFeelUI.configure();
      StreamingService.startIt();
      configureCdi();
    } catch (RuntimeException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);

    }
  }

  public void main(@Observes ContainerInitialized event) {
    rendererDiscoveryConfigurator.configurePlayerType().start();
  }

  private static void configureCdi() {
    Weld weld = new Weld();
    weld.initialize();
  }
}
