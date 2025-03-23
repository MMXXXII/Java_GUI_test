import javax.swing.table.AbstractTableModel;
import java.util.List;

public class LibraryTableModel extends AbstractTableModel {
    private List<PrintedEdition> editions;
    private final String[] columnNames = {"Название", "Автор", "Год", "Издательство", "Жанр", "Тип"};

    public LibraryTableModel(List<PrintedEdition> editions) {
        this.editions = editions;
    }

    @Override
    public int getRowCount() {
        return editions.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PrintedEdition edition = editions.get(rowIndex);
        switch (columnIndex) {
            case 0: return edition.getTitle();
            case 1: return edition.getAuthor();
            case 2: return edition.getYear();
            case 3: return edition.getPublisher();
            case 4: return edition.getGenre(); // Здесь должно быть genre, а не type
            case 5: return edition.getType();  // Это тип издания (книга, учебник, журнал)
            default: return null;
        }
    }


    public void updateData(List<PrintedEdition> newEditions) {
        this.editions = newEditions;
        fireTableDataChanged();
    }
}
