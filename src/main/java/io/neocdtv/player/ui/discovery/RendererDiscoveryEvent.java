package io.neocdtv.player.ui.discovery;

import io.neocdtv.player.ui.control.Player;

/**
 * RendererDiscoveryEvent.
 *
 * @author xix
 * @since 10.03.18
 */
public class RendererDiscoveryEvent {

    private final String name;
    private final String id;
    private final Player player;

  public RendererDiscoveryEvent(final String name, final String id, final Player player) {
      this.name = name;

      this.id = id;
      this.player = player;
    }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }

  public Player getPlayer() {
    return player;
  }
}