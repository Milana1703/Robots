package gui;

import java.awt.Frame;
// Frame - окно верхнего уровня с заголовком и рамкой.

import javax.swing.SwingUtilities;
// Вызывает асинхронное выполнение run() в потоке отправки событий AWT.
// Действие произойдет после обработки всех ожидающих AWT-событий.

import javax.swing.UIManager;

public class MainProgram
{
    public static void main(String[] args) {
        try {
            // UIManager.setLookAndFeel() используется для изменения внешнего вида и ощущения Java-приложения
            // В параметрах следует указать имя класса требуемого L&F.
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // Системный L&F: нативный стиль операционной системы: UIManager.getSystemLookAndFeelClassName()
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Вызов invokeLater(Runnable) ставит Runnable в очередь в потоке отправки событий.

        //  new Runnable() {
        //     @Override                // переопределение метода run()
        //     public void run() {
        //          // body
        //     }
        //  };                          // aka создание потока

        // Лямбда-выражение - краткий способ написания функции/метода, с той лишь особенностью, что такие методы
        // можно передавать в другие методы в качестве аргумента.

        //  () -> { // body }

        SwingUtilities.invokeLater(() -> {

            ApplicationFrame frame = new ApplicationFrame();

            // Метод pack() изменяет размер окна в соответствии с предпочтительным размером и компоновкой его элементов.
            // Связывает все компоненты вместе и правильно размещает их на панели.
            frame.pack();

            // Метод setVisible (логический флаг) - делает компонент видимым (true) или невидимым (false)
            // Переопределяет component.setVisible
            frame.setVisible(true);

            // Метод setExtendedState устанавливает состояние фрейма как "оба максимума" (max_ширина и max_высота)
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }
}