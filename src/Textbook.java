public class Textbook extends PrintedEdition {
    public Textbook(int id, String title, String author, int year, String publisher, String genre) {
        super(id, title, author, year, publisher, genre);
    }

    @Override
    public String getType() {
        return "Учебник";
    }
}