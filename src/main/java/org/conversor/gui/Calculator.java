package org.conversor.gui;


import org.conversor.gui.colors.PalleteColor;
import org.conversor.gui.components.CloseButton;
import org.conversor.gui.components.RoundBorderPanel;
import org.conversor.gui.components.ScreenPanel;
import org.conversor.gui.components.Window;
import org.conversor.model.CurrenciesData;
import org.conversor.service.ExchangeRateApi;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;

public class Calculator {
  protected Window mainWindow;
  private RoundBorderPanel converterPanel;
  private final EventListener eventListener;
  private final Widgets widgets = new Widgets();
  private Widgets topDisplayWidgets;
  private Widgets bottomDisplayWidgets;

  public Calculator() {
    ExchangeRateApi api = new ExchangeRateApi();
    CurrenciesData data = api.getCodeList();
    eventListener = new EventListener();
    eventListener.data = data;
    windowInit();
    setTitle("Conversor de divisa");
    Util.VSplit(converterPanel, 14, 2);
    displayTopScreen();
    Util.VSplit(converterPanel, 4, 4);
    displayBottomScreen();
    Util.VSplit(converterPanel, 10, 6);
    displayKeyMap();
    getRecord();

    eventListener.windowMotion();
    eventListener.displayClicked();
    eventListener.selectButtonClick();
    eventListener.recordShow();
    eventListener.close();
    for (int i = 0; i < widgets.getNum().length; i++) {
      eventListener.keyPressed(widgets.getNum()[i]);
    }
    connStatus();
  }

  public void setEnable() {
    mainWindow.setVisible(true);
  }

  private void windowInit() {
    mainWindow = new Window(PalleteColor.DARK, 300, 520, 15, 15);
    mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    eventListener.eventFrame = mainWindow;
    converterPanel = new RoundBorderPanel(
            PalleteColor.DARK_CORAL, 1, 270, 490
    );
    CloseButton closeButton = new CloseButton(20, 20);
    eventListener.eventButton = closeButton;
    Util.HSplit(converterPanel,210,0,0);
    Util.addContainer(converterPanel, closeButton, 1,0,1);
    mainWindow.add(converterPanel);
  }

  private void setTitle() {
    setTitle(null);
  }

  private void setTitle(String title) {
    JLabel mainTitle = widgets.title(title);
    Util.addContainer(converterPanel, mainTitle, 0,1,2);
  }

  private void displayTopScreen() {
    topDisplayWidgets = new Widgets();
    ScreenPanel topDisplay = topDisplayWidgets.Display("TOP");
    eventListener.eventTextField[0] = topDisplayWidgets.getValue();
    eventListener.eventSelect[0] = topDisplayWidgets.getCurrencySelectButton();
    eventListener.eventLabel[0] = topDisplayWidgets.getCurrency();
    topDisplayWidgets.getValue().setForeground(PalleteColor.GREY);
    Util.addContainer(converterPanel, topDisplay, 0,3,2);
  }

  private void displayBottomScreen() {
    bottomDisplayWidgets = new Widgets();
    ScreenPanel bottomDisplay = bottomDisplayWidgets.Display("BOTTOM");
    eventListener.eventTextField[1] = bottomDisplayWidgets.getValue();
    eventListener.globalTextField = bottomDisplayWidgets.getValue();
    eventListener.eventSelect[1] = bottomDisplayWidgets.getCurrencySelectButton();
    eventListener.eventLabel[1] = bottomDisplayWidgets.getCurrency();
    Util.addContainer(converterPanel, bottomDisplay, 0,5,2);
  }

  private void displayKeyMap() {
    JPanel keyMap = widgets.keyMap();
    Util.addContainer(converterPanel, keyMap, 0,7,2);
  }

  private void getRecord(){
    Util.addContainer(converterPanel,widgets.record(),0,8,2);
    eventListener.eventRecord = widgets.getRecordButton();
  }

  private void connStatus() {
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        try {
          InetAddress address = InetAddress.getByName("www.exchangerate-api.com");
        } catch (IOException e) {
          topDisplayWidgets.getValue().setText("OFF-LINE");
          topDisplayWidgets.getValue().setForeground(PalleteColor.GREY);
          bottomDisplayWidgets.getValue().setText("OFF-LINE");
          bottomDisplayWidgets.getValue().setForeground(PalleteColor.GREY);
          widgets.keyMap().setEnabled(false);
          System.err.println(STR."Error en la conexión: \{e.getMessage()}");
          timer.cancel();
        }
      }
    };
    timer.scheduleAtFixedRate(timerTask, 0, 1000);
  }
}
