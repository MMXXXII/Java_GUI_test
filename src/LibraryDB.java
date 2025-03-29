import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryDB {
    // Метод для добавления нового издания в базу данных
    public void addEdition(PrintedEdition edition) {
        String sql = "INSERT INTO editions (title, author, year, publisher, genre, type) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, edition.getTitle());
            stmt.setString(2, edition.getAuthor());
            stmt.setInt(3, edition.getYear());
            stmt.setString(4, edition.getPublisher());
            stmt.setString(5, edition.getGenre());
            stmt.setString(6, edition.getType());

            stmt.executeUpdate();
        } catch (SQLException e) {
            // Покажет сообщение об ошибке в случае сбоя вставки данных в базу
            JOptionPane.showMessageDialog(null, "Ошибка при добавлении издания в базу данных: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            // Выведет подробности об ошибке в консоль
            e.printStackTrace();
        }
    }

    public void removeEdition(PrintedEdition edition) {
        String sql = "DELETE FROM editions WHERE title = ? AND author = ? AND year = ? AND publisher = ? AND genre = ? AND type = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, edition.getTitle());
            stmt.setString(2, edition.getAuthor());
            stmt.setInt(3, edition.getYear());
            stmt.setString(4, edition.getPublisher());
            stmt.setString(5, edition.getGenre());
            stmt.setString(6, edition.getType());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ошибка при удалении издания из базы данных: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Метод для получения всех изданий из базы данных
    public List<PrintedEdition> getEditions() {
        List<PrintedEdition> editions = new ArrayList<>();
        String sql = "SELECT * FROM editions";

        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("year");
                String publisher = rs.getString("publisher");
                String genre = rs.getString("genre");
                String type = rs.getString("type");

                PrintedEdition edition;
                switch (type) {
                    case "Книга":
                        edition = new Book(title, author, year, publisher, genre);
                        break;
                    case "Учебник":
                        edition = new Textbook(title, author, year, publisher, genre);
                        break;
                    case "Журнал":
                        edition = new Magazine(title, author, year, publisher, genre);
                        break;
                    default:
                        edition = null;
                }

                if (edition != null) {
                    editions.add(edition);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // Для отладки
        System.out.println("Извлечено из базы данных: " + editions.size() + " записей.");
        return editions;
    }

}
