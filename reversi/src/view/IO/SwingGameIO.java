package src.view.IO;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class SwingGameIO implements GameIO{
    @Override
    public String readLine(String prompt) {
        return JOptionPane.showInputDialog(null,
                                            prompt,"输入提示",
                                            JOptionPane.QUESTION_MESSAGE);
    }
    @Override
    public String readLine(String prompt,ImageIcon icon) {
        return (String) JOptionPane.
        showInputDialog(
            null, 
            prompt,
            "输入提示",
            JOptionPane.QUESTION_MESSAGE,
            icon,
            null,
            null);
    }
    @Override
    public void println(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}

