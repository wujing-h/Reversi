package src;
import src.controller.Game;
import src.view.GUI.GameFrame;
import src.view.IO.ConsoleGameIO;
import src.view.IO.GameIO;
import src.view.IO.SwingGameIO;
public class Main {  
    public static void main(String[] args) {
        GameIO gameIO_1 = new ConsoleGameIO();
        GameIO gameIO_2 = new SwingGameIO();
        Game game = new Game(gameIO_2);
        javax.swing.SwingUtilities.invokeLater(() -> {
            GameFrame gameFrame = new GameFrame(game);
        });
    }
}
