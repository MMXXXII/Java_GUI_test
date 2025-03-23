public class Textbook extends PrintedEdition {
    public Textbook(String title, String author, int year, String publisher, String genre) {
        super(title, author, year, publisher, genre);  // Передаем жанр в родительский класс
    }

    @Override
    public String getType() {
        return "Учебник";
    }
}
