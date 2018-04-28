/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.player.ui.control.worker;

import io.neocdtv.player.ui.control.Player;
import io.neocdtv.player.ui.model.Playlist;
import io.neocdtv.player.ui.model.PlaylistEntry;
import io.neocdtv.service.UrlBuilder;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * NextWorker.
 *
 * @author xix
 * @since 22.12.17
 */
public class SavePlaylistWorker extends SwingWorker<Void, Void> {

  private final static Logger LOGGER = Logger.getLogger(SavePlaylistWorker.class.getName());
  public static final String LINUX_ENV_HOME_DIR = "HOME";

  private Playlist playlist;

  @Override
  protected Void doInBackground() {
    final StringBuffer playlistBuffer = new StringBuffer();
    final Enumeration<PlaylistEntry> elements = playlist.elements();
    while (elements.hasMoreElements()) {
      final PlaylistEntry playlistEntry = elements.nextElement();
      LOGGER.info("Entry.path: " + playlistEntry.getPath());
      playlistBuffer.append(playlistEntry.getPath() + "\n");
    }
    final File file = new File(System.getenv(LINUX_ENV_HOME_DIR) + "/playlist" + System.currentTimeMillis() + ".pls");
    try {
      java.nio.file.Files.write(
          Paths.get(file.toURI()),
          playlistBuffer.toString().getBytes(StandardCharsets.UTF_8),
          StandardOpenOption.CREATE,
          StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }
    return null;
  }

  public void setPlaylist(Playlist playlist) {
    this.playlist = playlist;
  }
}