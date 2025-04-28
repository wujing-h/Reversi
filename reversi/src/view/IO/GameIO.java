package src.view.IO;
import javax.swing.ImageIcon;

public interface GameIO {
    String readLine(String prompt);
    String readLine(String prompt, ImageIcon icon);
    void println(String message);
}
