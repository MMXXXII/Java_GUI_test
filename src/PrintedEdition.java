public abstract class PrintedEdition {
    private int id; // Добавляем поле для ID
    private String title;
    private String author;
    private int year;
    private String publisher;
    private String genre;  // Добавлено поле для жанра

    public PrintedEdition(int id, String title, String author, int year, String publisher, String genre) {
        this.id = id;  // Инициализация ID
        this.title = title;
        this.author = author;
        this.year = year;
        this.publisher = publisher;
        this.genre = genre;  // Инициализация жанра
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id; // Сеттер для ID
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public String getPublisher() { return publisher; }
    public String getGenre() { return genre; }  // Геттер для жанра

    public abstract String getType(); // Возвращает "Книга", "Журнал" или "Учебник"
}
