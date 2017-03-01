import javax.swing.*;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;


public class Main {
    static Access access = new Access();
    static ImageIcon image = new ImageIcon(
            "src\\main\\resources\\dolly.png");

    public static void main(String[] args){
        try {
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        String path="";
        Properties props = new Properties();
        try {
            props.load(new InputStreamReader
                    (new FileInputStream("path.ini"), "windows-1251"));
            path = (props.getProperty("PATH", ""));
        } catch (Exception ignored) {}
        if(path.equals("")){
            new Path();
        } else {
            if(Access.connectDB(path)){
                new login();
            } else {
                new Path();
            }
        }
    }
}
