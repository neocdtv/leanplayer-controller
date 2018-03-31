package io.neocdtv.player.ui.ui;

import io.neocdtv.player.ui.control.PlaylistTransferHandler;
import io.neocdtv.player.ui.model.Playlist;
import io.neocdtv.player.ui.model.PlaylistEntry;
import io.neocdtv.player.ui.model.PlaylistSelection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PlaylistUI.
 *
 * @author xix
 * @since 09.03.18
 */
public class PlaylistUI extends JList<PlaylistEntry> {

  private final static Logger LOGGER = Logger.getLogger(PlaylistUI.class.getName());

  @Inject
  private Playlist playlist;

  @Inject
  private PlaylistSelection playlistSelection;

  @Inject
  private PlaylistTransferHandler playlistTransferHandler;

  public PlaylistUI() {
    setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    setDragEnabled(true);
    setDropMode(DropMode.INSERT);
    setKeyListener();
  }

  @PostConstruct
  public void init() {
    setModel(playlist);
    setSelectionModel(playlistSelection);
    setTransferHandler(playlistTransferHandler);
  }

  // TODO: refactor somehow
  private void setKeyListener() {
    addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        LOGGER.log(Level.INFO, "keyTyped: {0}:", e.getKeyCode());
      }

      @Override
      public void keyPressed(KeyEvent e) {
        LOGGER.log(Level.INFO, "keyPressed: {0}:", e.getKeyCode());
        handlePaste(e);
        handleSelectAll(e);
        handleRemoveSelected(e);
      }

      private void handlePaste(KeyEvent e) {
        if ((e.getKeyCode()
            == KeyEvent.VK_V) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
          try {

            final String valueFromString = getValueFromClipboard();
            addElement(valueFromString);
          } catch (IOException | UnsupportedFlavorException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
          }
        }
      }

      private String getValueFromClipboard() throws IOException, UnsupportedFlavorException {
        return (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
      }

      public void addElement(final String url) {
        playlist.addElement(PlaylistEntry.create(url, url));
      }

      private void handleSelectAll(KeyEvent e) {
        if ((e.getKeyCode()
            == KeyEvent.VK_A) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
          selectAll();
        }
      }

      void selectAll() {
        for (int i = 0; i < playlist.getSize(); i++) {
          int[] indicies = new int[playlist.getSize()];
          indicies[i] = i;
          setSelectedIndices(indicies);
        }
      }

      private void handleRemoveSelected(KeyEvent e) {
        if (e.getKeyCode()
            == KeyEvent.VK_DELETE) {
          removeSelected();
        }
      }

      void removeSelected() {
        final java.util.List<PlaylistEntry> selectedValuesList = getSelectedValuesList();
        for (PlaylistEntry entry : selectedValuesList) {
          playlist.removeElement(entry);
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        LOGGER.log(Level.INFO, "keyReleased: {0}:", e.getKeyCode());
      }
    });
  }
}
