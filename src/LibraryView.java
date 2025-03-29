import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class LibraryView {
    JFrame frame;
    private JTable table;
    private LibraryTableModel tableModel;
    private JButton addButton, removeButton, searchButton, sortButton;
    private JComboBox<String> typeComboBox; // Добавим JComboBox для выбора типа

    public LibraryView(Library library) {
        frame = new JFrame("Библиотека");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        tableModel = new LibraryTableModel(library.getEditions());
        table = new JTable(tableModel);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel(new FlowLayout());
        addButton = new JButton("Добавить");
        removeButton = new JButton("Удалить");
        searchButton = new JButton("Поиск");
        sortButton = new JButton("Сортировка");

        // Создаем JComboBox для выбора типа
        String[] types = {"Книга", "Учебник", "Журнал"};
        typeComboBox = new JComboBox<>(types);

        panel.add(typeComboBox);
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(searchButton);
        panel.add(sortButton);
        frame.add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }


    public void setAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void setRemoveButtonListener(ActionListener listener) {
        removeButton.addActionListener(listener);
    }

    public void setSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public void setSortButtonListener(ActionListener listener) {
        sortButton.addActionListener(listener);
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    public String getSelectedType() {
        return (String) typeComboBox.getSelectedItem();
    }

    public void updateTable(Library library) {
        tableModel.updateData(library.getEditions()); // Обновляем данные в модели таблицы
        table.repaint(); // Перерисовываем таблицу
    }

}
