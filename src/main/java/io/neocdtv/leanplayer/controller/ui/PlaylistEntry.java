/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.leanplayer.controller.ui;

/**
 * PlaylistEntry.
 *
 * @author xix
 * @since 22.12.17
 */
public class PlaylistEntry {
  private final String name;
  private final String path;

  public PlaylistEntry(String fileName, String filePath) {
    this.name = fileName;
    this.path = filePath;
  }

  public String getPath() {
    return path;
  }

  @Override
  public String toString() {
    return name;
  }
}
