import javax.swing.*;

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

        // Назначение обработчиков событий с использованием лямбда-выражений
        view.setAddButtonListener(e -> addEdition());
        view.setRemoveButtonListener(e -> removeEdition());
        view.setSearchButtonListener(e -> searchEdition());
        view.setSortButtonListener(e -> sortEditions());
    }

    // Метод для добавления нового издания в библиотеку
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

        // Создаем объект нужного типа в зависимости от выбора пользователя
        PrintedEdition edition = createEdition(selectedType, title, author, year, publisher, genre);
        if (edition != null) {
            libraryDB.addEdition(edition); // Добавляем издание в базу данных через LibraryDB
            library.setEditions(libraryDB.getEditions()); // Обновляем список изданий в библиотеке
            view.updateTable(library);   // Обновляем таблицу в представлении
        }
    }

    // Метод для запроса ввода и повторного запроса при пустом значении
    private String requestInput(String message) {
        String input;
        while (true) {
            input = JOptionPane.showInputDialog(message);
            if (input == null || input.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Это поле не может быть пустым.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            } else {
                return input;
            }
        }
    }

    // Метод для создания объекта нужного типа
    private PrintedEdition createEdition(String type, String title, String author, int year, String publisher, String genre) {
        switch (type) {
            case "Книга":
                return new Book(title, author, year, publisher, genre);
            case "Учебник":
                return new Textbook(title, author, year, publisher, genre);
            case "Журнал":
                return new Magazine(title, author, year, publisher, genre);
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



    // Метод для поиска издания по ключевому слову
    private void searchEdition() {
        String keyword = JOptionPane.showInputDialog("Введите ключевое слово для поиска:");
        if (keyword != null && !keyword.trim().isEmpty()) {
            Library searchResults = new Library(); // Временная библиотека для хранения результатов
            for (PrintedEdition edition : libraryDB.getEditions()) { // Получаем издания из базы данных
                if (edition.getTitle().toUpperCase().startsWith(keyword.toUpperCase())) {
                    searchResults.addEdition(edition);
                }
            }

            if (!searchResults.getEditions().isEmpty()) {
                view.updateTable(searchResults); // Отображаем результаты поиска
            } else {
                JOptionPane.showMessageDialog(null, "Издание не найдено.", "Поиск", JOptionPane.INFORMATION_MESSAGE);
            }
        }
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
