/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.player.ui.model;

import io.neocdtv.player.ui.control.Player;
import io.neocdtv.player.ui.discovery.RendererDiscoveryEvent;

/**
 * RendererListEntry.
 *
 * @author xix
 * @since 22.12.17
 */
public class RendererListEntry {
  private final String name;
  private final Player player;

  private RendererListEntry(final String name, final Player player) {
    this.name = name;
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }

  @Override
  public String toString() {
    return name;
  }

  public static RendererListEntry create(final String name, final Player player) {
    return new RendererListEntry(name, player);
  }

  public static RendererListEntry fromEvent(final RendererDiscoveryEvent rendererDiscoveryEvent) {
    return create(rendererDiscoveryEvent.getName(), rendererDiscoveryEvent.getPlayer());
  }
}
