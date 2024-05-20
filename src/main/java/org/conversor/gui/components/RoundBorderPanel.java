package org.conversor.gui.components;

import javax.swing.*;
import java.awt.*;

public class RoundBorderPanel extends JPanel {
  private final Image bgImage = null;
  private final Color fg;
  private final float thickness;

  public RoundBorderPanel(Color borderColor, float thickness, int width, int height) {
    this.fg = borderColor;
    this.thickness = thickness;
    setOpaque(false);
    setPreferredSize(new Dimension(width, height));
    setBorder(null);
    setLayout(new GridBagLayout());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D graphics2D = (Graphics2D) g;
    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    graphics2D.setColor(fg);
    graphics2D.setStroke(new BasicStroke(thickness));
    graphics2D.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 40, 40);
  }
}

