package src.view.GUI;

import src.controller.Game;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private Game game;
    private BoardPanel boardPanel; // 棋盘面板
    private PlayerPanel playerPanel; // 玩家信息面板
    private JPanel infoPanel; // 信息面板
    private JPanel controlPanel; // 控制面板
    private JComboBox<String> gameList; // 游戏类型下拉框
    private JComboBox<String> createNewGame; // 新建游戏下拉框
    public GameFrame(Game game) {
        this.game = game;
        //初始化窗口
        setTitle("chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        //初始化棋盘
        boardPanel = new BoardPanel(game);
        add(boardPanel, BorderLayout.CENTER);
        //初始化玩家面板
        playerPanel = new PlayerPanel(game);
        playerPanel.updatePlayerLabels();
        //初始化信息面板
        infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout());
        infoPanel.add(playerPanel);
        add(infoPanel, BorderLayout.EAST);

        //初始化控制面板
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        // 为按钮或棋盘格子添加事件监听器

        setSize(800,650);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    protected void onBoardClick(int row, int col) {
        String input = (row + 1) + "" + (char)('A' + col); // 构造如"1A"的输入
        game.inputMatch(input);
        // 刷新界面显示
        playerPanel.updatePlayerLabels();
    }

    private void onNewGame(String type) {
        game.inputMatch(type); // 直接复用Game逻辑
        // 刷新界面显示
    }
    // 其他方法...
}