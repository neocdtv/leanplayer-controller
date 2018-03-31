package io.neocdtv.player.ui.ui;

import io.neocdtv.player.ui.control.worker.Next;
import io.neocdtv.player.ui.control.worker.Pause;
import io.neocdtv.player.ui.control.worker.Play;
import io.neocdtv.player.ui.control.worker.VolumeDown;
import io.neocdtv.player.ui.control.worker.VolumeUp;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ControlButtonsUI.
 *
 * @author xix
 * @since 11.03.18
 */
public class ControlButtonsUI {

  private final static Logger LOGGER = Logger.getLogger(ControlButtonsUI.class.getName());

  @Inject
  private Play play;

  @Inject
  private Pause pause;

  @Inject
  private Next next;

  @Inject
  private VolumeDown volumeDown;

  @Inject
  private VolumeUp volumeUp;


  JPanel buildButtonPanel() {
    JPanel buttonPanel = new JPanel(new GridLayout(1, 6));
    buttonPanel.add(buildPlayButton());
    buttonPanel.add(buildPauseButton());
    buttonPanel.add(buildNextButton());
    buttonPanel.add(buildVolumeDownButton());
    buttonPanel.add(buildVolumeUpButton());
    return buttonPanel;
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
        volumeDown.execute();
      }
    });
    return button;
  }

  public JButton buildVolumeUpButton() {
    JButton button = new JButton("+");
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        LOGGER.log(Level.INFO, "actionPerformed");
        volumeUp.execute();
      }
    });
    return button;
  }
}
