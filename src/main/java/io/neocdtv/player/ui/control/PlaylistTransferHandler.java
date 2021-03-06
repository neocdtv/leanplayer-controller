/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.player.ui.control;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 * ListTransferHandler.java is used by the DropDemo example.
 */

import io.neocdtv.player.ui.model.PlaylistEntry;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlaylistTransferHandler extends TransferHandler {

  private final static Logger LOGGER = Logger.getLogger(LookAndFeel.class.getName());

  private int[] indices = null;
  private int addIndex = -1; //Location where items were added
  private int addCount = 0;  //Number of items added.

  @Override
  public boolean canImport(TransferSupport info) {
    return info.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
  }

  @Override
  protected Transferable createTransferable(JComponent c) {
    return new StringSelection(exportString(c));
  }

  @Override
  public int getSourceActions(JComponent c) {
    return TransferHandler.COPY_OR_MOVE;
  }

  @Override
  public boolean importData(TransferSupport info) {
    if (!info.isDrop()) {
      return false;
    }

    //TODO: how make it type safe?
    JList list = (JList) info.getComponent();
    DefaultListModel<PlaylistEntry> listModel = (DefaultListModel) list.getModel();
    JList.DropLocation dropLocation = (JList.DropLocation) info.getDropLocation();

    // Get the string that is being dropped.
    Transferable transferable = info.getTransferable();
    List<File> data;
    try {
      data = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
      if (data.size() == 1 && isPlaylist(data.get(0).getName())) {
        final File file = data.get(0);
        final List<String> pathsFromPlaylist = Files.readAllLines(Paths.get(file.toURI()));
        for (String pathFromPlaylist: pathsFromPlaylist) {
          listModel.addElement(buildEntry(new File(pathFromPlaylist)));
        }
      } else {
        for (File file : data) {
          if (file.isDirectory()) {
            final boolean recursive = true;
            final String[] fileExtensionFilter = null;
            final List<File> listFiles = Arrays.asList(FileUtils.convertFileCollectionToFileArray(FileUtils.listFiles(file, fileExtensionFilter, recursive)));
            for (File o : listFiles) {
              listModel.addElement(buildEntry(o));
            }
          } else {
            listModel.addElement(buildEntry(file));
          }
        }
      }
    } catch (UnsupportedFlavorException | IOException ex) {
      LOGGER.log(Level.SEVERE, null, ex);
      return false;
    }
    return true;
  }

  private boolean isPlaylist(final String name) {
    final String[] fileNameSplit = name.split("\\.");
    if (fileNameSplit.length == 2) {
      return fileNameSplit[1].equals("pls");
    }
    return false;
  }

  private PlaylistEntry buildEntry(final File file) {
    final PlaylistEntry entry = PlaylistEntry.create(file.getName(), file.getAbsolutePath());
    LOGGER.info("entry: " + entry.getPath());
    return entry;
  }

  @Override
  protected void exportDone(JComponent c, Transferable data, int action) {
    cleanup(c, action == TransferHandler.MOVE);
  }

  protected String exportString(JComponent c) {
    JList list = (JList) c;
    indices = list.getSelectedIndices();
    List values = list.getSelectedValuesList();

    StringBuilder buff = new StringBuilder();

    for (int i = 0; i < values.size(); i++) {
      Object val = values.get(i);
      buff.append(val == null ? "" : val.toString());
      if (i != values.size() - 1) {
        buff.append("\n");
      }
    }

    return buff.toString();
  }

  //Take the incoming string and wherever there is a
  //newline, break it into a separate item in the list.
  // TODO: do i need this method?
  protected void importString(JComponent c, String str) {
    JList target = (JList) c;
    DefaultListModel listModel = (DefaultListModel) target.getModel();
    int index = target.getSelectedIndex();

    //Prevent the user from dropping data back on itself.
    //For example, if the user is moving items #4,#5,#6 and #7 and
    //attempts to insert the items after item #5, this would
    //be problematic when removing the original items.
    //So this is not allowed.
    if (indices != null && index >= indices[0] - 1
        && index <= indices[indices.length - 1]) {
      indices = null;
      return;
    }

    int max = listModel.getSize();
    if (index < 0) {
      index = max;
    } else {
      index++;
      if (index > max) {
        index = max;
      }
    }
    addIndex = index;
    String[] values = str.split("\n");
    addCount = values.length;
    for (String value : values) {
      listModel.add(index++, value);
    }
  }

  //If the remove argument is true, the drop has been
  //successful and it's time to remove the selected items
  //from the list. If the remove argument is false, it
  //was a Copy operation and the original list is left
  //intact.
  protected void cleanup(JComponent c, boolean remove) {
    if (remove && indices != null) {
      JList source = (JList) c;
      DefaultListModel model = (DefaultListModel) source.getModel();
      //If we are moving items around in the same list, we
      //need to adjust the indices accordingly, since those
      //after the insertion point have moved.
      if (addCount > 0) {
        for (int i = 0; i < indices.length; i++) {
          if (indices[i] > addIndex) {
            indices[i] += addCount;
          }
        }
      }
      for (int i = indices.length - 1; i >= 0; i--) {
        model.remove(indices[i]);
      }
    }
    indices = null;
    addCount = 0;
    addIndex = -1;
  }
}
