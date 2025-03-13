public class Magazine extends PrintedEdition {
    private int issueNumber;

    public Magazine(String title, String author, int year, String publisher, int issueNumber) {
        super(title, author, year, publisher);
        this.issueNumber = issueNumber;
    }

    @Override
    public void displayInfo() {
        System.out.println("Журнал: " + getTitle() + ", Автор: " + getAuthor() +
                ", Выпуск №" + issueNumber + ", Год: " + getYear() + ", Издательство: " + getPublisher());
    }

    @Override
    public String getGenre() {
        return "Журнал";
    }
}
