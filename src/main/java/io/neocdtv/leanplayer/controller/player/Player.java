/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.leanplayer.controller.player;

/**
 * Player.
 *
 * @author xix
 * @since 22.12.17
 */

public interface Player {
  void play(final String url);

  void pause();

  void next();

  void volumeUp();

  void volumeDown();
}
