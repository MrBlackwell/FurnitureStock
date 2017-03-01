import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;

/**
 * Created by Валентин on 12.08.2016.
 */
class ItemRenderer extends BasicComboBoxRenderer {
    private static final long serialVersionUID = 1L;

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value != null) {
            Item item = (Item) value;
            setText(item.getDescription().toUpperCase());
        }
        if (index == -1) {
            Item item = (Item) value;
            setText("" + item.getId());
        }
        return this;
    }
}

class Item {

    private int id;
    private String description;

    public Item(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
