package org.conversor.gui;


import org.conversor.gui.colors.PalleteColor;
import org.conversor.gui.components.KeyButton;
import org.conversor.gui.components.RoundBorderTable;
import org.conversor.gui.components.ScreenPanel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;

class Widgets {
  private final Font openSan;
  private JLabel[] num;
  private JTextField value;
  private JButton currencySelectButton;
  private JTable table;
  private JLabel currency;
  private JButton recordButton;

  protected Widgets() {

    try {
      openSan = Font.createFont(Font.TRUETYPE_FONT, new File(
              "src/main/java/org/conversor/gui/fonts/OpenSans-VariableFont_wdth,wght.ttf")
      );
      GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
      graphicsEnvironment.registerFont(openSan);

    } catch (FontFormatException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  public JButton getRecordButton() {
    return recordButton;
  }

  public JTable getTable() {
    return table;
  }

  public JLabel[] getNum() {
    return num;
  }

  public JLabel getCurrency() {
    return currency;
  }

  public JTextField getValue() {
    return value;
  }

  public JButton getCurrencySelectButton() {
    return currencySelectButton;
  }

  protected JLabel title(String title) {
    Image icon = Util.getImage("src/main/java/org/conversor/gui/image/icon.png");
    icon = icon.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
    JLabel mainTitle = new JLabel(STR."\{title}   ", new ImageIcon(icon), SwingConstants.CENTER);
    mainTitle.setFont(openSan.deriveFont(Font.BOLD, 18));
    mainTitle.setForeground(PalleteColor.FOREGROUND);
    return mainTitle;
  }

  protected JLabel titleNotIcon(String title, int width, int height) {
    JLabel mainTitle = new JLabel(STR."\{title}   ", SwingConstants.LEFT);
    mainTitle.setPreferredSize(new Dimension(width, height));
    mainTitle.setFont(openSan.deriveFont(Font.BOLD, 18));
    mainTitle.setForeground(PalleteColor.FOREGROUND);
    return mainTitle;
  }

  protected JPanel record() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    panel.setPreferredSize(new Dimension(230,28));
    panel.setOpaque(false);
    Image icon = Util.getImage("src/main/java/org/conversor/gui/image/record_unfocused.png");
    JButton recordButton = new JButton("Historial");
    this.recordButton = recordButton;
    recordButton.setBorder(null);
    recordButton.setContentAreaFilled(false);
    recordButton.setIcon(new ImageIcon(icon.getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
    recordButton.setPreferredSize(new Dimension(90, 20));
    recordButton.setFont(openSan.deriveFont(Font.BOLD, 14));
    recordButton.setForeground(PalleteColor.DARK_GREY);
    recordButton.setFocusPainted(false);
    icon = Util.getImage("src/main/java/org/conversor/gui/image/record.png");
    recordButton.setRolloverIcon(new ImageIcon(icon.getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
    panel.add(recordButton);
    return panel;
  }

  protected ScreenPanel Display(String pos) {
    Font digital7;
    try {
      digital7 = Font.createFont(Font.TRUETYPE_FONT,
              new File("src/main/java/org/conversor/gui/fonts/digital-7.ttf"));
      GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
      graphicsEnvironment.registerFont(digital7);
    } catch (FontFormatException | IOException e) {
      throw new RuntimeException(e);
    }

    ScreenPanel screenPanel = new ScreenPanel(220, 70, pos);

    currency = new JLabel("COP");
    currency.setFont(digital7.deriveFont(Font.PLAIN, 16));
    currency.setForeground(PalleteColor.FOREGROUND);
    currency.setPreferredSize(new Dimension(30, 20));
    currency.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, PalleteColor.FOREGROUND));
    Util.addContainer(screenPanel, currency, 0, 0, 1);

    final JButton select = getSelect();
    currencySelectButton = select;
    Util.addContainer(screenPanel, select, 1, 0, 1);

    value = new JTextField("0.0");
    value.setOpaque(false);
    value.setBorder(null);
    value.setEditable(false);
    value.setAutoscrolls(false);
    value.setDragEnabled(false);
    value.setFocusable(false);
    value.setFont(digital7.deriveFont(Font.PLAIN, 35));
    value.setPreferredSize(new Dimension(210, 40));
    value.setHorizontalAlignment(SwingConstants.RIGHT);
    value.setForeground(PalleteColor.FOREGROUND);
    Util.addContainer(screenPanel, value, 0, 1, 4);
    return screenPanel;
  }

  private static JButton getSelect() {
    JButton select = new JButton() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(PalleteColor.FOREGROUND);
        graphics2D.setRenderingHints(
                new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        );
        graphics2D.fillPolygon(
                new int[]{0, getWidth() / 2 - 5, getWidth() - 10}, new int[]{5, getHeight() - 10, 5}, 3)
        ;
      }
    };
    select.setPreferredSize(new Dimension(20, 20));
    select.setContentAreaFilled(false);
    select.setFocusPainted(false);
    select.setBorderPainted(false);
    return select;
  }

  protected JPanel keyMap() {
    JPanel keyMapPanel = new JPanel();
    keyMapPanel.setOpaque(false);
    String numericKey[] = {
            "7", "8", "9", "MR",
            "4", "5", "6", "C",
            "1", "2", "3", "AC",
            "0", "."
    };

    JPanel numericPanel = new JPanel(new GridBagLayout());
    numericPanel.setOpaque(false);
    KeyButton[] key = new KeyButton[14];
    num = new JLabel[14];
    int count = 0;
    for (int i = 0; i < 4; i++) {
      key[count] = new KeyButton(52, 45, PalleteColor.CORAL);
      switch (numericKey[count]) {
        case "0" -> {
          Util.addContainer(numericPanel, key[count], 0, i, 2, 1);
        }
        case "AC" -> {
          Util.addContainer(numericPanel, key[count], 0, i + 1, 1, 1);
        }
        case "MR" -> {
          Util.addContainer(numericPanel, key[count], 0, i, 1, 2);
        }
        default -> {
          key[count] = new KeyButton(52, 45, PalleteColor.DARK_CORAL);
          switch (numericKey[count]) {
            case "." -> Util.addContainer(numericPanel, key[count], 1, i, 1, 1);
            case "C" -> Util.addContainer(numericPanel, key[count], 0, i + 1, 1, 1);
            default -> Util.addContainer(numericPanel, key[count], 0, i, 1, 1);
          }
        }
      }
      num[count] = new JLabel(numericKey[count]);
      num[count].setFont(openSan.deriveFont(Font.BOLD, 18));
      num[count].setForeground(PalleteColor.FOREGROUND);
      num[count].setHorizontalAlignment(SwingConstants.CENTER);
      key[count].add(num[count], BorderLayout.CENTER);
      ++count;
      if (count != numericKey.length) {

        key[count] = new KeyButton(52, 45, PalleteColor.CORAL);
        switch (numericKey[count]) {
          case "0" -> {
            Util.addContainer(numericPanel, key[count], 1, i, 2, 1);
          }
          case "AC" -> {
            Util.addContainer(numericPanel, key[count], 1, i + 1, 1, 1);
          }
          case "MR" -> {
            Util.addContainer(numericPanel, key[count], 1, i, 1, 2);
          }
          default -> {
            key[count] = new KeyButton(52, 45, PalleteColor.DARK_CORAL);
            switch (numericKey[count]) {
              case "." -> Util.addContainer(numericPanel, key[count], 1 + 1, i, 1, 1);
              case "C" -> Util.addContainer(numericPanel, key[count], 1, i + 1, 1, 1);
              default -> Util.addContainer(numericPanel, key[count], 1, i, 1, 1);
            }
          }
        }
        num[count] = new JLabel(numericKey[count]);
        num[count].setFont(openSan.deriveFont(Font.BOLD, 18));
        num[count].setForeground(PalleteColor.FOREGROUND);
        num[count].setHorizontalAlignment(SwingConstants.CENTER);
        key[count].add(num[count], BorderLayout.CENTER);
        ++count;
        if (count != numericKey.length) {

          key[count] = new KeyButton(52, 45, PalleteColor.CORAL);
          switch (numericKey[count]) {
            case "0" -> {
              Util.addContainer(numericPanel, key[count], 2, i, 2, 1);
            }
            case "AC" -> {
              Util.addContainer(numericPanel, key[count], 2, i + 1, 1, 1);
            }
            case "MR" -> {
              Util.addContainer(numericPanel, key[count], 2, i, 1, 2);
            }
            default -> {
              key[count] = new KeyButton(52, 45, PalleteColor.DARK_CORAL);
              switch (numericKey[count]) {
                case "." -> Util.addContainer(numericPanel, key[count], 2 + 1, i, 1, 1);
                case "C" -> Util.addContainer(numericPanel, key[count], 2, i + 1, 1, 1);
                default -> Util.addContainer(numericPanel, key[count], 2, i, 1, 1);
              }
            }
          }
          num[count] = new JLabel(numericKey[count]);
          num[count].setFont(openSan.deriveFont(Font.BOLD, 18));
          num[count].setForeground(PalleteColor.FOREGROUND);
          num[count].setHorizontalAlignment(SwingConstants.CENTER);
          key[count].add(num[count], BorderLayout.CENTER);
          ++count;
          if (count != numericKey.length) {

            key[count] = new KeyButton(52, 45, PalleteColor.CORAL);
            switch (numericKey[count]) {
              case "0" -> {
                Util.addContainer(numericPanel, key[count], 3, i, 2, 1);
              }
              case "AC" -> {
                Util.addContainer(numericPanel, key[count], 3, i + 1, 1, 1);
              }
              case "MR" -> {
                Util.addContainer(numericPanel, key[count], 3, i, 1, 2);
              }
              default -> {
                key[count] = new KeyButton(52, 45, PalleteColor.DARK_CORAL);
                switch (numericKey[count]) {
                  case "." -> Util.addContainer(numericPanel, key[count], 3 + 1, i, 1, 1);
                  case "C" -> Util.addContainer(numericPanel, key[count], 3, i + 1, 1, 1);
                  default -> Util.addContainer(numericPanel, key[count], 3, i, 1, 1);
                }
              }
            }
            num[count] = new JLabel(numericKey[count]);
            num[count].setFont(openSan.deriveFont(Font.BOLD, 18));
            num[count].setForeground(PalleteColor.FOREGROUND);
            num[count].setHorizontalAlignment(SwingConstants.CENTER);
            key[count].add(num[count], BorderLayout.CENTER);
            ++count;
          }
        }
      }
    }
    Util.addContainer(keyMapPanel, numericPanel);
    return keyMapPanel;
  }

  protected JScrollPane currenciesTable(TableModel model, int width, int height) {
    RoundBorderTable roundBorderTable = new RoundBorderTable(model, width, height, openSan);
    roundBorderTable.getTable().getColumnModel().getColumn(0).setMaxWidth(width*20/100);
    roundBorderTable.getTable().getColumnModel().getColumn(1).setMaxWidth(width*65/100);
    table = roundBorderTable.getTable();
    return roundBorderTable;
  }
  protected JScrollPane recordTable(TableModel model, int width, int height){
    RoundBorderTable roundBorderTable = new RoundBorderTable(model, width, height, openSan);
    roundBorderTable.getTable().getColumnModel().getColumn(0).setMaxWidth(width*10/100);
    roundBorderTable.getTable().getColumnModel().getColumn(1).setPreferredWidth(1);
    roundBorderTable.getTable().getColumnModel().getColumn(2).setMaxWidth(width*10/100);
    roundBorderTable.getTable().getColumnModel().getColumn(3).setPreferredWidth(1);

    return  roundBorderTable;
  }
}