/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.simpleplayer.player;

/**
 * @author xix
 */
public interface Player {
  void play(final String url);

  void pause() throws PlayerException;

  void next() throws PlayerException;

  void volumeUp() throws PlayerException;

  void volumeDown() throws PlayerException;
}
