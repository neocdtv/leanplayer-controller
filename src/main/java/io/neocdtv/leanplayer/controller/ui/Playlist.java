/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.leanplayer.controller.ui;

import io.neocdtv.service.UrlBuilder;
import org.apache.commons.io.IOUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PlaylistUI.
 *
 * @author xix
 * @since 22.12.17
 */

@ApplicationScoped
public class Playlist {

  private final static Logger LOGGER = Logger.getLogger(io.neocdtv.leanplayer.controller.ui.Playlist.class.getName());
  private PlaylistUI playlistUI = new PlaylistUI();

  public PlaylistUI getPlaylistUI() {
    return playlistUI;
  }

  public static class PlaylistUI extends JList<PlaylistEntry> {
    private int playingIndex = 0;

    public PlaylistUI() {
      super(new DefaultListModel<>());
      setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      setDragEnabled(true);
      setTransferHandler(new PlaylistTransferHandler());
      setDropMode(DropMode.INSERT);

      addKeyListener(new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
          LOGGER.log(Level.INFO, "keyTyped: {0}:", e.getKeyCode());
        }

        @Override
        public void keyPressed(KeyEvent e) {
          LOGGER.log(Level.INFO, "keyPressed: {0}:", e.getKeyCode());
          if ((e.getKeyCode()
              == KeyEvent.VK_V) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
            try {
              final StringReader valueFromClipboardReader = getValueFromClipboard(DataFlavor.plainTextFlavor);
              final String valueFromString = IOUtils.toString(valueFromClipboardReader);
              LOGGER.log(Level.INFO, "fromClipBoard: {0}:", valueFromString);
              addElement(valueFromString);
            } catch (IOException ex) {
              LOGGER.log(Level.SEVERE, null, ex);
            }
          }
          if ((e.getKeyCode()
              == KeyEvent.VK_A) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
            selectAll();
          }
          if (e.getKeyCode()
              == KeyEvent.VK_DELETE) {
            removeSelected();
          }
        }

        @Override
        public void keyReleased(KeyEvent e) {
          LOGGER.log(Level.INFO, "keyReleased: {0}:", e.getKeyCode());
        }
      });
    }

    // TODO: is this right? looks strange
    @Override
    public DefaultListModel<PlaylistEntry> getModel() {
      return (DefaultListModel<PlaylistEntry>) super.getModel();
    }

    public void addElement(final String url) {
      getModel().addElement(new PlaylistEntry(url, url));
    }

    public String getSelectedTrackUrl() {
      LOGGER.log(Level.INFO, "getting track url for current playlist entry...");

      String selectedTrackUrl = null;
      final int selectedIndex = getSelectedIndex();

      LOGGER.log(Level.INFO, "selected index {0} ", selectedIndex);
      if (selectedIndex >= 0) {
        selectedTrackUrl = getModel().get(selectedIndex).getPath();
        playingIndex = selectedIndex;
      }
      LOGGER.log(Level.INFO, "selected playlist track {0}", selectedTrackUrl);
      ensureIndexIsVisible(selectedIndex);
      return UrlBuilder.build(selectedTrackUrl);
    }

    public String getNextSelectedTrackUrl() {
      LOGGER.log(Level.INFO, "getting track url for next selected playlist entry...");
      String nextSelectedTrackUrl = null;
      int selectedIndex = getSelectedIndex();

      if (selectedIndex >= 0) {
        if (selectedIndex == getModel().getSize()) {
          selectedIndex = 0;
        } else {
          selectedIndex++;
        }
        playingIndex = selectedIndex;
        setSelectedIndex(selectedIndex);
        nextSelectedTrackUrl = getModel().get(selectedIndex).getPath();
      }
      LOGGER.log(Level.INFO, "next playlist track {0}", nextSelectedTrackUrl);
      ensureIndexIsVisible(selectedIndex);
      return UrlBuilder.build(nextSelectedTrackUrl);
    }

    public String getNextTrackUrl() {
      LOGGER.log(Level.INFO, "getting track url for next playlist entry...");
      String nextTrackUrl = null;
      if (getModel().getSize() > 0 && playingIndex >= 0) {
        playingIndex++;
        if (playingIndex >= getModel().getSize()) {
          playingIndex = 0;
        }
        setSelectedIndex(playingIndex);
        nextTrackUrl = getModel().get(playingIndex).getPath();
      }
      ensureIndexIsVisible(playingIndex);
      return UrlBuilder.build(nextTrackUrl);
    }

    void selectAll() {
      final DefaultListModel<PlaylistEntry> model = getModel();
      for (int i = 0; i < model.getSize(); i++) {
        int[] indicies = new int[model.getSize()];
        indicies[i] = i;
        setSelectedIndices(indicies);
      }
    }

    void removeSelected() {
      final DefaultListModel<PlaylistEntry> model = getModel();
      final List<PlaylistEntry> selectedValuesList = getSelectedValuesList();
      for (PlaylistEntry entry : selectedValuesList) {
        model.removeElement(entry);
      }
    }

    private <T> T getValueFromClipboard(final DataFlavor flavor) {
      T valueFromClipboard = null;
      try {
        valueFromClipboard = (T) Toolkit.getDefaultToolkit().getSystemClipboard().getData(flavor);
      } catch (UnsupportedFlavorException | IOException ex) {
        LOGGER.log(Level.SEVERE, null, ex);
      }
      return valueFromClipboard;
    }
  }
}
