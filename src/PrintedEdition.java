public abstract class PrintedEdition {
    private String title;
    private String author;
    private int year;
    private String publisher;

    public PrintedEdition(String title, String author, int year, String publisher) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.publisher = publisher;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public String getPublisher() { return publisher; }

    public abstract void displayInfo();
    public abstract String getGenre();
}
