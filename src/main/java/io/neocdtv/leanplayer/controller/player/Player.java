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

// TODO: is this interface required
public interface Player {
  void play(final String url);

  void pause() throws PlayerException;

  void next() throws PlayerException;

  void volumeUp() throws PlayerException;

  void volumeDown() throws PlayerException;
}
