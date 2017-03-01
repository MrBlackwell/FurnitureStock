import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Валентин on 25.12.2016.
 */
public class StockUser {
    private JFrame frame = new JFrame();
    private JComboBox comboBox1;
    private JTable table1;
    private JPanel rootPanel;

    public StockUser(){
        frame.setContentPane(rootPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Склад");
        frame.setIconImage(Main.image.getImage());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ArrayList<Item> categoryItem = Main.access.getCategory();
        ArrayList<stockType> types = Main.access.getStock(-1);
        TableModel stockTableModel = new stockTableModel(types);
        table1.setModel(stockTableModel);
        comboBox1.addItem(new Item(-1, "Весь склад"));
        for (Item aCategoryItem : categoryItem) {
            comboBox1.addItem(aCategoryItem);
        }

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox)e.getSource();
                int id = -1;
                if (comboBox.getSelectedItem() != null) {
                    id = ((Item) comboBox.getSelectedItem()).getId();
                }
                ArrayList<stockType> types = Main.access.getStock(id);
                TableModel stockTableModel = new stockTableModel(types);
                table1.setModel(stockTableModel);
            }
        });
    }
}
