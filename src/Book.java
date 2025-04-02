public class Book extends PrintedEdition {
    public Book(int id, String title, String author, int year, String publisher, String genre) {
        super(id, title, author, year, publisher, genre);
    }

    @Override
    public String getType() {
        return "Книга";
    }
}