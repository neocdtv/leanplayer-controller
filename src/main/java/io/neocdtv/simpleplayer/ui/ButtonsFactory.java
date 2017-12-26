/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.simpleplayer.ui;

import io.neocdtv.simpleplayer.worker.NextWorker;
import io.neocdtv.simpleplayer.worker.PauseWorker;
import io.neocdtv.simpleplayer.worker.PlayWorker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 * @author xix
 */
public class ButtonsFactory {
  private final static Logger LOGGER = Logger.getLogger(PlaylistUI.class.getName());

  private static JButton PLAY_BUTTON;
  private static JButton PAUSE_BUTTON;
  private static JButton NEXT_BUTTON;
  private static JButton VOLUME_DOWN_BUTTON;
  private static JButton VOLUME_UP_BUTTON;

  private ButtonsFactory() {
  }

  public static JButton playButtonInstance() {
    if (PLAY_BUTTON == null) {
      PLAY_BUTTON = new JButton("play");
      PLAY_BUTTON.addActionListener(actionEvent -> {
        LOGGER.log(Level.INFO, "actionPerformed on playButton");
        new PlayWorker().execute();
      });
    }
    return PLAY_BUTTON;
  }

  public static JButton pauseButtonInstance() {
    if (PAUSE_BUTTON == null) {
      PAUSE_BUTTON = new JButton("pause");
      PAUSE_BUTTON.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          LOGGER.log(Level.INFO, "actionPerfomed on pauseButton");
          new PauseWorker().execute();
        }
      });
    }
    return PAUSE_BUTTON;
  }

  public static JButton nextButtonInstance() {
    if (NEXT_BUTTON == null) {
      NEXT_BUTTON = new JButton("next");
      NEXT_BUTTON.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          LOGGER.log(Level.INFO, "actionPerfomed on nextButton");
          new NextWorker().execute();
        }
      });
    }
    return NEXT_BUTTON;
  }

  public static JButton volumeDownButtonInstance() {
    if (VOLUME_DOWN_BUTTON == null) {
      VOLUME_DOWN_BUTTON = new JButton("-");
      VOLUME_DOWN_BUTTON.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          LOGGER.log(Level.INFO, "actionPerfomed on volumeDownButton");
          //new VolumeDownWorker().execute();
        }
      });
    }
    return VOLUME_DOWN_BUTTON;
  }

  public static JButton volumeUpButtonInstance() {
    if (VOLUME_UP_BUTTON == null) {
      VOLUME_UP_BUTTON = new JButton("+");
      VOLUME_UP_BUTTON.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          LOGGER.log(Level.INFO, "actionPerfomed on volumeUpButton");
          //new VolumeUpWorker().execute();
        }
      });
    }
    return VOLUME_UP_BUTTON;
  }

  public static void changeButtonsStatus(final boolean enabled) {
    final String actionName = enabled ? "enabling" : "disabling";
    LOGGER.log(Level.INFO, "{0} buttons...", actionName);
    PLAY_BUTTON.setEnabled(enabled);
  }
}
