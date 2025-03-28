import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class Library {
    // Список для хранения всех изданий в библиотеке
    private List<PrintedEdition> editions;

    // Конструктор, который инициализирует список изданий
    public Library() {
        editions = new ArrayList<>(); // Создаем пустой список для изданий
    }

    // Метод для добавления нового издания в библиотеку
    public void addEdition(PrintedEdition edition) {
        // Проверка на то, что год издания не больше 2024
        if (edition.getYear() > 2024) {
            System.out.println("Год издания не может быть больше 2024!"); // Выводим сообщение об ошибке
            return; // Прерываем выполнение метода, если год больше 2024
        }
        editions.add(edition); // Добавляем издание в список
    }

    // Метод для удаления издания по индексу
    public void removeEdition(int index) {
        // Проверяем, что индекс находится в пределах списка
        if (index >= 0 && index < editions.size()) {
            editions.remove(index); // Удаляем издание по индексу
        } else {
            System.out.println("Некорректный индекс!"); // Если индекс некорректный, выводим ошибку
        }
    }


    // Метод для получения всех изданий из библиотеки
    public List<PrintedEdition> getEditions() {
        return editions; // Возвращаем список всех изданий
    }

    // Метод для установки новых изданий в библиотеку
    public void setEditions(List<PrintedEdition> editions) {
        this.editions = editions; // Обновляем список изданий
    }

    // Пример метода для сортировки изданий по названию
    public void sortByTitle() {
        editions.sort(Comparator.comparing(PrintedEdition::getTitle)); // Сортируем список изданий по названию
    }

    // Пример метода для сортировки изданий по автору
    public void sortByAuthor() {
        editions.sort(Comparator.comparing(PrintedEdition::getAuthor)); // Сортируем список изданий по автору
    }

    // Пример метода для сортировки изданий по году издания
    public void sortByYear() {
        editions.sort(Comparator.comparingInt(PrintedEdition::getYear)); // Сортируем список изданий по году издания
    }

    // Пример метода для сортировки изданий по издательству
    public void sortByPublisher() {
        editions.sort(Comparator.comparing(PrintedEdition::getPublisher)); // Сортируем список изданий по издательству
    }

    // Пример метода для сортировки изданий по жанру
    public void sortByGenre() {
        editions.sort(Comparator.comparing(PrintedEdition::getGenre)); // Сортируем список изданий по жанру
    }

    public void sortByType() {
        editions.sort(Comparator.comparing(PrintedEdition::getType)); // Сортируем список изданий по типу
    }
}

