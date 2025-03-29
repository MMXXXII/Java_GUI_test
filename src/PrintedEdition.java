public abstract class PrintedEdition {
    private String title;
    private String author;
    private int year;
    private String publisher;
    private String genre;  // Добавлено поле для жанра

    public PrintedEdition(String title, String author, int year, String publisher, String genre) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.publisher = publisher;
        this.genre = genre;  // Инициализация жанра
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public String getPublisher() { return publisher; }
    public String getGenre() { return genre; }  // Геттер для жанра

    public abstract String getType(); // Возвращает "Книга", "Журнал" или "Учебник"


}
