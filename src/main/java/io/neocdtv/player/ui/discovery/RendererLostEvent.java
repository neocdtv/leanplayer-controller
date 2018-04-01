package io.neocdtv.player.ui.discovery;

/**
 * RendererDiscoveryEvent.
 *
 * @author xix
 * @since 10.03.18
 */
public class RendererLostEvent {

  private final String id;

  private RendererLostEvent(final String id) {
    this.id = id;
  }

  public static RendererLostEvent create(final String id) {
    return new RendererLostEvent(id);
  }

  public String getId() {
    return id;
  }
}