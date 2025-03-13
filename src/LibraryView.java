import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class LibraryView { // Определение класса LibraryView (отвечает за интерфейс)
    JFrame frame; // Главное окно приложения
    private JTable table; // Таблица для отображения данных о книгах
    private DefaultTableModel tableModel; // Модель данных для таблицы
    private JButton addButton, removeButton, searchButton, sortButton; // Кнопки управления

    public LibraryView() { // Конструктор класса (инициализация интерфейса)
        frame = new JFrame("Библиотека"); // Создаем главное окно с заголовком "Библиотека"
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Закрытие программы при закрытии окна
        frame.setSize(800, 500); // Устанавливаем размеры окна
        frame.setLayout(new BorderLayout()); // Устанавливаем менеджер компоновки BorderLayout

        // Создаем модель таблицы с заголовками столбцов
        tableModel = new DefaultTableModel(new Object[]{"Название", "Автор", "Год", "Издательство", "Жанр"}, 0);
        table = new JTable(tableModel); // Создаем таблицу и связываем ее с моделью
        frame.add(new JScrollPane(table), BorderLayout.CENTER); // Добавляем таблицу в окно с возможностью прокрутки

        JPanel panel = new JPanel(); // Создаем панель для кнопок
        addButton = new JButton("Добавить"); // Создаем кнопку "Добавить"
        removeButton = new JButton("Удалить"); // Создаем кнопку "Удалить"
        searchButton = new JButton("Поиск"); // Создаем кнопку "Поиск"
        sortButton = new JButton("Сортировка"); // Создаем кнопку "Сортировка"

        // Добавляем кнопки на панель
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(searchButton);
        panel.add(sortButton);
        frame.add(panel, BorderLayout.SOUTH); // Размещаем панель с кнопками в нижней части окна

        frame.setVisible(true); // Делаем окно видимым
    }

    // Метод для установки обработчика событий для кнопки "Добавить"
    public void setAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    // Метод для установки обработчика событий для кнопки "Удалить"
    public void setRemoveButtonListener(ActionListener listener) {
        removeButton.addActionListener(listener);
    }

    // Метод для установки обработчика событий для кнопки "Поиск"
    public void setSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    // Метод для установки обработчика событий для кнопки "Сортировка"
    public void setSortButtonListener(ActionListener listener) {
        sortButton.addActionListener(listener);
    }

    // Метод для получения индекса выбранной строки в таблице
    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    // Метод для обновления таблицы
    public void updateTable(Library library) {
        // Получаем данные для таблицы
        Object[][] data = library.getEditionsData();
        // Обновляем модель таблицы
        table.setModel(new DefaultTableModel(data, getColumnNames()));
    }



    // Метод для получения имен колонок
    private String[] getColumnNames() {
        return new String[]{"Название", "Автор", "Год", "Издательство", "Жанр"};
    }
}
