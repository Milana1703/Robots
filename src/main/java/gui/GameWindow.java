package gui;

import javax.swing.JInternalFrame;  // Internal - внутренний
import javax.swing.JPanel;

import java.awt.BorderLayout;
// BorderLayout - макет по умолчанию для объектов window, таких как: JWindow, JDialog, JInternalFrame и т.д.
// Распределяет компоненты по 5 областям: СЕВЕР, ЮГ, ЗАПАД, ВОСТОК и ЦЕНТР (каждая может содержать только 1 компонент)
// Конструктор BorderLayout() создаст новый BorderLayout без пробелов между компонентами.

/** Класс, реализующий основное игровое поле */

public class GameWindow extends JInternalFrame
{
    public GameWindow() {
        super("Игровое поле", true, true, true, true);

        GameVisualizer m_visualizer = new GameVisualizer();

        JPanel panel = new JPanel(new BorderLayout());

        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);

        pack();  // связывает все компоненты вместе и правильно размещает их на панели
    }
}