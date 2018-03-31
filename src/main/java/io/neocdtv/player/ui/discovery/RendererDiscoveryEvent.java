package io.neocdtv.player.ui.discovery;

import io.neocdtv.player.ui.control.Player;

/**
 * RendererDiscoveryEvent.
 *
 * @author xix
 * @since 10.03.18
 */
public class RendererDiscoveryEvent {

    // TODO: add ID?
    private final String name;
    private final Player player;

    public RendererDiscoveryEvent(final String name, final Player player) {
      this.name = name;
      this.player = player;
    }

  public String getName() {
    return name;
  }

  public Player getPlayer() {
    return player;
  }
}