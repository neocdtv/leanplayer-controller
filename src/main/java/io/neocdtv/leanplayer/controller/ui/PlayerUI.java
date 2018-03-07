package io.neocdtv.leanplayer.controller.ui;

import io.neocdtv.leanplayer.controller.worker.NextWorker;
import io.neocdtv.leanplayer.controller.worker.PauseWorker;
import io.neocdtv.leanplayer.controller.worker.PlayWorker;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PlayerUI.
 *
 * @author xix
 * @since 22.12.17
 */
public class PlayerUI {

  private final static Logger LOGGER = Logger.getLogger(PlaylistUI.class.getName());
  private final PlaylistUI playList = PlaylistUI.getInstance();
  private static final String PLAYER_TITLE = "LeanPlayer Controller";

  public void startIt() {
    JFrame frame = new JFrame();
    frame.setTitle(PLAYER_TITLE);
    defineBehaviourOnWindowClose(frame);
    final Container contentPane = frame.getContentPane();
    contentPane.add(buildDevicePanel(), BorderLayout.NORTH);
    contentPane.add(buildPlaylist(), BorderLayout.CENTER);
    contentPane.add(buildBottomPanel(), BorderLayout.SOUTH);
    frame.setSize(400, 400);
    frame.setResizable(true);
    frame.setVisible(true);
  }

  private JPanel buildDevicePanel() {
    JPanel devicePanel = new JPanel(new GridLayout(1, 1));
    JComboBox<PlayerSelectionEntry> comboBox = new JComboBox<>(PlayerSelectionListFactory.instance());
    devicePanel.add(comboBox);
    return devicePanel;
  }

  private JPanel buildBottomPanel() {
    JPanel panel = new JPanel(new GridLayout(2, 1));
    panel.add(buildButtonPanel());
    panel.add(PlayerStateFieldFactory.instance());
    return panel;
  }

  private JPanel buildButtonPanel() {
    JPanel buttonPanel = new JPanel(new GridLayout(1, 6));
    buttonPanel.add(buildPlayButton());
    buttonPanel.add(buildPauseButton());
    buttonPanel.add(buildNextButton());
    buttonPanel.add(buildVolumeDownButton());
    buttonPanel.add(buildVolumeUpButton());
    return buttonPanel;
  }

  private JScrollPane buildPlaylist() {
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setPreferredSize(new Dimension(300, 400));
    scrollPane.setViewportView(playList);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    final TitledBorder createTitledBorder = BorderFactory.createTitledBorder("Playlist");
    createTitledBorder.setTitleJustification(TitledBorder.CENTER);
    scrollPane.setBorder(createTitledBorder);
    return scrollPane;
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

  public static JButton buildPlayButton() {
    JButton play = new JButton("play");
    play.addActionListener(actionEvent -> {
        LOGGER.log(Level.INFO, "actionPerformed");
        new PlayWorker().execute();
      });
    return play;
  }

  public JButton buildPauseButton() {
    JButton pause = new JButton("pause");
    pause.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          LOGGER.log(Level.INFO, "actionPerformed");
          new PauseWorker().execute();
        }
      });
    return pause;
  }

  public JButton buildNextButton() {
    JButton next = new JButton("next");
    next.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          LOGGER.log(Level.INFO, "actionPerformed");
          new NextWorker().execute();
        }
      });
    return next;
  }

  public JButton buildVolumeDownButton() {
    JButton volumeDown = new JButton("-");
    volumeDown.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          LOGGER.log(Level.INFO, "actionPerformed");
          //new VolumeDownWorker().execute();
        }
      });
    return volumeDown;
  }

  public static JButton buildVolumeUpButton() {
    JButton volumeUp = new JButton("+");
    volumeUp.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          LOGGER.log(Level.INFO, "actionPerformed");
          //new VolumeUpWorker().execute();
        }
      });
    return volumeUp;
  }
}
