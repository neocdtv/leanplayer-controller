package io.neocdtv.player.ui.control;

import io.neocdtv.commons.network.NetworkUtil;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

/**
 * ActiveAddresses.
 *
 * @author xix
 * @since 22.05.18
 */
public class ActiveAddresses {

  private List<InetAddress> addresses;

  @Inject
  private NetworkUtil networkUtil;

  @PostConstruct
  public void prepareAddresses() throws SocketException {
    addresses = networkUtil.findIpv4AddressForActiveInterfaces();
  }

  public List<InetAddress> getAddresses() {
    return addresses;
  }
}
