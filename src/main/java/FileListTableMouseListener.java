import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.TableModel;

public class FileListTableMouseListener extends MouseAdapter {

    // собственно экземпляр JTable, к которому этот листенер и прикрепляем
    private JTable fileListTable;
    private boolean singleClick  = true;
    private Timer timer;


    public FileListTableMouseListener(JTable fileListTable) {

        this.fileListTable = fileListTable;

        ActionListener actionListener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                timer.stop();
                if (singleClick) {
                    singleClickHandler(e);
                } else {
                    doubleClickHandler(e);
                }
            }
        };

        int doubleClickDelay = 200;
        timer = new Timer(doubleClickDelay, actionListener);
        timer.setRepeats(false);
    }


    public void mouseClicked(MouseEvent e) {

        if (e.getClickCount() == 1) {
            singleClick = true;
            timer.start();
        } else {
            singleClick = false;
        }
    }


    private void singleClickHandler(ActionEvent e) {
    }


    private void doubleClickHandler(ActionEvent e) {
        int selectedRow = fileListTable.getSelectedRow();
        if(selectedRow > -1) {
            TableModel model = fileListTable.getModel();
            String numberInvoice = model.getValueAt(selectedRow, 0).toString();
            String dateInvoice = model.getValueAt(selectedRow, 1).toString();
            int type;
            if (model.getValueAt(selectedRow, 2).toString().equals("Добавление на склад")) {
                type = 0;
            } else if (model.getValueAt(selectedRow, 2).toString().equals("Уход со склада")) {
                type = 1;
            } else {
                type = 3;
            }
            String material = model.getValueAt(selectedRow, 3).toString();
            String count = model.getValueAt(selectedRow, 4).toString();
            String id = Stock.invoiceTypes[0].get(selectedRow).getId();
            String idMaterial = Stock.invoiceTypes[0].get(selectedRow).getId_material();
            new editInvoice(id, idMaterial, numberInvoice, dateInvoice, type, material, count);
        }
    }
}