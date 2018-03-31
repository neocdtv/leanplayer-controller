package io.neocdtv.player.ui.ui;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

/**
 * ControllerUI.
 *
 * @author xix
 * @since 09.03.18
 */
public class ControllerUI extends JFrame {

  @Inject
  private RendererListUI rendererListUI;

  @Inject
  private PlaylistUI playlistUI;

  @Inject private ControlButtonsUI controlButtonsUI;

  private final static Logger LOGGER = Logger.getLogger(ControllerUI.class.getName());
  private static final String PLAYER_TITLE = "Controller";

  @PostConstruct
  public void init() {
    setTitle(PLAYER_TITLE);
    defineBehaviourOnWindowClose(this);
    final Container contentPane = getContentPane();
    contentPane.add(buildRendererPanel(), BorderLayout.NORTH);
    contentPane.add(buildPlaylist(), BorderLayout.CENTER);
    contentPane.add(buildControlPanel(), BorderLayout.SOUTH);
    setSize(400, 400);
    setResizable(true);
    setVisible(true);
  }

  private void defineBehaviourOnWindowClose(JFrame frame) {
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        e.getWindow().dispose();
      }
    });
  }

  private JPanel buildRendererPanel() {
    JPanel devicePanel = new JPanel(new GridLayout(1, 1));
    devicePanel.add(rendererListUI);
    return devicePanel;
  }

  private JScrollPane buildPlaylist() {
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setPreferredSize(new Dimension(300, 400));
    scrollPane.setViewportView(playlistUI);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    final TitledBorder createTitledBorder = BorderFactory.createTitledBorder("Playlist");
    createTitledBorder.setTitleJustification(TitledBorder.CENTER);
    scrollPane.setBorder(createTitledBorder);
    return scrollPane;
  }

  private JPanel buildControlPanel() {
    JPanel panel = new JPanel(new GridLayout(2, 1));
    panel.add(controlButtonsUI.buildButtonPanel());
    JTextField field = new JTextField();
    field.setEnabled(false);
    panel.add(field);
    return panel;
  }
}
