package org.conversor.gui.components;


import org.conversor.gui.colors.PalleteColor;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ScreenPanel extends JPanel {

  private final String pos;

  public ScreenPanel(int width, int height, String position) {
    setOpaque(false);
    setPreferredSize(new Dimension(width, height));
    setBorder(null);
    setLayout(new GridBagLayout());
    this.pos = position;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D graphics2D = (Graphics2D) g;

    if (Objects.equals(pos, "TOP")) {
      graphics2D.setColor(PalleteColor.DARK_CORAL);
      Point roundRectPoint_A = new Point(0, 0);
      Point roundRectPoint_B = new Point(getWidth(), getHeight() * 2);
      graphics2D.fillRoundRect(roundRectPoint_A.x, roundRectPoint_A.y, roundRectPoint_B.x, roundRectPoint_B.y, 30, 30);
    } else if (Objects.equals(pos, "BOTTOM")) {
      graphics2D.setColor(PalleteColor.CORAL);
      Point roundRectPoint_A = new Point(0, -10);
      Point roundRectPoint_B = new Point(getWidth(), getHeight() + 10);
      graphics2D.fillRoundRect(roundRectPoint_A.x, roundRectPoint_A.y, roundRectPoint_B.x, roundRectPoint_B.y, 30, 30);
    }
  }
}
