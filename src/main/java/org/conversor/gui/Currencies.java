package org.conversor.gui;


import org.conversor.gui.colors.PalleteColor;
import org.conversor.gui.components.CloseButton;
import org.conversor.gui.components.RoundBorderPanel;
import org.conversor.gui.components.Window;
import org.conversor.model.CurrenciesData;
import org.conversor.service.ExchangeRateApi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Currencies {
  private Window currenciesWindow;
  private RoundBorderPanel currenciesPanel;
  private final EventListener eventListener;
  private final Widgets widgets = new Widgets();
  private ExchangeRateApi api;
  private final CurrenciesData data;
  private TableModel model;

  protected Currencies(
          JFrame mainContainer,
          JLabel mainCurrency,
          JLabel secondaryCurrency,
          JTextField result,
          JTextField operand,
          CurrenciesData data
  ) {
    this.data = data;
    eventListener = new EventListener();
    currenciesInit();
    setTitle();
    Util.VSplit(currenciesPanel, 5, 2);
    setCurrenciesTable();

    eventListener.windowMotion();
    eventListener.close(mainContainer);
    eventListener.dobleClickTable(model, mainCurrency, secondaryCurrency, result, operand, mainContainer);
  }
  public void setEnable() {
    currenciesWindow.setVisible(true);
  }

  protected void currenciesInit() {
    currenciesWindow = new Window(PalleteColor.DARK, 400, 300, 15, 15);
    eventListener.eventFrame = currenciesWindow;
    eventListener.currenciesWindow = currenciesWindow;
    currenciesPanel = new RoundBorderPanel(
            PalleteColor.DARK_CORAL, 1, 370, 270
    );
    CloseButton closeButton = new CloseButton(20, 20);
    eventListener.eventButton = closeButton;
    Util.HSplit(currenciesPanel, 310, 0, 0);
    Util.addContainer(currenciesPanel, closeButton, 1, 0, 1);
    currenciesWindow.add(currenciesPanel);
  }

  private void setTitle() {
    JLabel mainTitle = widgets.titleNotIcon("Selección de divisas:", 320, 20);
    Util.addContainer(currenciesPanel, mainTitle, 0, 1, 2);
  }

  private void setCurrenciesTable() {
    RoundBorderPanel separate = new RoundBorderPanel(PalleteColor.DARK_CORAL, 2, 320, 3);
    String[] name = {"Código", "Descripción"};
    String[][] dataTable = data.supported_codes();
    model = new DefaultTableModel(dataTable, name) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    JScrollPane table = widgets.currenciesTable(model, 320, 150);
    Util.addContainer(currenciesPanel, separate, 0, 3, 2);
    Util.VSplit(currenciesPanel, 10, 4);
    Util.addContainer(currenciesPanel, table, 0, 5, 2);
    eventListener.eventTable = widgets.getTable();
  }
}
