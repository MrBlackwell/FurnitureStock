import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.util.Properties;

public class Path {
    private JTextField textField1;
    private JButton Button;
    private JLabel label;
    private JPanel rootPanel;
    private JFrame frame = new JFrame();

    public Path(){
        frame.setContentPane(rootPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Путь к БД");
        frame.setIconImage(Main.image.getImage());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final Properties props = new Properties();

        textField1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (textField1.getText().equals("C:\\\\папка1\\папка2\\")) {
                    textField1.setForeground(Color.black);
                    textField1.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textField1.getText().isEmpty()) {
                    textField1.setForeground(Color.LIGHT_GRAY);
                    textField1.setText("C:\\\\папка1\\папка2\\");
                }
            }
        });

        Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!Access.connectDB(textField1.getText())){
                    label.setText("Неверный путь, проверьте указанный путь");
                } else {
                    frame.dispose();
                    props.setProperty("PATH", textField1.getText());
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream("path.ini");
                        props.store(fileOutputStream, "");
                    } catch (Exception ignored) {}
                    new Stock();
                }
            }
        });
    }
}