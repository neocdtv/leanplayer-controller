package io.neocdtv.simpleplayer.ui;

import org.apache.commons.io.IOUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerUI {

  private final static Logger LOGGER = Logger.getLogger(PlaylistUI.class.getName());
  private final PlaylistUI playList = PlaylistUI.getInstance();
  private static final String PLAYER_TITLE = "Zenplayer-UI";

  public void init() {
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
    JComboBox<CombolistEntry> comboBox = new JComboBox<>(ComboBoxFactory.instance());
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
    buttonPanel.add(ButtonsFactory.playButtonInstance());
    buttonPanel.add(ButtonsFactory.pauseButtonInstance());
    buttonPanel.add(ButtonsFactory.nextButtonInstance());
    buttonPanel.add(ButtonsFactory.volumeDownButtonInstance());
    buttonPanel.add(ButtonsFactory.volumeUpButtonInstance());
    return buttonPanel;
  }

  private JScrollPane buildPlaylist() {
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setPreferredSize(new Dimension(300, 400));
    scrollPane.setViewportView(playList);
    playList.addKeyListener(new KeyListener() {
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
            playList.addElement(valueFromString);
          } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
          }
        }
        if ((e.getKeyCode()
            == KeyEvent.VK_A) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
          playList.selectAll();
        }
        if (e.getKeyCode()
            == KeyEvent.VK_DELETE) {
          playList.removeSelected();
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        LOGGER.log(Level.INFO, "keyReleased: {0}:", e.getKeyCode());
      }
    });
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    final TitledBorder createTitledBorder = BorderFactory.createTitledBorder("Playlist");
    createTitledBorder.setTitleJustification(TitledBorder.CENTER);
    scrollPane.setBorder(createTitledBorder);
    return scrollPane;
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

  private void defineBehaviourOnWindowClose(JFrame frame) {
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        e.getWindow().dispose();
      }
    });
  }
}
