package src.model;
import java.awt.Image;
import javax.swing.ImageIcon;

import src.view.IO.GameIO;
public class Player {
    private final String name;//玩家名称
    private final Piece piece;//玩家棋子
    private final ImageIcon icon;//玩家图标
    public Player(char piece,GameIO gameIO,String name){
        ImageIcon iconOrigin = new ImageIcon("resources/player.jpg");
        Image scaledImage = iconOrigin.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        this.icon = new ImageIcon(scaledImage);
        this.name = gameIO.readLine("请输入玩家"+name+"名称",icon);
        this.piece = new Piece(piece);
    }
    public String getName(){
        return name;
    }
    public Piece getPiece(){
        return piece;
    }
}
