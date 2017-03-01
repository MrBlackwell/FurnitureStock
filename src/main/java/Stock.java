import javax.swing.*;
import java.awt.*;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Stock extends JFrame{
    private JTabbedPane tabbedPane1;
    private JPanel rootPanel;
    private JButton addMaterial;
    private JButton addToStock;
    private JTextField numberInvoice;
    private JTextField countMaterialTextField;
    private JComboBox<Item> changeMaterials;
    private JButton addMaterialButton;
    private JTable tableStock;
    private JTable invoiceDeliveryTable;
    private JFormattedTextField dateInvoice;
    private JTextField shipmentNumberInvoice;
    private JFormattedTextField shipmentDateInvoice;
    private JComboBox<Item> chooseShipmentMaterial;
    private JTextField countShipmentMaterial;
    private JButton addShipmentMaterial;
    private JTable shipmentInvoiceTable;
    private JButton shipment;
    private JLabel errorNumberDelyvery;
    private JLabel errorDateDelivery;
    private JLabel errorNumberShipment;
    private JLabel errorDateShipment;
    private JTable invoiceTable;
    private JScrollPane scroollPane;
    private JComboBox<Item> changeCategory;
    private JComboBox<Item> changeShipmentCategory;
    private JComboBox<Item> categoryComboBox;
    private ArrayList<Item> categoryItem = Main.access.getCategory();
    private ArrayList<Item> materialItem = new ArrayList<>();
    final static ArrayList<invoiceType> invoiceTypes[] = new ArrayList[1];


    public Stock(){
        try {
            MaskFormatter maskFormatter = new MaskFormatter("##.##.####");
            MaskFormatter maskFormatter1 = new MaskFormatter("##.##.####");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            maskFormatter.setPlaceholderCharacter('_');
            maskFormatter.setPlaceholder(dateFormat.format(new Date()));
            maskFormatter1.setPlaceholderCharacter('_');
            maskFormatter1.setPlaceholder(dateFormat.format(new Date()));
            maskFormatter.install(dateInvoice);
            maskFormatter1.install(shipmentDateInvoice);
        } catch (ParseException ignored){}

        ArrayList<stockType> types = Main.access.getStock(-1);
        Stock.invoiceTypes[0] = Main.access.getAllInvoice();
        final ArrayList<stockType> invoiceDelivery = new ArrayList<>();
        final ArrayList<Integer> invoiceDeliveryID = new ArrayList<>();
        final ArrayList<stockType> invoiceShipment = new ArrayList<>();
        final ArrayList<Integer> invoiceShipmentID = new ArrayList<>();
        TableModel stockTableModel = new stockTableModel(types);
        TableModel invoiceDeliveryModel = new invoiceTableModel(invoiceDelivery);
        TableModel shipmentTableModel = new invoiceTableModel(invoiceShipment);
        TableModel allinvoiceTableModel = new allInvoiceTableModel(invoiceTypes[0]);

        shipmentInvoiceTable.setModel(shipmentTableModel);
        invoiceDeliveryTable.setModel(invoiceDeliveryModel);
        tableStock.setModel(stockTableModel);
        invoiceTable.setModel(allinvoiceTableModel);
        invoiceTable.setDefaultRenderer(Object.class, new TextAreaRenderer());
        invoiceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        categoryComboBox.addItem(new Item(-1, "Весь склад"));
        for (Item aCategoryItem : categoryItem) {
            changeCategory.addItem(aCategoryItem);
            changeShipmentCategory.addItem(aCategoryItem);
            categoryComboBox.addItem(aCategoryItem);
        }

        if (categoryItem.size() != 0) {
            int id = ((Item) changeCategory.getSelectedItem()).getId();
            materialItem = Main.access.getItem(id);
            changeMaterials.removeAllItems();
            for (Item aMeaterialItem : materialItem) {
                changeMaterials.addItem(aMeaterialItem);
            }

            id = ((Item) changeShipmentCategory.getSelectedItem()).getId();
            materialItem = Main.access.getItem(id);
            chooseShipmentMaterial.removeAllItems();
            for (Item aMeaterialItem : materialItem) {
                chooseShipmentMaterial.addItem(aMeaterialItem);
            }
        }

        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox)e.getSource();
                int id = -1;
                if (comboBox.getSelectedItem() != null) {
                    id = ((Item) comboBox.getSelectedItem()).getId();
                }
                ArrayList<stockType> types = Main.access.getStock(id);
                TableModel stockTableModel = new stockTableModel(types);
                tableStock.setModel(stockTableModel);
            }
        });

        changeCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox)e.getSource();
                int id = -1;
                if (comboBox.getSelectedItem() != null) {
                    id = ((Item) comboBox.getSelectedItem()).getId();
                }
                materialItem = Main.access.getItem(id);
                changeMaterials.removeAllItems();
                for (Item aMeaterialItem : materialItem){
                    changeMaterials.addItem(aMeaterialItem);
                }
            }
        });

        changeShipmentCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox)e.getSource();
                int id = -1;
                if (comboBox.getSelectedItem() != null) {
                    id = ((Item) comboBox.getSelectedItem()).getId();
                }
                materialItem = Main.access.getItem(id);
                chooseShipmentMaterial.removeAllItems();
                for (Item aMeaterialItem : materialItem){
                    chooseShipmentMaterial.addItem(aMeaterialItem);
                }
            }
        });

        invoiceTable.addMouseListener(new FileListTableMouseListener(invoiceTable));

        final JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Удалить");
        popupMenu.add(menuItem);
        invoiceDeliveryTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isRightMouseButton(e)){
                    popupMenu.show(invoiceDeliveryTable, e.getX(), e.getY());
                    int row = invoiceDeliveryTable.rowAtPoint(e.getPoint());
                    invoiceDeliveryTable.setRowSelectionInterval(row, row);
                }
            }
        });

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invoiceDelivery.remove(invoiceDeliveryTable.getSelectedRow());
                invoiceDeliveryID.remove(invoiceDeliveryTable.getSelectedRow());
                TableModel invoiceDeliveryModel = new invoiceTableModel(invoiceDelivery);
                invoiceDeliveryTable.setModel(invoiceDeliveryModel);
                invoiceDeliveryTable.getSelectionModel().clearSelection();
            }
        });

        final JPopupMenu popupMenu1 = new JPopupMenu();
        JMenuItem menuItem1 = new JMenuItem("Удалить");
        popupMenu1.add(menuItem1);
        shipmentInvoiceTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isRightMouseButton(e)){
                    popupMenu1.show(shipmentInvoiceTable, e.getX(), e.getY());
                    int row = shipmentInvoiceTable.rowAtPoint(e.getPoint());
                    shipmentInvoiceTable.setRowSelectionInterval(row, row);
                }
            }
        });

        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invoiceShipment.remove(shipmentInvoiceTable.getSelectedRow());
                invoiceShipmentID.remove(shipmentInvoiceTable.getSelectedRow());
                TableModel shipmentTableModel = new invoiceTableModel(invoiceShipment);
                shipmentInvoiceTable.setModel(shipmentTableModel);
                shipmentInvoiceTable.getSelectionModel().clearSelection();
            }
        });

        setContentPane(rootPanel);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setTitle("Склад");
        setIconImage(Main.image.getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        addMaterial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new editMaterial();
            }
        });
        countMaterialTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (countMaterialTextField.getText().equals("Количество материала")) {
                    countMaterialTextField.setForeground(Color.black);
                    countMaterialTextField.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (countMaterialTextField.getText().isEmpty()) {
                    countMaterialTextField.setForeground(Color.LIGHT_GRAY);
                    countMaterialTextField.setText("Количество материала");
                }
            }
        });
        addMaterialButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String count = countMaterialTextField.getText();
                String name = ((Item)changeMaterials.getSelectedItem()).getDescription();
                Pattern pattern = Pattern.compile("^[0-9]{1,6}$");
                Matcher matcher = pattern.matcher(count);
                if(matcher.matches()){
                    invoiceDeliveryID.add(((Item)changeMaterials.getSelectedItem()).getId());
                    invoiceDelivery.add(new stockType(name, count));
                    TableModel invoiceDeliveryModel = new invoiceTableModel(invoiceDelivery);
                    invoiceDeliveryTable.setModel(invoiceDeliveryModel);
                    countMaterialTextField.setText("");
                }
            }
        });
        addToStock.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String number = numberInvoice.getText();
                String date = dateInvoice.getText();
                if(number.equals("")){
                    errorNumberDelyvery.setText("Введите номер!!");
                    errorNumberDelyvery.setForeground(Color.red);
                }
                if(date.equals("__.__.____")){
                    errorDateDelivery.setText("Введите дату!!");
                    errorDateDelivery.setForeground(Color.red);
                }
                if (!number.equals("") && !date.equals("__.__.____")) {
                    for (int i = 0; i < invoiceDelivery.size(); i++) {
                        int id = invoiceDeliveryID.get(i);
                        int count = Integer.parseInt(invoiceDelivery.get(i).getCount());
                        Main.access.addToStock(number, date, id, count);
                    }
                    ArrayList<stockType> types = Main.access.getStock(-1);
                    TableModel stockTableModel = new stockTableModel(types);
                    invoiceTypes[0] = Main.access.getAllInvoice();
                    TableModel allinvoiceTableModel = new allInvoiceTableModel(invoiceTypes[0]);
                    invoiceTable.setModel(allinvoiceTableModel);
                    invoiceTable.setDefaultRenderer(Object.class, new TextAreaRenderer());
                    invoiceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    tableStock.setModel(stockTableModel);
                    numberInvoice.setText("");
                    countMaterialTextField.setText("");
                    invoiceDelivery.clear();
                    invoiceDeliveryID.clear();
                    TableModel invoiceDeliveryModel = new stockTableModel(invoiceDelivery);
                    invoiceDeliveryTable.setModel(invoiceDeliveryModel);
                    errorNumberDelyvery.setText("");
                    errorDateDelivery.setText("");
                }
            }
        });

        //Слушатели "Отгрузки"
        countShipmentMaterial.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (countShipmentMaterial.getText().equals("Количество материала")) {
                    countShipmentMaterial.setForeground(Color.black);
                    countShipmentMaterial.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (countShipmentMaterial.getText().isEmpty()) {
                    countShipmentMaterial.setForeground(Color.LIGHT_GRAY);
                    countShipmentMaterial.setText("Количество материала");
                }
            }
        });

        addShipmentMaterial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String count = countShipmentMaterial.getText();
                String name = ((Item)chooseShipmentMaterial.getSelectedItem()).getDescription();
                Pattern pattern = Pattern.compile("^[0-9]{1,6}$");
                Matcher matcher = pattern.matcher(count);
                if(matcher.matches()){
                    invoiceShipmentID.add(((Item)chooseShipmentMaterial.getSelectedItem()).getId());
                    invoiceShipment.add(new stockType(name, count));
                    TableModel shipmentTableModel = new invoiceTableModel(invoiceShipment);
                    shipmentInvoiceTable.setModel(shipmentTableModel);
                    countShipmentMaterial.setText("");
                }
            }
        });

        shipment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String number = shipmentNumberInvoice.getText();
                String date = shipmentDateInvoice.getText();
                if(number.equals("")){
                    errorNumberShipment.setText("Введите номер!!");
                    errorNumberShipment.setForeground(Color.red);
                }
                if(date.equals("__.__.____")){
                    errorDateShipment.setText("Введите дату!!");
                    errorDateShipment.setForeground(Color.red);
                }
                if (!number.equals("") && !date.equals("__.__.____")) {
                    for (int i = 0; i < invoiceShipment.size(); i++) {
                        int id = invoiceShipmentID.get(i);
                        int count = Integer.parseInt(invoiceShipment.get(i).getCount());
                        Main.access.deleteFromStock(number, date, id, count);
                    }
                    ArrayList<stockType> types = Main.access.getStock(-1);
                    TableModel stockTableModel = new stockTableModel(types);
                    invoiceTypes[0] = Main.access.getAllInvoice();
                    TableModel allinvoiceTableModel = new allInvoiceTableModel(invoiceTypes[0]);
                    invoiceTable.setModel(allinvoiceTableModel);
                    invoiceTable.setDefaultRenderer(Object.class, new TextAreaRenderer());
                    invoiceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    tableStock.setModel(stockTableModel);
                    shipmentNumberInvoice.setText("");
                    countShipmentMaterial.setText("");
                    invoiceShipment.clear();
                    invoiceShipmentID.clear();
                    TableModel shipmentTableModel = new stockTableModel(invoiceShipment);
                    shipmentInvoiceTable.setModel(shipmentTableModel);
                    errorNumberShipment.setText("");
                    errorDateShipment.setText("");
                }
            }
        });


        scroollPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                invoiceTypes[0] = Main.access.getAllInvoice();
                TableModel allinvoiceTableModel = new allInvoiceTableModel(invoiceTypes[0]);
                invoiceTable.setModel(allinvoiceTableModel);
                invoiceTable.setDefaultRenderer(Object.class, new TextAreaRenderer());
                invoiceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                ArrayList<stockType> types = Main.access.getStock(-1);
                TableModel stockTableModel = new stockTableModel(types);
                tableStock.setModel(stockTableModel);
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                super.windowActivated(e);
                changeCategory.removeAllItems();
                changeShipmentCategory.removeAllItems();
                categoryComboBox.removeAllItems();
                categoryItem = Main.access.getCategory();
                categoryComboBox.addItem(new Item(-1, "Весь склад"));
                for (Item aCategoryItem : categoryItem) {
                    changeCategory.addItem(aCategoryItem);
                    changeShipmentCategory.addItem(aCategoryItem);
                    categoryComboBox.addItem(aCategoryItem);
                }
                if (categoryItem.size() !=0) {
                    int id = ((Item) changeCategory.getSelectedItem()).getId();
                    materialItem = Main.access.getItem(id);
                    changeMaterials.removeAllItems();

                    for (Item aMeaterialItem : materialItem) {
                        changeMaterials.addItem(aMeaterialItem);
                    }

                    id = ((Item) changeShipmentCategory.getSelectedItem()).getId();
                    materialItem = Main.access.getItem(id);
                    chooseShipmentMaterial.removeAllItems();
                    for (Item aMeaterialItem : materialItem) {
                        chooseShipmentMaterial.addItem(aMeaterialItem);
                    }
                }
            }
        });
    }
}