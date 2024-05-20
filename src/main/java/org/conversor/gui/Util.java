package org.conversor.gui;

import javax.swing.*;
import java.awt.*;

public class Util {
  protected static void addContainer(Container master, Container slave) {
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridx = 0;
    constraints.gridy = 0;
    master.add(slave, constraints);
  }

  protected static void addContainer(Container master, Container slave, int gridX, int gridY, int gridWidth) {
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridx = gridX;
    constraints.gridy = gridY;
    constraints.gridwidth = gridWidth;
    master.add(slave, constraints);
  }

  protected static void addContainer(Container master, Container slave, int gridX, int gridY, int gridWidth, int gridHeight) {
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridx = gridX;
    constraints.gridy = gridY;
    constraints.gridwidth = gridWidth;
    constraints.gridheight = gridHeight;
    constraints.insets = new Insets(2, 2, 2, 2);
    constraints.fill = GridBagConstraints.BOTH;
    master.add(slave, constraints);
  }

  public static Image getImage(String path) {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    return toolkit.getImage(path);
  }

  protected static void VSplit(Container container, int space, int grdY) {
    GridBagConstraints constraints;
    constraints = new GridBagConstraints();
    constraints.gridx = 0;
    constraints.gridy = grdY;
    container.add(Box.createRigidArea(new Dimension(0, space)), constraints);
  }

  protected static void HSplit(Container container, int space, int grdX, int grdY) {
    GridBagConstraints constraints;
    constraints = new GridBagConstraints();
    constraints.gridx = grdX;
    constraints.gridy = grdY;
    container.add(Box.createRigidArea(new Dimension(space, 0)), constraints);
  }
}
