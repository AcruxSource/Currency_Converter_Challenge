package org.conversor.gui.components;

import javax.swing.*;
import java.awt.*;

public class KeyButton extends JButton {
  protected Color color;

  public KeyButton(int width, int height, Color bgColor){
    color = bgColor;
    setPreferredSize(new Dimension(width,height));
    setBorder(null);
    setFocusPainted(false);
    setContentAreaFilled(false);
    setLayout(new BorderLayout());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D graphics2D = (Graphics2D) g;
    graphics2D.setColor(color);
    graphics2D.fillRoundRect(0,0,getWidth(),getHeight(),10,10);
  }
}
