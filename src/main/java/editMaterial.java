import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class editMaterial {
    private JPanel rootPanel;
    private JTextField nameCategory;
    private JButton addCategoryButton;
    private JTextField nameMaterial;
    private JButton addMaterialButton;
    private JComboBox<Item> categoryComboBox;
    private JTextField newNameCategory;
    private JButton editCategoryButton;
    private JButton deleteCategoryButton;
    private JComboBox<Item> materialComboBox;
    private JTextField newNameMaterial;
    private JButton editMaterialButton;
    private JButton deleteMaterialButton;
    private JLabel addCategoryLabel;
    private JLabel addMaterialLabel;
    private JComboBox<Item> addMaterialCategoryComboBox;
    private JComboBox<Item> editMaterialCategoryComboBox;
    private JLabel editCategoryLabel;
    private JComboBox<Item> oldCategoryComboBox;
    private JComboBox<Item> perenosMaterialComboBox;
    private JComboBox<Item> newCategoryComboBox;
    private JLabel refreshComplete;
    private JButton perenestiButton;
    private ArrayList<Item> materialItem;
    private ArrayList<Item> categoryItem;

    public editMaterial(){
        JFrame frame = new JFrame();
        frame.setContentPane(rootPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Редактирование материлов");
        frame.setIconImage(Main.image.getImage());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        categoryItem = Main.access.getCategory();
        for (Item aCategoryItem: categoryItem) {
            categoryComboBox.addItem(aCategoryItem);
            addMaterialCategoryComboBox.addItem(aCategoryItem);
            editMaterialCategoryComboBox.addItem(aCategoryItem);
            oldCategoryComboBox.addItem(aCategoryItem);
            newCategoryComboBox.addItem(aCategoryItem);
        }

        if(categoryItem.size() != 0) {
            int id = ((Item) editMaterialCategoryComboBox.getSelectedItem()).getId();
            materialItem = Main.access.getItem(id);

            for (Item aMeaterialItem : materialItem) {
                materialComboBox.addItem(aMeaterialItem);
            }

            id = ((Item)oldCategoryComboBox.getSelectedItem()).getId();
            materialItem = Main.access.getItem(id);

            for (Item aMeaterialItem : materialItem) {
                perenosMaterialComboBox.addItem(aMeaterialItem);
            }

        }

        //Смена материала при смене категории
        editMaterialCategoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox)e.getSource();
                int id = -1;
                if (comboBox.getSelectedItem() != null) {
                    id = ((Item) comboBox.getSelectedItem()).getId();
                }
                materialItem = Main.access.getItem(id);
                materialComboBox.removeAllItems();
                for (Item aMeaterialItem : materialItem){
                    materialComboBox.addItem(aMeaterialItem);
                }
            }
        });

        oldCategoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox)e.getSource();
                int id = -1;
                if (comboBox.getSelectedItem() != null) {
                    id = ((Item) comboBox.getSelectedItem()).getId();
                }
                materialItem = Main.access.getItem(id);
                perenosMaterialComboBox.removeAllItems();
                for (Item aMeaterialItem : materialItem){
                    perenosMaterialComboBox.addItem(aMeaterialItem);
                }
            }
        });
        //Добавление категории
        addCategoryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String name = nameCategory.getText();
                if(nameCategory.getText().equals("Введите название") || name.equals("")){
                    nameCategory.setText("Введите название");
                } else {
                    Main.access.addCategory(name);
                    nameCategory.setText("");
                    addCategoryLabel.setText("Добавлено");
                    categoryItem = Main.access.getCategory();
                    categoryComboBox.removeAllItems();
                    addMaterialCategoryComboBox.removeAllItems();
                    editMaterialCategoryComboBox.removeAllItems();
                    oldCategoryComboBox.removeAllItems();
                    newCategoryComboBox.removeAllItems();
                    categoryComboBox.removeAllItems();
                    categoryComboBox.addItem(new Item(-1, "Весь склад"));
                    for (Item aCategoryItem: categoryItem) {
                        categoryComboBox.addItem(aCategoryItem);
                        addMaterialCategoryComboBox.addItem(aCategoryItem);
                        editMaterialCategoryComboBox.addItem(aCategoryItem);
                        oldCategoryComboBox.addItem(aCategoryItem);
                        newCategoryComboBox.addItem(aCategoryItem);
                        categoryComboBox.addItem(aCategoryItem);
                    }
                }
            }
        });

        //Редактирование категорий
        editCategoryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int id_category = ((Item)categoryComboBox.getSelectedItem()).getId();
                String newName = newNameCategory.getText();
                Main.access.editCategory(id_category, newName);
                categoryItem = Main.access.getCategory();
                categoryComboBox.removeAllItems();
                addMaterialCategoryComboBox.removeAllItems();
                editMaterialCategoryComboBox.removeAllItems();
                oldCategoryComboBox.removeAllItems();
                newCategoryComboBox.removeAllItems();
                for (Item aCategoryItem: categoryItem) {
                    categoryComboBox.addItem(aCategoryItem);
                    addMaterialCategoryComboBox.addItem(aCategoryItem);
                    editMaterialCategoryComboBox.addItem(aCategoryItem);
                    oldCategoryComboBox.addItem(aCategoryItem);
                    newCategoryComboBox.addItem(aCategoryItem);
                }
                editCategoryLabel.setText("Обновлено");
                newNameCategory.setText("");
            }
        });

        //плэйсхолдер для имени категории при добавлении
        nameCategory.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (nameCategory.getText().equals("Введите название") || nameCategory.getText().equals("")) {
                    nameCategory.setForeground(Color.black);
                    nameCategory.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (nameCategory.getText().isEmpty()) {
                    nameCategory.setForeground(Color.LIGHT_GRAY);
                    nameCategory.setText("Введите название");
                }
            }
        });

        //плэйсхолдер для имени категории при редактировании
        newNameCategory.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (newNameCategory.getText().equals("Введите название") || nameCategory.getText().equals("")) {
                    newNameCategory.setForeground(Color.black);
                    newNameCategory.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (newNameCategory.getText().isEmpty()) {
                    newNameCategory.setForeground(Color.LIGHT_GRAY);
                    newNameCategory.setText("Введите название");
                }
            }
        });

        //плэйсхолдер для имени материала при добавлении
        nameMaterial.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (nameMaterial.getText().equals("Введите название") || nameMaterial.getText().equals("")) {
                    nameMaterial.setForeground(Color.black);
                    nameMaterial.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (nameMaterial.getText().isEmpty()) {
                    nameMaterial.setForeground(Color.LIGHT_GRAY);
                    nameMaterial.setText("Введите название");
                }
            }
        });

        //Добавление материала
        addMaterialButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String name = nameMaterial.getText();
                int id = ((Item)addMaterialCategoryComboBox.getSelectedItem()).getId();
                if(nameMaterial.getText().equals("Введите название") || name.equals("")){
                    nameMaterial.setText("Введите название");
                } else {
                    Main.access.addMaterial(name, id);
                    nameMaterial.setText("");
                    addMaterialLabel.setText("Добавлено");
                    id = ((Item)editMaterialCategoryComboBox.getSelectedItem()).getId();
                    materialItem = Main.access.getItem(id);
                    materialComboBox.removeAllItems();
                    for (Item aMaterialComboBox: materialItem) {
                        materialComboBox.addItem(aMaterialComboBox);
                    }
                    id = ((Item)oldCategoryComboBox.getSelectedItem()).getId();
                    materialItem = Main.access.getItem(id);
                    perenosMaterialComboBox.removeAllItems();
                    for (Item aMeaterialItem : materialItem) {
                        perenosMaterialComboBox.addItem(aMeaterialItem);
                    }
                }
            }
        });

        /**Удаление категории**/
        deleteCategoryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int id_category = ((Item)categoryComboBox.getSelectedItem()).getId();
                Main.access.deleteCategory(id_category);
                categoryItem = Main.access.getCategory();
                categoryComboBox.removeAllItems();
                addMaterialCategoryComboBox.removeAllItems();
                editMaterialCategoryComboBox.removeAllItems();
                oldCategoryComboBox.removeAllItems();
                newCategoryComboBox.removeAllItems();
                categoryComboBox.addItem(new Item(-1, "Весь склад"));
                for (Item aCategoryItem: categoryItem) {
                    categoryComboBox.addItem(aCategoryItem);
                    addMaterialCategoryComboBox.addItem(aCategoryItem);
                    editMaterialCategoryComboBox.addItem(aCategoryItem);
                    oldCategoryComboBox.addItem(aCategoryItem);
                    newCategoryComboBox.addItem(aCategoryItem);
                    categoryComboBox.addItem(aCategoryItem);
                }
                editCategoryLabel.setText("Удалено");
            }
        });

        /*Изменение материала*/
        editMaterialButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int id = ((Item)materialComboBox.getSelectedItem()).getId();
                String new_name = newNameMaterial.getText();
                Main.access.editMaterial(id, new_name);
                id = ((Item)editMaterialCategoryComboBox.getSelectedItem()).getId();
                materialItem = Main.access.getItem(id);
                materialComboBox.removeAllItems();
                for (Item aMaterialComboBox: materialItem) {
                    materialComboBox.addItem(aMaterialComboBox);
                }
                id = ((Item)oldCategoryComboBox.getSelectedItem()).getId();
                materialItem = Main.access.getItem(id);
                perenosMaterialComboBox.removeAllItems();
                for (Item aMeaterialItem : materialItem) {
                    perenosMaterialComboBox.addItem(aMeaterialItem);
                }
            }
        });


        deleteMaterialButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int id = ((Item)materialComboBox.getSelectedItem()).getId();
                Main.access.deleteMaterial(id);
                id = ((Item)editMaterialCategoryComboBox.getSelectedItem()).getId();
                materialItem = Main.access.getItem(id);
                materialComboBox.removeAllItems();
                for (Item aMaterialComboBox: materialItem) {
                    materialComboBox.addItem(aMaterialComboBox);
                }
            }
        });

        /*Перенос материала*/
        perenestiButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int new_id = ((Item)newCategoryComboBox.getSelectedItem()).getId();
                int material_id = ((Item)perenosMaterialComboBox.getSelectedItem()).getId();
                Main.access.swapMaterial(new_id, material_id);
                int id = ((Item)editMaterialCategoryComboBox.getSelectedItem()).getId();
                materialItem = Main.access.getItem(id);
                materialComboBox.removeAllItems();
                for (Item aMaterialComboBox: materialItem) {
                    materialComboBox.addItem(aMaterialComboBox);
                }
                id = ((Item)oldCategoryComboBox.getSelectedItem()).getId();
                materialItem = Main.access.getItem(id);
                perenosMaterialComboBox.removeAllItems();
                for (Item aMeaterialItem : materialItem) {
                    perenosMaterialComboBox.addItem(aMeaterialItem);
                }
            }
        });
    }
}
