/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.player.ui.model;

import java.util.UUID;

/**
 * PlaylistEntry.
 *
 * @author xix
 * @since 22.12.17
 */
public class PlaylistEntry {
  private final String name;
  private final String path;
  private UUID uuid;

  private PlaylistEntry(final String name, final String path) {
    this.name = name;
    this.path = path;
    this.uuid = UUID.randomUUID();
  }

  public String getPath() {
    return path;
  }

  @Override
  public String toString() {
    return name;
  }

  public UUID getUuid() {
    return uuid;
  }

  public static PlaylistEntry create(final String name, final String path) {
    return new PlaylistEntry(name, path);
  }
}
