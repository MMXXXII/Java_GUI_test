import javax.swing.*;

public class LibraryController { // Класс контроллера, управляющий взаимодействием модели и представления
    private Library library; // Объект библиотеки (модель), содержащий данные о книгах
    private LibraryView view; // Объект представления (GUI), отвечающий за интерфейс пользователя

    // Конструктор контроллера, принимающий библиотеку и представление
    public LibraryController(Library library, LibraryView view) {
        this.library = library; // Сохраняем ссылку на модель
        this.view = view; // Сохраняем ссылку на представление

        // Устанавливаем обработчики событий для кнопок в представлении
        view.setAddButtonListener(e -> addEdition()); // Добавление книги
        view.setRemoveButtonListener(e -> removeEdition()); // Удаление книги
        view.setSearchButtonListener(e -> searchEdition()); // Поиск книги
        view.setSortButtonListener(e -> sortEditions()); // Сортировка книг
    }

    // Метод для добавления нового издания в библиотеку
    private void addEdition() {
        // Запрашиваем у пользователя ввод данных через диалоговые окна
        String title = JOptionPane.showInputDialog("Введите название издания:");
        String author = JOptionPane.showInputDialog("Введите автора:");
        int year = Integer.parseInt(JOptionPane.showInputDialog("Введите год выпуска:"));
        String publisher = JOptionPane.showInputDialog("Введите издательство:");
        String genre = JOptionPane.showInputDialog("Введите жанр:");

        // Создаем новый объект Book и добавляем его в библиотеку
        library.addEdition(new Book(title, author, year, publisher, genre));

        // Обновляем таблицу в представлении, чтобы отобразить изменения
        view.updateTable(library);
    }

    // Метод для удаления выбранного издания из библиотеки
    private void removeEdition() {
        int selectedRow = view.getSelectedRow(); // Получаем индекс выбранной строки в таблице
        if (selectedRow != -1) { // Проверяем, выбрана ли строка
            library.removeEdition(selectedRow); // Удаляем книгу из библиотеки
            view.updateTable(library); // Обновляем таблицу в интерфейсе
        } else { // Если строка не выбрана
            JOptionPane.showMessageDialog(null, "Выберите строку для удаления."); // Показываем сообщение об ошибке
        }
    }

    private void searchEdition() {
        String keyword = JOptionPane.showInputDialog("Введите ключевое слово для поиска:"); // Запрашиваем ключевое слово
        if (keyword != null && !keyword.isEmpty()) { // Проверяем, что пользователь ввел данные
            Library searchResults = new Library(); // Создаем временный объект библиотеки для хранения результатов поиска
            for (PrintedEdition edition : library.getEditions()) { // Проходим по всем книгам в библиотеке
                // Проверяем, что название издания начинается с введенного ключевого слова
                if (edition.getTitle().toUpperCase().startsWith(keyword.toUpperCase())) {
                    searchResults.addEdition(edition); // Добавляем найденные книги в новую библиотеку
                }
            }
            view.updateTable(searchResults); // Обновляем таблицу в представлении, показывая результаты поиска
        }
    }




    // Метод для сортировки изданий по выбранному параметру
    private void sortEditions() {
        // Массив с возможными параметрами сортировки (название, автор, год и т.д.)
        String[] options = {"Название", "Автор", "Год", "Издательство", "Жанр"};

        // Открытие диалогового окна с выбором параметра сортировки
        // Используем view.frame вместо переменной frame для ссылки на главное окно из класса LibraryView
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
                case "Название":  // Если выбрано "Название"
                    library.sortByTitle();  // Сортируем по названию
                    break;
                case "Автор":  // Если выбрано "Автор"
                    library.sortByAuthor();  // Сортируем по автору
                    break;
                case "Год":  // Если выбрано "Год"
                    library.sortByYear();  // Сортируем по году
                    break;
                case "Издательство":  // Если выбрано "Издательство"
                    library.sortByPublisher();  // Сортируем по издательству
                    break;
                case "Жанр":  // Если выбрано "Жанр"
                    library.sortByGenre();  // Сортируем по жанру
                    break;
            }
            // Обновляем таблицу в представлении, чтобы отобразить отсортированные данные
            view.updateTable(library);  // Метод обновления таблицы с данными из библиотеки
        }
    }



}
