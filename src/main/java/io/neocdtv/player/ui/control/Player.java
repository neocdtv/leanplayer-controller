/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.player.ui.control;

/**
 * Player.
 *
 * @author xix
 * @since 22.12.17
 */

public interface Player {
  void play(final String url);

  void pause();

  void volumeUp();

  void volumeDown();
}
