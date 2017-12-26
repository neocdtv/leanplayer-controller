/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.leanplayer.controller.player;

/**
 * PlayerException.
 *
 * @author xix
 * @since 22.12.17
 */
// TODO: is this exception required???
public class PlayerException extends Exception {

  public PlayerException(Exception ex) {
    super(ex);
  }
}
