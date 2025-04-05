import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibraryController {
    private Library library;
    private LibraryView view;
    private LibraryDB libraryDB; // Экземпляр для работы с базой данных

    // Конструктор, инициализирует модель и представление, а также назначает обработчики событий
    public LibraryController(Library library, LibraryView view) {
        this.library = library;
        this.view = view;
        this.libraryDB = new LibraryDB(); // Инициализация LibraryDB

        library.setEditions(libraryDB.getEditions()); // Загружаем все издания из базы данных
        view.updateTable(library); // Обновляем таблицу на UI

        // Обработчик кнопки "Добавить"
        view.setAddButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEdition();
            }
        });

        // Обработчик кнопки "Удалить"
        view.setRemoveButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeEdition();
            }
        });

        // Обработчик кнопки "Поиск"
        view.setSearchButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchEdition();
            }
        });

        // Обработчик кнопки "Сортировка"
        view.setSortButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortEditions();
            }
        });

        // Обработчик кнопки "Изменить"
        view.setEditButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editEdition();
            }
        });

        // Обработчик кнопки "Обновить"
        view.setUpdateButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });
    }


    private void updateTable() {
        library.setEditions(libraryDB.getEditions()); // Обновляем данные из БД
        view.updateTable(library); // Обновляем отображение таблицы
    }

    private void addEdition() {
        String selectedType = view.getSelectedType(); // Получаем выбранный тип

        // Запрос данных у пользователя с проверкой на пустое значение
        String title = requestInput("Введите название:");
        if (title == null || title.trim().isEmpty()) return;

        String author = requestInput("Введите автора:");
        if (author == null || author.trim().isEmpty()) return;

        int year;
        while (true) {
            try {
                String yearInput = JOptionPane.showInputDialog("Введите год издания:");
                if (yearInput == null || yearInput.trim().isEmpty()) continue; // Повторный запрос, если введено пустое значение
                year = Integer.parseInt(yearInput);
                if (year > 2024) {
                    JOptionPane.showMessageDialog(null, "Год издания не может быть больше 2024!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                break; // Выход из цикла при корректном вводе
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Введите корректный год!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }

        String publisher = requestInput("Введите издательство:");
        if (publisher == null || publisher.trim().isEmpty()) return;

        String genre = requestInput("Введите жанр:");
        if (genre == null || genre.trim().isEmpty()) return;

        // Получаем новый ID, предположим, что это автоинкремент или генерируется как-то
        int newId = libraryDB.generateNewId(); // Пример генерации ID

        // Создаем объект нужного типа в зависимости от выбора пользователя
        PrintedEdition edition = createEdition(newId, selectedType, title, author, year, publisher, genre);
        if (edition != null) {
            libraryDB.addEdition(edition); // Добавляем издание в базу данных через LibraryDB
            library.setEditions(libraryDB.getEditions()); // Обновляем список изданий в библиотеке
            view.updateTable(library);   // Обновляем таблицу в представлении
        }
    }

    private void editEdition() {
        int selectedRow = view.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Выберите запись для изменения!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PrintedEdition oldEdition = library.getEditions().get(selectedRow);
        int editionId = oldEdition.getId(); // Сохраняем ID

        // Запрос новых данных
        String newTitle = requestInput("Введите новое название:", oldEdition.getTitle());
        if (newTitle == null) return;

        String newAuthor = requestInput("Введите нового автора:", oldEdition.getAuthor());
        if (newAuthor == null) return;

        int newYear;
        while (true) {
            try {
                String yearInput = JOptionPane.showInputDialog("Введите новый год издания:", oldEdition.getYear());
                if (yearInput == null) return;
                newYear = Integer.parseInt(yearInput);
                if (newYear > 2024) {
                    JOptionPane.showMessageDialog(null, "Год издания не может быть больше 2024!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Введите корректный год!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }

        String newPublisher = requestInput("Введите новое издательство:", oldEdition.getPublisher());
        if (newPublisher == null) return;

        String newGenre = requestInput("Введите новый жанр:", oldEdition.getGenre());
        if (newGenre == null) return;

        // Создаем новый объект и устанавливаем старый ID
        PrintedEdition newEdition = createEdition(editionId, oldEdition.getType(), newTitle, newAuthor, newYear, newPublisher, newGenre);
        if (newEdition != null) {
            libraryDB.updateEdition(oldEdition, newEdition); // Обновляем в базе данных
            library.setEditions(libraryDB.getEditions()); // Обновляем библиотеку
            view.updateTable(library); // Обновляем UI
        }
    }



    private String requestInput(String message) {
        return requestInput(message, "");
    }

    private String requestInput(String message, String defaultValue) {
        String input = JOptionPane.showInputDialog(null, message, defaultValue);
        return (input != null && !input.trim().isEmpty()) ? input : null;
    }


    // Метод для создания объекта нужного типа
    private PrintedEdition createEdition(int id, String type, String title, String author, int year, String publisher, String genre) {
        switch (type) {
            case "Книга":
                return new Book(id, title, author, year, publisher, genre);
            case "Учебник":
                return new Textbook(id, title, author, year, publisher, genre);
            case "Журнал":
                return new Magazine(id, title, author, year, publisher, genre);
            default:
                JOptionPane.showMessageDialog(null, "Неизвестный тип издания!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return null;
        }
    }

    private void removeEdition() {
        int selectedRow = view.getSelectedRow(); // Получаем индекс выбранной строки
        if (selectedRow != -1) {
            PrintedEdition editionToRemove = library.getEditions().get(selectedRow); // Получаем издание, которое нужно удалить

            // Удаляем издание из базы данных
            libraryDB.removeEdition(editionToRemove);

            // Удаляем издание из локального списка
            library.removeEdition(selectedRow);

            // Обновляем таблицу в представлении
            view.updateTable(library);
        } else {
            JOptionPane.showMessageDialog(null, "Выберите запись для удаления!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }



    public List<PrintedEdition> searchEdition() {
        List<PrintedEdition> editions = new ArrayList<>();

        // Запрос ключевого слова у пользователя
        String keyword = requestInput("Введите название для поиска:");
        if (keyword == null || keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Введите корректное название!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return editions;
        }

        String sql = "SELECT * FROM editions WHERE title LIKE ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Здесь мы используем '%' в начале и в конце для поиска всех изданий, которые содержат введенную строку в любой части
            stmt.setString(1, keyword + "%"); // Заменим на поиск по началу строки
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("year");
                String publisher = rs.getString("publisher");
                String genre = rs.getString("genre");
                String type = rs.getString("type");

                PrintedEdition edition = createEdition(id, type, title, author, year, publisher, genre);
                if (edition != null) {
                    editions.add(edition);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ошибка при поиске изданий в базе данных: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        library.setEditions(editions);
        view.updateTable(library);


        return editions;
    }




    // Метод для сортировки изданий по выбранному параметру
    private void sortEditions() {
        String[] options = {"Название", "Автор", "Год", "Издательство", "Жанр", "Тип"};
        String choice = (String) JOptionPane.showInputDialog(
                view.frame,
                "Выберите параметр сортировки:",
                "Сортировка",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice != null) {
            switch (choice) {
                case "Название":
                    library.sortByTitle(); // Используем уже существующий метод
                    break;
                case "Автор":
                    library.sortByAuthor(); // Используем уже существующий метод
                    break;
                case "Год":
                    library.sortByYear(); // Используем уже существующий метод
                    break;
                case "Издательство":
                    library.sortByPublisher(); // Используем уже существующий метод
                    break;
                case "Жанр":
                    library.sortByGenre(); // Используем уже существующий метод
                    break;
                case "Тип":
                    library.sortByType(); // Используем уже существующий метод
                    break;
            }
            library.setEditions(library.getEditions()); // Обновляем список изданий в библиотеке
            view.updateTable(library); // Обновляем таблицу после сортировки
        }

    }
}
