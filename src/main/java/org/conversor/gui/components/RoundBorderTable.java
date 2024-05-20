package org.conversor.gui.components;



import org.conversor.gui.colors.PalleteColor;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;

public class RoundBorderTable extends JScrollPane {
  JTable table;

  public JTable getTable() {
    return table;
  }

  public RoundBorderTable(TableModel tableModel, int width, int height, Font font) {
    table = new JTable(tableModel);
    table.setOpaque(false);
    table.setBorder(null);
    table.setShowGrid(false);
    table.setForeground(PalleteColor.GREY);
    table.setFont(font.deriveFont(Font.PLAIN, 14));
    table.setFocusable(false);
    table.setDragEnabled(false);
    table.setSelectionForeground(PalleteColor.FOREGROUND);
    ((DefaultTableCellRenderer) table.getDefaultRenderer(Object.class)).setOpaque(false);

    setViewportView(table);
    setOpaque(false);
    setPreferredSize(new Dimension(width, height));

    getVerticalScrollBar().setUI(new BasicScrollBarUI() {
      @Override
      protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(PalleteColor.DARK);
        g.fillRoundRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height, 20, 20);
      }

      @Override
      protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        g.setColor(PalleteColor.DARK_CORAL);
        g.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height + 12, 12, 12);
      }


      @Override
      protected JButton createDecreaseButton(int orientation) {
        JButton button = new JButton();
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorder(null);
        return button;
      }

      @Override
      protected JButton createIncreaseButton(int orientation) {
        JButton button = new JButton();
        button.setOpaque(false);
        button.setBorder(null);
        button.setContentAreaFilled(false);
        return button;
      }
    });

    setLayout(new ScrollPaneLayout() {
      @Override
      public void layoutContainer(Container parent) {
        JScrollPane jScrollPane = (JScrollPane) parent;
        Rectangle viewPortRectangle = jScrollPane.getBounds();
        Insets insets = parent.getInsets();
        viewPortRectangle.x = insets.left + 10;
        viewPortRectangle.y = insets.top + 5;
        viewPortRectangle.width -= insets.left + insets.right + 18;
        viewPortRectangle.height -= insets.top + insets.bottom + 5;

        Rectangle vsbRectangle = new Rectangle();

        vsbRectangle.width = 12;
        vsbRectangle.height = viewPortRectangle.height;
        vsbRectangle.x = viewPortRectangle.x + viewPortRectangle.width - vsbRectangle.width + 5;
        vsbRectangle.y = viewPortRectangle.y;

        if (viewport != null) {
          viewport.setOpaque(false);
          viewport.setBounds(viewPortRectangle);
        }
        if (vsb != null) {
          vsb.setBounds(vsbRectangle);
          vsb.setOpaque(false);
          vsb.setVisible(true);
        }
      }
    });
  }

  @Override
  protected void paintBorder(Graphics g) {
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D graphics2D = (Graphics2D) g;
    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    graphics2D.setColor(PalleteColor.CORAL);
    graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
  }
}
