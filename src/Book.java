public class Book extends PrintedEdition {
    private String genre;

    public Book(String title, String author, int year, String publisher, String genre) {
        super(title, author, year, publisher);
        this.genre = genre;
    }

    public String getGenre() { return genre; }

    @Override
    public void displayInfo() {
        System.out.println("Книга: " + getTitle() + ", Автор: " + getAuthor() +
                ", Жанр: " + genre + ", Год: " + getYear() + ", Издательство: " + getPublisher());
    }
}
