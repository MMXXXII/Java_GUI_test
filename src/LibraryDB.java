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

    public List<PrintedEdition> getEditions() {
        List<PrintedEdition> editions = new ArrayList<>();
        String sql = "SELECT * FROM editions";

        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");  // Извлекаем ID
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("year");
                String publisher = rs.getString("publisher");
                String genre = rs.getString("genre");
                String type = rs.getString("type");

                PrintedEdition edition;
                switch (type) {
                    case "Книга":
                        edition = new Book(id, title, author, year, publisher, genre); // Передаем ID
                        break;
                    case "Учебник":
                        edition = new Textbook(id, title, author, year, publisher, genre);
                        break;
                    case "Журнал":
                        edition = new Magazine(id, title, author, year, publisher, genre);
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

        System.out.println("Извлечено из базы данных: " + editions.size() + " записей.");
        return editions;
    }


    public int generateNewId() {
        String sql = "SELECT MAX(id) FROM editions";  // Получаем максимальный ID из базы данных

        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1) + 1; // Возвращаем следующий ID
            } else {
                return 1;  // Если нет записей, начинаем с 1
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ошибка при получении нового ID: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return -1;  // В случае ошибки возвращаем -1
        }
    }


    // Обновление издания в базе данных
    public void updateEdition(PrintedEdition oldEdition, PrintedEdition newEdition) {
        String sql = "UPDATE editions SET title = ?, author = ?, year = ?, publisher = ?, genre = ?, type = ? WHERE id = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newEdition.getTitle());
            stmt.setString(2, newEdition.getAuthor());
            stmt.setInt(3, newEdition.getYear());
            stmt.setString(4, newEdition.getPublisher());
            stmt.setString(5, newEdition.getGenre());
            stmt.setString(6, newEdition.getType());
            stmt.setInt(7, oldEdition.getId()); // Используем ID для поиска старой записи

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ошибка при обновлении издания в базе данных: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public List<PrintedEdition> searchEdition(String keyword) {
        List<PrintedEdition> editions = new ArrayList<>();

        // Убедимся, что ключевое слово не пустое
        if (keyword == null || keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Введите корректное название!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return editions;
        }

        // SQL-запрос для поиска изданий по названию
        String sql = "SELECT * FROM editions WHERE title LIKE ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Используем '%' в начале и в конце для поиска изданий, содержащих введенную строку
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("year");
                String publisher = rs.getString("publisher");
                String genre = rs.getString("genre");
                String type = rs.getString("type");

                PrintedEdition edition;
                switch (type) {
                    case "Книга":
                        edition = new Book(id, title, author, year, publisher, genre);
                        break;
                    case "Учебник":
                        edition = new Textbook(id, title, author, year, publisher, genre);
                        break;
                    case "Журнал":
                        edition = new Magazine(id, title, author, year, publisher, genre);
                        break;
                    default:
                        edition = null;
                }

                if (edition != null) {
                    editions.add(edition);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ошибка при поиске изданий в базе данных: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return editions;
    }


}
