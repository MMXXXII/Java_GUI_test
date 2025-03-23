import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryController {
    private Library library;
    private LibraryView view;

    public LibraryController(Library library, LibraryView view) {
        this.library = library;
        this.view = view;

        view.setAddButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEdition();
            }
        });

        view.setRemoveButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeEdition();
            }
        });

        view.setSearchButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchEdition();
            }
        });

        view.setSortButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortEditions();
            }
        });
    }

    private void addEdition() {
        String selectedType = view.getSelectedType(); // Получаем выбранный тип

        String title = JOptionPane.showInputDialog("Введите название:");
        if (title == null || title.trim().isEmpty()) return;

        String author = JOptionPane.showInputDialog("Введите автора:");
        if (author == null || author.trim().isEmpty()) return;

        int year;
        try {
            year = Integer.parseInt(JOptionPane.showInputDialog("Введите год издания:"));
            if (year > 2024) {
                JOptionPane.showMessageDialog(null, "Год издания не может быть больше 2024!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Введите корректный год!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String publisher = JOptionPane.showInputDialog("Введите издательство:");
        if (publisher == null || publisher.trim().isEmpty()) return;

        // Получаем жанр
        String genre = JOptionPane.showInputDialog("Введите жанр:");
        if (genre == null || genre.trim().isEmpty()) return;

        // Создаем объект нужного типа
        PrintedEdition edition;
        switch (selectedType) {
            case "Книга":
                edition = new Book(title, author, year, publisher, genre);
                break;
            case "Учебник":
                edition = new Textbook(title, author, year, publisher, genre);
                break;
            case "Журнал":
                edition = new Magazine(title, author, year, publisher, genre);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Неизвестный тип издания!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
        }

        library.addEdition(edition);
        view.updateTable(library); // Обновляем таблицу
    }



    private void removeEdition() {
        int selectedRow = view.getSelectedRow();
        if (selectedRow != -1) {
            library.removeEdition(selectedRow);
            view.updateTable(library);
        } else {
            JOptionPane.showMessageDialog(null, "Выберите запись для удаления!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchEdition() {
        String keyword = JOptionPane.showInputDialog("Введите ключевое слово для поиска:"); // Запрашиваем ключевое слово
        if (keyword != null && !keyword.isEmpty()) { // Проверяем, что пользователь ввел данные
            Library searchResults = new Library(); // Создаем временный объект библиотеки для хранения результатов поиска
            for (PrintedEdition edition : library.getEditions()) { // Проходим по всем книгам в библиотеке
                // Проверяем, что название издания начинается с введенного ключевого слова (игнорируем регистр)
                if (edition.getTitle().toUpperCase().startsWith(keyword.toUpperCase())) {
                    searchResults.addEdition(edition); // Добавляем найденные книги в новую библиотеку
                }
            }
            // Если найдены результаты поиска
            if (!searchResults.getEditions().isEmpty()) {
                view.updateTable(searchResults); // Обновляем таблицу в представлении, показывая результаты поиска
            } else {
                JOptionPane.showMessageDialog(null, "Издание не найдено.", "Поиск", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }




    // Метод для сортировки изданий по выбранному параметру
    private void sortEditions() {
        // Массив с возможными параметрами сортировки (название, автор, год и т.д.)
        String[] options = {"Название", "Автор", "Год", "Издательство", "Жанр", "Тип"};

        // Открытие диалогового окна с выбором параметра сортировки
        String choice = (String) JOptionPane.showInputDialog(
                view.frame,  // Ссылка на главное окно из класса LibraryView
                "Выберите параметр сортировки:", // Текст, который будет отображаться в диалоговом окне
                "Сортировка", // Заголовок окна
                JOptionPane.QUESTION_MESSAGE, // Тип диалогового окна (вопрос)
                null,  // Значок (пока не используется, можно передать иконку)
                options, // Массив опций для выбора
                options[0]  // Значение по умолчанию (первый элемент в массиве)
        );

        // Если пользователь выбрал параметр сортировки (не отменил выбор)
        if (choice != null) {
            // В зависимости от выбора пользователя, вызываем соответствующий метод сортировки
            switch (choice) {
                case "Название":
                    library.sortByTitle();  // Сортируем по названию
                    break;
                case "Автор":
                    library.sortByAuthor();  // Сортируем по автору
                    break;
                case "Год":
                    library.sortByYear();  // Сортируем по году
                    break;
                case "Издательство":
                    library.sortByPublisher();  // Сортируем по издательству
                    break;
                case "Жанр":
                    library.sortByGenre();  // Сортируем по жанру
                    break;
                case "Тип":
                    library.sortByType();  // Сортируем по типу (книга, учебник, журнал)
                    break;
            }
            // Обновляем таблицу в представлении, чтобы отобразить отсортированные данные
            view.updateTable(library);  // Метод обновления таблицы с данными из библиотеки
        }
    }

}
