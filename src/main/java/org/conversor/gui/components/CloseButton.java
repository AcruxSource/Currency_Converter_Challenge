package org.conversor.gui.components;


import org.conversor.gui.Util;

import javax.swing.*;
import java.awt.*;

public class CloseButton extends JButton {

  public CloseButton(int width, int height) {
    setBorder(null);
    setFocusPainted(false);
    setContentAreaFilled(false);
    setPreferredSize(new Dimension(width, height));
    Image icon = Util.getImage("src/main/java/org/conversor/gui/image/close_unfocused.png");
    setIcon(new ImageIcon(icon));
    icon = Util.getImage("src/main/java/org/conversor/gui/image/close.png");
    setRolloverIcon(new ImageIcon(icon));
    setHorizontalAlignment(SwingConstants.RIGHT);
  }
}
