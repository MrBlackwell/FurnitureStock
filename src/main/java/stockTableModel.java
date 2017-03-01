import javax.swing.event.ListDataEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Валентин on 27.07.2016.
 */
public class stockTableModel implements TableModel{
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private List<stockType> stockTypes;

    public stockTableModel(List<stockType> stockTypes){
        this.stockTypes=stockTypes;
    }

    @Override
    public int getRowCount() {
        return stockTypes.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex){
            case 0:
                return "Материал";
            case 1:
                return "Количество";
            default:
                return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        stockType type = stockTypes.get(rowIndex);
        switch (columnIndex){
            case 0:
                return type.getName_materials();
            case 1:
                return type.getCount();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }
}

class stockType {
    private String name_materials;
    private String count;

    public stockType(){}

    public stockType(String name_materials, String count){
        this.name_materials = name_materials;
        this.count = count;
    }

    public String getCount() {
        return count;
    }

    public String getName_materials() {
        return name_materials;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setName_materials(String name_materials) {
        this.name_materials = name_materials;
    }
}