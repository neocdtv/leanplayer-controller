package io.neocdtv.leanplayer.controller.ui;

import io.neocdtv.leanplayer.controller.worker.Next;
import io.neocdtv.leanplayer.controller.worker.Pause;
import io.neocdtv.leanplayer.controller.worker.Play;

import javax.inject.Inject;
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

  private final static Logger LOGGER = Logger.getLogger(Playlist.class.getName());
  private static final String PLAYER_TITLE = "LeanPlayer Controller";

  @Inject
  private Next next;

  @Inject
  private Play play;

  @Inject
  private Pause pause;

  @Inject
  private Playlist playlist;

  @Inject
  private PlayerSelectionList playerSelectionList;

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
    JComboBox<PlayerSelectionEntry> comboBox = new JComboBox<>(playerSelectionList.getModel());
    devicePanel.add(comboBox);
    return devicePanel;
  }



  private JPanel buildBottomPanel() {
    JPanel panel = new JPanel(new GridLayout(2, 1));
    panel.add(buildButtonPanel());
    JTextField field = new JTextField();
    field.setEnabled(false);
    panel.add(field);
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
    scrollPane.setViewportView(playlist.getPlaylistUI());
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

  public JButton buildPlayButton() {
    JButton button = new JButton("play");
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        LOGGER.log(Level.INFO, "actionPerformed");
        play.execute();
      }
    });
    return button;
  }

  public JButton buildPauseButton() {
    JButton button = new JButton("pause");
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          LOGGER.log(Level.INFO, "actionPerformed");
          pause.execute();
        }
      });
    return button;
  }

  public JButton buildNextButton() {
    JButton button = new JButton("next");
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          LOGGER.log(Level.INFO, "actionPerformed");
          next.execute();
        }
      });
    return button;
  }

  public JButton buildVolumeDownButton() {
    JButton button = new JButton("-");
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          LOGGER.log(Level.INFO, "actionPerformed");
          //new VolumeDownWorker().execute();
        }
      });
    return button;
  }

  public static JButton buildVolumeUpButton() {
    JButton button = new JButton("+");
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          LOGGER.log(Level.INFO, "actionPerformed");
          //new VolumeUpWorker().execute();
        }
      });
    return button;
  }
}
