package src.view.GUI;

import src.controller.Game;
import src.model.Board;
import src.model.Reversi;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class BoardPanel extends JPanel {
    private final int boardSize = 8;
    private final int cellSize = 60;
    private Game game;
    public BoardPanel(Game game) {
        this.game = game;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = e.getY() / cellSize;
                int col = e.getX() / cellSize;
                Container parent = getParent();
                //调用GameFrame的onBoardClick方法
                while(parent != null && !(parent instanceof GameFrame)){
                    parent = parent.getParent();
                }
                if(parent instanceof src.view.GUI.GameFrame){
                    ((src.view.GUI.GameFrame)parent).onBoardClick(row,col);
                }
                repaint();
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
            //绘制棋盘格子（填充+边框
               g.setColor(Color.LIGHT_GRAY);
               g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
               g.setColor(Color.BLACK);
               g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize); 
               if(game != null && game.getCurrentBoard() != null){
                    Board board = game.getCurrentBoard();
                    char piece = board.getBoardPiece(row, col).getSymbol();
                    //绘制棋子
                    if(piece == '●'){
                        g.setColor(Color.BLACK);
                        g.fillOval(col * cellSize + 8, row * cellSize + 8, cellSize - 16, cellSize - 16);
                    }
                    else if(piece == '○'){
                        g.setColor(Color.WHITE);
                        g.fillOval(col * cellSize + 8, row * cellSize + 8, cellSize - 16, cellSize - 16); 
                        g.setColor(Color.BLACK);
                        g.drawOval(col * cellSize + 8, row * cellSize + 8, cellSize - 16, cellSize - 16);
                    }
                    //翻转提示
                    if((piece == '·')&&(board.ifReverse(row,col,game.getCurrentPlayer()))&&(board instanceof Reversi)){
                        g.setColor(Color.RED);
                        g.fillOval(col * cellSize + 8, row * cellSize + 8, cellSize - 16, cellSize - 16);
                    }
               }
            } 
        }
    }
}
