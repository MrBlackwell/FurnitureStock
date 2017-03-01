import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Валентин on 25.12.2016.
 */
public class login{
    private JFrame frame = new JFrame();
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton adminButton;
    private JButton userButton;
    private JPanel rootPanel;

    login(){

        frame.setContentPane(rootPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Склад");
        frame.setIconImage(Main.image.getImage());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        userButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new StockUser();
            }
        });
        adminButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(textField1.getText().equals("admin") && passwordField1.getText().equals("12345")){
                    frame.dispose();
                    new Stock();
                }
            }
        });
    }
}
