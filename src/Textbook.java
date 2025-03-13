public class Textbook extends PrintedEdition {
    private String discipline;
    private String educationLevel;

    public Textbook(String title, String author, int year, String publisher, String discipline, String educationLevel) {
        super(title, author, year, publisher);
        this.discipline = discipline;
        this.educationLevel = educationLevel;
    }

    @Override
    public void displayInfo() {
        System.out.println("Учебник: " + getTitle() + ", Автор: " + getAuthor() +
                ", Дисциплина: " + discipline + ", Уровень: " + educationLevel +
                ", Год: " + getYear() + ", Издательство: " + getPublisher());
    }

    @Override
    public String getGenre() {
        return "Учебник"; // Или просто вернуть "", если не хотите учитывать учебники при сортировке по жанру.
    }
}
