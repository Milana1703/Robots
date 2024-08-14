package gui;

// Библиотека AWT (Abstract Window Toolkit) — инструментарий для работы с различными оконными средами.

// Класс Dimension инкапсулирует ширину и высоту компонента (с целочисленной точностью) в одном объекте.

import java.awt.Toolkit;
// Возвращает изображение, получаемое пиксельные данные из указанного файла.
// Возвращаемое изображение представляет собой новый ОБЪЕКТ, не передаваемый ни одному другому объекту,
// вызывающему этот метод (или его вариацию getImage).

import java.awt.event.KeyEvent;

// Событие, указывающее на то, что в компоненте произошло нажатие клавиши.
// Событие генерируется объектом компонента при нажатии, отпускании или вводе клавиши.

import javax.swing.*;
import log.Logger;

/** Класс, отвечающий за визуальное представление компонент приложения */

// @ Todo :
//    1. Метод создания меню перегружен функционалом -> следует разделить его
//       на серию более простых методов (или вообще выделить отдельный класс).
//    2. Выделить код построения выпадающих меню в отдельные методы.
//    3. Переделать кнопку меню выхода из приложения НЕ через повторный вызов System.exit(0).

// JFrame - класс, позволяющий создавать окна верхнего уровня и управлять ими.

public class ApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    // JDesktopPane - контейнер, используемый для создания виртуального рабочего стола.

    public ApplicationFrame() {
        setContentPane(desktopPane);

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        var logWindowWight = logWindow.getWidth();
        var screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        GameWindow gameWindow = new GameWindow();
        gameWindow.setLocation(logWindowWight + DisplayConst.DOUBLE_INSET, DisplayConst.SINGLE_INSET);
        gameWindow.setSize(screenSize.width - logWindowWight - DisplayConst.TRIPLE_INSET,
                           screenSize.height - DisplayConst.QUADRUPLE_INSET);
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());

        var screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        logWindow.setLocation(DisplayConst.SINGLE_INSET, DisplayConst.SINGLE_INSET);
        logWindow.setSize(DisplayConst.MIN_WIGHT, screenSize.height - DisplayConst.QUADRUPLE_INSET);

        setMinimumSize(logWindow.getSize());
        logWindow.pack();

        Logger.debugLog("Протокол работает.");

        return logWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu visionMenu = new JMenu("Внешний вид");
        visionMenu.setMnemonic(KeyEvent.VK_V);  // ассоциирована с горячим сочетанием клавиш Alt+V
        visionMenu.getAccessibleContext().setAccessibleDescription("Управление внешним видом приложения");

        {
            JMenuItem systemLookAndFeel = new JMenuItem("Системный режим", KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                this.invalidate();  // делает контейнер недействительным
            });
            visionMenu.add(systemLookAndFeel);
        }

        {
            JMenuItem universalLookAndFeel = new JMenuItem("Универсальный режим", KeyEvent.VK_S);
            universalLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                this.invalidate();
            });
            visionMenu.add(universalLookAndFeel);
        }

        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);  // ассоциирована с горячим сочетанием клавиш Alt+T
        testMenu.getAccessibleContext().setAccessibleDescription("Тестовые команды");

        {
            JMenuItem addLogMessageItem = new JMenuItem("Написать сообщение в лог", KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debugLog("Новое сообщение :з");
            });
            testMenu.add(addLogMessageItem);
        }

        JMenu exitMenu = new JMenu("Выход");
        exitMenu.setMnemonic(KeyEvent.VK_X);  // ассоциирована с горячим сочетанием клавиш Alt+X
        exitMenu.getAccessibleContext().setAccessibleDescription("Управление закрытием приложения");

        {
            JMenuItem addButtonExit = new JMenuItem("Закрыть приложение", KeyEvent.VK_S);
            addButtonExit.addActionListener((event) -> exitApp());
            exitMenu.add(addButtonExit);
        }

        menuBar.add(visionMenu);
        menuBar.add(testMenu);
        menuBar.add(exitMenu);

        return menuBar;
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

    private void exitApp() {
        String[] options = {"Да", "Нет"};

        int response = JOptionPane.showOptionDialog(this,
                "Вы точно хотите выйти из приложения?",
                "Подтверждение",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}