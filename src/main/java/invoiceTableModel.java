import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Валентин on 02.08.2016.
 */
public class invoiceTableModel implements TableModel{
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private List<stockType> stockTypes;

    public invoiceTableModel(List<stockType> stockTypes){
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
        if(columnIndex==1){
            return true;
        } else {
            return false;
        }
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
        if (columnIndex==0) {
            stockTypes.get(rowIndex).setName_materials((String)aValue);
        } else {
            stockTypes.get(rowIndex).setCount((String)aValue);
        }
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
