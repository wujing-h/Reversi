package src.view.GUI;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;


import src.controller.Game;
import src.model.Board;
import src.model.Player;

public class PlayerPanel extends JPanel {
    private Game game;
    private JLabel playerLabel1; // 玩家姓名标签
    private JLabel playerLabel2; // 玩家姓名标签
    public PlayerPanel(Game game){
        this.game = game;
        setLayout(new BorderLayout());
        playerLabel1 = new JLabel("Player 1");
        playerLabel2 = new JLabel("Player 2");
        Font font = new Font("微软雅黑", Font.BOLD, 20);
        playerLabel1.setFont(font);
        playerLabel2.setFont(font);
        add(playerLabel2,BorderLayout.SOUTH);
        add(playerLabel1,BorderLayout.NORTH);
    }
    protected void updatePlayerLabels(){
        Board board = game.getCurrentBoard();
        Player player1 = game.getPlayer(1);
        Player player2 = game.getPlayer(2);
        //HTML表格格式化文本
        String player1Html = String.format("<html><table width='150'>" + // 设置表格宽度，根据需要调整
                                            "<tr>" +
                                            "<td width='100'>玩家[%s]</td>" + // 名字列宽度
                                            "<td width='20' align='center'>%s</td>" + // 符号列宽度和居中对齐
                                            "<td width='30' align='right'>%d</td>" +  // 数量列宽度和右对齐
                                            "</tr>" +
                                            "</table></html>",
                                            player1.getName(),
                                            player1.getName() == game.getCurrentPlayer().getName()? player1.getPiece().getSymbol() :"",
                                            board.numOfChess(player1)
                                            );
        String player2Html = String.format("<html><table width='150'>" + // 设置表格宽度，根据需要调整
                                            "<tr>" +
                                            "<td width='100'>玩家[%s]</td>" + // 名字列宽度
                                            "<td width='20' align='center'>%s</td>" + // 符号列宽度和居中对齐
                                            "<td width='30' align='right'>%d</td>" +  // 数量列宽度和右对齐
                                            "</tr>" +
                                            "</table></html>",
                                            player2.getName(),
                                            player2.getName() == game.getCurrentPlayer().getName()? player2.getPiece().getSymbol() :"",
                                            board.numOfChess(player2)
                                            );                                    
        playerLabel1.setText(player1Html);
        playerLabel2.setText(player2Html);
        revalidate();
        repaint();
    }
}
