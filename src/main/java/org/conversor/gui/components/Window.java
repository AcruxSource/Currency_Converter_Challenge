package org.conversor.gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Window extends JFrame {

  public Window(Color bg, int width, int height, int hGap, int vGap) {
    setSize(width, height);
    setLayout(new FlowLayout(FlowLayout.CENTER, hGap, vGap));
    setResizable(false);
    getContentPane().setBackground(bg);
    setUndecorated(true);
    setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40));
    setLocation(3400, 250);
  }

  @Override
  public void paintComponents(Graphics g) {
    super.paintComponents(g);
    Graphics2D graphics2D = (Graphics2D) g;
    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
  }
}
