import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Валентин on 03.08.2016.
 */
public class editInvoice /*extends JFrame*/{
    private JFrame frame = new JFrame();
    private JComboBox chooseMaterial;
    private JTextField numberInvoice;
    private JFormattedTextField dateInvoice;
    private JTextField countMaterial;
    private JButton addMaterialEdit;
    private JTable table1;
    private JButton editButton;
    private JLabel errorNumberInvoice;
    private JLabel errorDateInvoice;
    private JPanel rootPanel;
    private JButton deleteButton;
    private JComboBox chooseCategory;
    private ArrayList<Item> categoryItem = Main.access.getCategory();
    private ArrayList<Item> materialItem = new ArrayList<>();


    public editInvoice(String id, String idMaterial, String number, String date, final int type, String material, String count){
        try {
            MaskFormatter maskFormatter = new MaskFormatter("##.##.####");
            maskFormatter.setPlaceholderCharacter('_');
            maskFormatter.setPlaceholder(date);
            maskFormatter.install(dateInvoice);
        } catch (ParseException e){}
        numberInvoice.setText(number);
        String[] materials = material.split("\n");
        String[] counts = count.split("\n");
        final String[] ids = id.split("\n");
        final String[] idMaterials = idMaterial.split("\n");
        final ArrayList<stockType> editInvoice = new ArrayList<>();
        final ArrayList<Integer> editInvoiceID = new ArrayList<>();
        final int[] counter = {materials.length};
        for(int i = 0; i < counter[0]; i++){
            editInvoice.add(new stockType(materials[i], counts[i]));
        }
        TableModel editInvoiceTableModel = new invoiceTableModel(editInvoice);
        table1.setModel(editInvoiceTableModel);

        final JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Удалить");
        popupMenu.add(menuItem);

        categoryItem = Main.access.getCategory();
        for (Item aCetegoryItem: categoryItem) {
            chooseCategory.addItem(aCetegoryItem);
        }

        int mat_id = ((Item)chooseCategory.getSelectedItem()).getId();
        materialItem = Main.access.getItem(mat_id);
        chooseMaterial.removeAllItems();
        for (Item aMeaterialItem : materialItem){
            chooseMaterial.addItem(aMeaterialItem);
        }


        frame.setContentPane(rootPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Редактирование накладной");
        frame.setIconImage(Main.image.getImage());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        chooseCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox)e.getSource();
                int id = -1;
                if (comboBox.getSelectedItem() != null) {
                    id = ((Item) comboBox.getSelectedItem()).getId();
                }
                materialItem = Main.access.getItem(id);
                chooseMaterial.removeAllItems();
                for (Item aMeaterialItem : materialItem){
                    chooseMaterial.addItem(aMeaterialItem);
                }
            }
        });

        countMaterial.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (countMaterial.getText().equals("Количество материала")) {
                    countMaterial.setForeground(Color.black);
                    countMaterial.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (countMaterial.getText().isEmpty()) {
                    countMaterial.setForeground(Color.LIGHT_GRAY);
                    countMaterial.setText("Количество материала");
                }
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isRightMouseButton(e)){
                    popupMenu.show(table1, e.getX(), e.getY());
                    int row = table1.rowAtPoint(e.getPoint());
                    table1.setRowSelectionInterval(row, row);
                }
            }
        });

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if(row >= counter[0]){
                    editInvoice.remove(row);
                    editInvoice.remove(row- counter[0]);
                    TableModel editInvoiceTableModel = new invoiceTableModel(editInvoice);
                    table1.setModel(editInvoiceTableModel);
                } else {
                    editInvoice.remove(row);
                    TableModel editInvoiceTableModel = new invoiceTableModel(editInvoice);
                    table1.setModel(editInvoiceTableModel);
                    if(type==0){
                        Main.access.deleteRow(ids[row], "Delivery");
                    } else if(type==1){
                        Main.access.deleteRow(ids[row], "Shipment");
                    }
                    for(int j = row; j < ids.length - 1; j++){
                        ids[j] = ids[j+1];
                    }
                    counter[0]--;
                }

            }
        });

        addMaterialEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String count = countMaterial.getText();
                String name = chooseMaterial.getSelectedItem().toString();
                Pattern pattern = Pattern.compile("^[0-9]{1,6}$");
                Matcher matcher = pattern.matcher(count);
                if(matcher.matches()){
                    editInvoiceID.add(((Item)chooseMaterial.getSelectedItem()).getId());
                    editInvoice.add(new stockType(name, count));
                    TableModel editInvoiceTableModel = new invoiceTableModel(editInvoice);
                    table1.setModel(editInvoiceTableModel);
                    countMaterial.setText("");
                }
            }
        });


        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(type==0){
                    String number = numberInvoice.getText();
                    String date = dateInvoice.getText();
                    if(number.equals("")){
                        errorNumberInvoice.setText("Введите номер!!");
                        errorNumberInvoice.setForeground(Color.red);
                    }
                    if(date.equals("__.__.____")){
                        errorDateInvoice.setText("Введите дату!!");
                        errorDateInvoice.setForeground(Color.red);
                    }
                    if (!number.equals("") && !date.equals("__.__.____")) {
                        for(int i = 0; i < counter[0]; i++){
                            int idMaterial = Integer.parseInt(idMaterials[i]);
                            int count = Integer.parseInt(editInvoice.get(i).getCount());
                            Main.access.updateData(ids[i], "Delivery", number, date, idMaterial, count);
                        }
                        if(editInvoice.size()> counter[0]){
                            for(int i = counter[0]; i < editInvoice.size(); i++){
                                int idMaterial = editInvoiceID.get(i- counter[0]);
                                int count = Integer.parseInt(editInvoice.get(i).getCount());
                                Main.access.addToStock(number, date, idMaterial, count);
                            }
                        }
                    }
                } else if(type==1){
                    String number = numberInvoice.getText();
                    String date = dateInvoice.getText();
                    if(number.equals("")){
                        errorNumberInvoice.setText("Введите номер!!");
                        errorNumberInvoice.setForeground(Color.red);
                    }
                    if(date.equals("__.__.____")){
                        errorDateInvoice.setText("Введите дату!!");
                        errorDateInvoice.setForeground(Color.red);
                    }
                    if (!number.equals("") && !date.equals("__.__.____")) {
                        for(int i = 0; i < counter[0]; i++){
                            int idMaterial = Integer.parseInt(idMaterials[i]);
                            int count = Integer.parseInt(editInvoice.get(i).getCount());
                            Main.access.updateData(ids[i], "Shipment", number, date, idMaterial, count);
                        }
                        if(editInvoice.size()> counter[0]){
                            for(int i = counter[0]; i < editInvoice.size(); i++){
                                int idMaterial = editInvoiceID.get(i- counter[0]);
                                int count = Integer.parseInt(editInvoice.get(i).getCount());
                                Main.access.deleteFromStock(number, date, idMaterial, count);
                            }
                        }
                    }
                }
                frame.dispose();
            }
        });
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(type==0){
                    for (int i = 0; i < ids.length; i++){
                        Main.access.deleteRow(ids[i], "Delivery");
                    }
                } else if(type==1){
                    for (int i = 0; i < ids.length; i++){
                        Main.access.deleteRow(ids[i], "Shipment");
                    }
                }
                frame.dispose();
            }
        });
    }
}
