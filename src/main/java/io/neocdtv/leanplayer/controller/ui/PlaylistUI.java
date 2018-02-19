/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.leanplayer.controller.ui;

import io.neocdtv.service.UrlBuilder;

import javax.swing.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PlaylistUI.
 *
 * @author xix
 * @since 22.12.17
 */
public class PlaylistUI extends JList<PlaylistEntry> {

  private static PlaylistUI INSTANCE;

  public static PlaylistUI getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new PlaylistUI();
    }
    return INSTANCE;
  }

  private final static Logger LOGGER = Logger.getLogger(PlaylistUI.class.getName());

  private int playingIndex = 0;

  private PlaylistUI() {
    super(new DefaultListModel<>());
    setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    setDragEnabled(true);
    setTransferHandler(new PlaylistTransferHandler());
    setDropMode(DropMode.INSERT);
  }

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
}
