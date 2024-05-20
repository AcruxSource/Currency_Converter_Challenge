package org.conversor.gui;



import org.conversor.gui.colors.PalleteColor;
import org.conversor.model.CurrenciesData;
import org.conversor.service.ExchangeRateApi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

class EventListener {
  private Point initPoint;
  protected JFrame eventFrame;
  protected JFrame currenciesWindow;
  protected JButton eventButton;
  protected JTextField[] eventTextField = new JTextField[2];
  protected JLabel eventKey;
  protected JTextField globalTextField;
  protected JButton[] eventSelect = new JButton[2];
  protected JTable eventTable;
  protected JLabel[] eventLabel = new JLabel[2];
  protected CurrenciesData data;
  private final ExchangeRateApi api = new ExchangeRateApi();
  private double rate, mainValue, secondaryValue;
  protected JButton eventRecord;

  protected void windowMotion() {
    eventFrame.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent event) {
        initPoint = event.getPoint();
      }
    });

    eventFrame.addMouseMotionListener(new MouseMotionAdapter() {

      @Override
      public void mouseDragged(MouseEvent event) {
        int XonScreen = event.getXOnScreen();
        int YonScreen = event.getYOnScreen();
        eventFrame.setLocation(XonScreen - initPoint.x, YonScreen - initPoint.y);
      }
    });
  }

  protected void close() {
    eventButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        eventFrame.dispose();
      }
    });
  }

  protected void close(JFrame mainContainer) {
    eventButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        mainContainer.setEnabled(true);
        eventFrame.dispose();
      }
    });
  }

  protected void displayClicked() {
    eventTextField[0].addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        eventTextField[0].setForeground(PalleteColor.FOREGROUND);
        globalTextField = eventTextField[0];
        eventTextField[1].setForeground(PalleteColor.GREY);
      }
    });
    eventTextField[1].addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        eventTextField[1].setForeground(PalleteColor.FOREGROUND);
        eventTextField[0].setForeground(PalleteColor.GREY);
        globalTextField = eventTextField[1];

      }
    });
  }

  protected void keyPressed(JLabel eventKey) {
    eventKey.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        String valueScreen = globalTextField.getText();
        String keyValue = eventKey.getText();
        switch (keyValue) {
          case "AC" -> {
            globalTextField.setText("0");
          }
          case "C" -> {
            if (valueScreen.length() != 1) {
              globalTextField.setText(valueScreen.substring(0, valueScreen.length() - 1));
            }
          }
          case "MR" -> {
            LocalDateTime dateTime = LocalDateTime.now();
            String formatterDate = dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT));

            File file = new File("record.txt");
            FileWriter fWriter = null;
            try {
              fWriter = new FileWriter(file.getAbsoluteFile(), true);
              fWriter.write(STR."\{eventLabel[0].getText()};\{eventTextField[0].getText()};\{eventLabel[1].getText()};\{eventTextField[1].getText()};\{formatterDate}\n");
              fWriter.close();
            } catch (IOException ex) {
              throw new RuntimeException(ex);
            }

          }
          default -> {
            boolean pointCountCtl = valueScreen.contains(".") && keyValue.equals(".");
            if (valueScreen.replace(".", "").length() < 10 && !pointCountCtl) {
              if (valueScreen.equals("0") && keyValue.equals(".")) {
                globalTextField.setText("0.");
              } else if (valueScreen.equals("0")) {
                valueScreen = "";
                globalTextField.setText(valueScreen.concat(keyValue));
              } else {
                globalTextField.setText(valueScreen.concat(keyValue));
              }
            }
          }
        }
        if (!keyValue.equals("AC"))
          if (!keyValue.equals("C") && !keyValue.equals(".") && !keyValue.equals("MR")) {
            ExchangeRateApi api = new ExchangeRateApi();
            double result;
            if (globalTextField.equals(eventTextField[0])) {
              rate = api.conversionRate(eventLabel[0].getText(), eventLabel[1].getText());
              mainValue = Double.parseDouble(eventTextField[0].getText());
              result = rate * mainValue;
              System.out.println(STR."\{mainValue} \{eventLabel[0].getText()} mutltiplicado por la tasa de \{rate}, equivale: \{result}");
              eventTextField[1].setText(String.format("%.1f", result).replace(",", "."));
            } else {
              rate = api.conversionRate(eventLabel[1].getText(), eventLabel[0].getText());
              mainValue = Double.parseDouble(eventTextField[1].getText());
              result = rate * mainValue;
              System.out.println(STR."\{mainValue} \{eventLabel[1].getText()} mutltiplicado por la tasa de \{rate}, equivale: \{result}");
              eventTextField[0].setText(String.format("%.1f", result).replace(",", "."));
            }
          }
      }
    });
  }

  protected void selectButtonClick() {
    eventSelect[0].addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        eventFrame.setEnabled(false);
        Currencies currenciesWindow = new Currencies(eventFrame, eventLabel[0], eventLabel[1], eventTextField[1], eventTextField[0], data);
        currenciesWindow.setEnable();
      }
    });

    eventSelect[1].addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        eventFrame.setEnabled(false);
        Currencies currenciesWindow = new Currencies(eventFrame, eventLabel[1], eventLabel[0], eventTextField[0], eventTextField[1], data);
        currenciesWindow.setEnable();
      }
    });
  }

  protected void dobleClickTable(
          TableModel tableModel, JLabel mainCurrency, JLabel secondaryCurrency, JTextField result, JTextField operand, JFrame mainContainer
  ) {
    eventTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        Point point = e.getPoint();
        int row = table.rowAtPoint(point);
        if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
          mainCurrency.setText(String.valueOf(tableModel.getValueAt(row, 0)));
          mainContainer.setEnabled(true);
          currenciesWindow.dispose();
          rate = api.conversionRate(mainCurrency.getText(), secondaryCurrency.getText());
          System.out.println(STR."1 \{mainCurrency.getText()} es igual \{rate} \{secondaryCurrency.getText()}");
          secondaryValue = Double.parseDouble(operand.getText());
          double value = rate * secondaryValue;
          result.setText(String.format("%.1f", value).replace(",", "."));
        }
      }
    });
  }

  protected void recordShow() {
    eventRecord.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        eventRecord.setForeground(PalleteColor.GREY);
      }

      @Override
      public void mouseExited(MouseEvent e) {
        eventRecord.setForeground(PalleteColor.DARK_GREY);
      }

      @Override
      public void mouseClicked(MouseEvent e) {
        File file = new File("record.txt");
        TableModel baseData;
        try {
          BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

          List<String> list = new ArrayList<>();

          String line;
          while ((line = bufferedReader.readLine()) != null) {
            list.add(line);
          }
          String[][] data = new String[list.size()][5];
          for (int i = 0; i < list.size(); i++) {
            String[] fields = list.get(i).split(";");
            System.arraycopy(fields, 0, data[i], 0, fields.length);
          }
          String[] head = {"Divisa1", "Valor1", "Divisa2", "valor2", "fecha-hora" };
          baseData = new DefaultTableModel(data, head) {
            @Override
            public boolean isCellEditable(int row, int column) {
              return false;
            }
          };

        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        ConversionLog conversionLog = new ConversionLog(eventFrame, baseData);
        conversionLog.setEnable();
      }
    });
  }
}

