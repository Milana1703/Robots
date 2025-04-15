package gui;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.*;

public class SaveUtilities {

    public static void restoreState(Component componentToSave, String saveFileName, Rectangle defaultBounds) {
        try (FileReader fileInputStream = new FileReader(saveFileName);
             BufferedReader reader = new BufferedReader(fileInputStream)) {
            String[] restoredData = reader.readLine().split(" ");
            int restoredX = Integer.parseInt(restoredData[0]);
            int restoredY = Integer.parseInt(restoredData[1]);
            int restoredWidth = Integer.parseInt(restoredData[2]);
            int restoredHeight = Integer.parseInt(restoredData[3]);
            componentToSave.setBounds(
                    restoredX,
                    restoredY,
                    restoredWidth,
                    restoredHeight
            );
        } catch (FileNotFoundException e) {
            componentToSave.setBounds(defaultBounds);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void restoreState(JInternalFrame componentToSave, String saveFileName, Rectangle defaultBounds) {
        try (FileReader fileInputStream = new FileReader(saveFileName);
             BufferedReader reader = new BufferedReader(fileInputStream)) {
            String[] restoredData = reader.readLine().split(" ");
            int restoredX = Integer.parseInt(restoredData[0]);
            int restoredY = Integer.parseInt(restoredData[1]);
            int restoredWidth = Integer.parseInt(restoredData[2]);
            int restoredHeight = Integer.parseInt(restoredData[3]);
            boolean restoredIsIcon = Boolean.parseBoolean(restoredData[4]);
            componentToSave.setBounds(
                    restoredX,
                    restoredY,
                    restoredWidth,
                    restoredHeight
            );
            componentToSave.setIcon(restoredIsIcon);
        } catch (FileNotFoundException e) {
            componentToSave.setBounds(defaultBounds);
        } catch (PropertyVetoException e) {
            System.out.println("Пуk \uD83E\uDD86\uD83D\uDCA8 ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveState(Component componentToSave, String saveFileName) {
        try {
            new File(saveFileName).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fileOutputStream = new FileWriter(saveFileName);
             BufferedWriter objectOutputStream = new BufferedWriter(fileOutputStream)) {
            String dataToSave = componentToSave.getX() + " "
                    + componentToSave.getY() + " "
                    + componentToSave.getWidth() + " "
                    + componentToSave.getHeight();
            objectOutputStream.write(dataToSave);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveState(JInternalFrame componentToSave, String saveFileName) {
        try {
            new File(saveFileName).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fileOutputStream = new FileWriter(saveFileName);
             BufferedWriter objectOutputStream = new BufferedWriter(fileOutputStream)) {
            String dataToSave = componentToSave.getX() + " "
                    + componentToSave.getY() + " "
                    + componentToSave.getWidth() + " "
                    + componentToSave.getHeight() + " "
                    + componentToSave.isIcon();
            objectOutputStream.write(dataToSave);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
