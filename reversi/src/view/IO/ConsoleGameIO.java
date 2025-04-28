package src.view.IO;

import java.util.Scanner;
import javax.swing.ImageIcon;

public class ConsoleGameIO implements GameIO {
    private final Scanner scanner = new Scanner(System.in);
    @Override
    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    @Override
    public void println(String message) {
        System.out.println(message);
    }
    @Override
    public String readLine(String prompt, ImageIcon icon) {
        throw new UnsupportedOperationException("Unimplemented method 'readLine'");
    }
}
