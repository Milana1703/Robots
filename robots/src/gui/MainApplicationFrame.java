package gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

import javax.swing.*;

import log.Logger;

public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private static final String saveFileName = "main-frame.txt";
    private LogWindow logWindow;
    private GameWindow gameWindow;

    public MainApplicationFrame() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle defaultBounds = new Rectangle(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);
        SaveUtilities.restoreState(this, saveFileName, defaultBounds);
        setContentPane(desktopPane);


        logWindow = createLogWindow();
        addWindow(logWindow);

        gameWindow = new GameWindow();
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                createConfirmExitPane();
            }
        });
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        Logger.debug("Протокол работает");
        return logWindow;
    }

    private void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu lookAndFeelMenu = getLookAndFeelMenu();
        JMenu testMenu = getTestMenu();
        JMenu exitMenu = getExitMenu();

        menuBar.add(exitMenu);
        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);

        return menuBar;
    }

    private JMenu getTestMenu() {
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");
        JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug("Новая строка");
        });
        testMenu.add(addLogMessageItem);
        return testMenu;
    }

    private JMenu getExitMenu() {
        JMenu exitMenu = new JMenu("Выход");
        JMenuItem exitItem = new JMenuItem("Нет блин вход (да выход выход, точно)");
        exitItem.addActionListener(event -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
        exitMenu.add(exitItem);
        return exitMenu;
    }

    private void createConfirmExitPane() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Ты точно хочешь отсюда уйти? \uD83D\uDC49\uD83D\uDC48",
                "Выход",
                JOptionPane.YES_NO_OPTION
        );
        if (result == 0) {
            SaveUtilities.saveState(this, saveFileName);
            SaveUtilities.saveState(logWindow, LogWindow.saveFileName);
            SaveUtilities.saveState(gameWindow, GameWindow.saveFileName);
            System.exit(0);
        }
    }

    private JMenu getLookAndFeelMenu() {
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");
        JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
        systemLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });
        lookAndFeelMenu.add(systemLookAndFeel);
        return lookAndFeelMenu;
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }
}
