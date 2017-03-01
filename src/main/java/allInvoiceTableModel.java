import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class allInvoiceTableModel implements TableModel {
    private Set<TableModelListener> listeners = new HashSet<>();
    private List<invoiceType> invoiceTypes;

    public allInvoiceTableModel(ArrayList<invoiceType> invoicetypes){
        this.invoiceTypes = invoicetypes;
    }

    @Override
    public int getRowCount() {
        return invoiceTypes.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Номер";
            case 1:
                return "Дата";
            case 2:
                return "Тип накладной";
            case 3:
                return "Материал в накладной";
            case 4:
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
        invoiceType type = invoiceTypes.get(rowIndex);
        switch (columnIndex){
            case 0:
                return type.getNumberInvoice();
            case 1:
                return type.getDateInvoice();
            case 2:
                if(type.isDelivery() == 0){
                    return "Добавление на склад";
                } else if(type.isDelivery() == 1) {
                    return "Уход со склада";
                } else {
                    return "";
                }
            case 3:
                return type.getMaterialInvoice();
            case 4:
                return type.getCountMaterials();
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

class invoiceType {
    private String id;
    private String numberInvoice;
    private String dateInvoice;
    private String materialInvoice;
    private String countMaterials;
    private int isDelivery;
    private String id_material;

    public invoiceType(){}

    public invoiceType(String id, String id_material, String numberInvoice, String dateInvoice, String materialInvoice,
                       String countMaterials, int isDelivery){
        this.id = id;
        this.id_material = id_material;
        this.numberInvoice=numberInvoice;
        this.materialInvoice=materialInvoice;
        this.countMaterials=countMaterials;
        this.isDelivery = isDelivery;
        this.dateInvoice = dateInvoice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_material() {
        return id_material;
    }

    public void setId_material(String id_material) {
        this.id_material = id_material;
    }

    public String getCountMaterials() {
        return countMaterials;
    }

    public String getMaterialInvoice() {
        return materialInvoice;
    }

    public String getNumberInvoice() {
        return numberInvoice;
    }

    public int isDelivery() {
        return isDelivery;
    }

    public String getDateInvoice() {
        return dateInvoice;
    }

    public void setCountMaterials(String countMaterials) {
        this.countMaterials = countMaterials;
    }

    public void setMaterialInvoice(String materialInvoice) {
        this.materialInvoice = materialInvoice;
    }

    public void setNumberInvoice(String numberInvoice) {
        this.numberInvoice = numberInvoice;
    }

    public void setDelivery(int delivery) {
        isDelivery = delivery;
    }
}
