package org.conversor.gui;


import org.conversor.gui.colors.PalleteColor;
import org.conversor.gui.components.CloseButton;
import org.conversor.gui.components.RoundBorderPanel;
import org.conversor.gui.components.Window;

import javax.swing.*;
import javax.swing.table.TableModel;

public class ConversionLog {
  private final EventListener eventListener;
  private Window logWindow;
  private RoundBorderPanel logPanel;
  private final Widgets widgets = new Widgets();
  private final TableModel baseData;
  private final JFrame mainContainer;

  protected ConversionLog(JFrame mainContainer, TableModel model) {
    this.mainContainer = mainContainer;
    baseData = model;
    eventListener = new EventListener();
    recordInit();
    setTitle();
    Util.VSplit(logPanel,10,3);
    setRecordTable();

    eventListener.windowMotion();
    eventListener.close(mainContainer);
  }

  public void setEnable() {
    logWindow.setVisible(true);
  }

  protected void recordInit() {
    logWindow = new Window(PalleteColor.DARK, 530, 300, 15, 15);
    eventListener.eventFrame = logWindow;
    logPanel = new RoundBorderPanel(
            PalleteColor.DARK_CORAL, 1, 500, 270
    );
    CloseButton closeButton = new CloseButton(20, 20);
    eventListener.eventButton = closeButton;
    Util.HSplit(logPanel, 400, 0, 0);
    Util.addContainer(logPanel, closeButton, 1, 0, 1);
    logWindow.add(logPanel);
  }

  private void setTitle() {
    JLabel title = widgets.titleNotIcon("Historial:",460, 30);
    RoundBorderPanel separate = new RoundBorderPanel(PalleteColor.DARK_CORAL, 2, 460, 3);
    Util.addContainer(logPanel, title, 0, 1, 2);
    Util.addContainer(logPanel, separate, 0, 2, 2);

  }
  private void setRecordTable() {
    JScrollPane table = widgets.recordTable(baseData, 460, 150);
    mainContainer.setEnabled(false);
    Util.addContainer(logPanel,table,0,4,2);
  }
}

