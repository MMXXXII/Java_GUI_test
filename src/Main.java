public class Main {
    public static void main(String[] args) {
        Library library = new Library(); // Создаем новый объект библиотеки (Library).
        LibraryView view = new LibraryView(library); // Создаем новый объект представления (LibraryView)
        new LibraryController(library, view); // Создаем новый объект контроллера (LibraryController)
    }
}
