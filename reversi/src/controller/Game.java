package src.controller;

import src.model.Board;
import src.model.Gomoku;
import src.model.Peace;
import src.model.Player;
import src.model.Reversi;
import src.view.IO.GameIO;

import java.util.regex.Pattern;
import java.io.IOException;
import java.util.ArrayList;
public class Game {
    private final ArrayList<Board> board = new ArrayList<>();
    private Board currentBoard;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private final GameIO gameIO;

    public Game(GameIO gameIO) {
        player1 = new Player('●',gameIO,"1");
        player2 = new Player('○',gameIO,"2");
        this.currentPlayer = player1;
        this.gameIO = gameIO;
        this.board.add(new Peace(8,currentPlayer));
        this.board.add(new Reversi(8,currentPlayer));
        this.board.add(new Gomoku(8,currentPlayer));
        this.currentBoard = board.getFirst();
    }
    //获取输入
    private String input(){
        return gameIO.readLine("请玩家[" + currentPlayer.getName() + "]输入落子位置(1A)/游戏编号/新游戏类型(reversi/peace/gomoku)/放弃行棋(pass)/退出游戏(quit)\n>>>");
    }
    //利用正则表达式匹配输入
    public void inputMatch(String input) {
       input = input.trim();//消除空格
       //落子
       if(Pattern.matches("^\\d[a-zA-Z]$",input)){
           if (currentBoard.isGameOver(player1,player2)) {
               gameIO.println("游戏已结束，请开始新游戏");
               return;
           }
            moveChess(input);
            return;
       }
       //切换棋盘
       else if(Pattern.matches("^\\d+$", input)){
            switchBoard(input);
            return;
       }
       //增加新游戏
       else if(input.equalsIgnoreCase("reversi")
                                    ||input.equalsIgnoreCase("peace")
                                    ||input.equalsIgnoreCase("gomoku")
                                    ){
           createNewGame(input);
       }
       //放弃行棋   
       else if(input.equalsIgnoreCase("pass")){
           if(currentBoard.ifSwitch(currentPlayer)&(currentBoard instanceof Reversi)){
               switchCurrentPlayer();
           }
           else{
               gameIO.println("不允许放弃本轮行棋");
           }
       }
       //退出游戏
       else if(input.equalsIgnoreCase("quit")){
           System.exit(0);
       }
       else{
          gameIO.println("输入格式有误，请重新输入");
        }
    }
    //选择落子后的处理
    private void moveChess(String input){
        if (currentBoard.ifSwitch(currentPlayer)&(currentBoard instanceof Reversi)) {
            gameIO.println("没有可下位置,请输入pass转换玩家");
            return;
        }
        char colChar = Character.toUpperCase(input.charAt(1)); // 统一转为大写
        int i = input.charAt(0) - '1';
        int j = colChar - 'A';
        boolean moveSuccess = currentBoard.makeMove(i, j, input, currentPlayer);
        if(moveSuccess){
            switchCurrentPlayer();
        }
        else{
            gameIO.println("["+input+"]是非法位置!");
        }
    }

    //转换玩家
    private void switchCurrentPlayer() {
        currentPlayer = currentPlayer == player1 ? player2 : player1;
    }
    //转换棋盘
    private void switchBoard(String input){
        int num;
        try {
            num = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            gameIO.println("输入的棋盘编号无效");
            return;
        }
        //处理输入棋盘编号
        if(num < 0 || num >= board.size()){
            gameIO.println("棋盘还未创建");
            return;
        }
        currentBoard.updateLastPlayer(currentPlayer);
        currentBoard = board.get(num);
        currentPlayer = currentBoard.getLastPlayer();
    }
    //增加新棋盘
    private void createNewGame(String input){
        int size = 8;
        switch (input) {
            case "reversi":
                board.add(new Reversi(size,player1));
                break;
            case "gomoku":
                board.add(new Gomoku(size,player1));
                break;
            case "peace":
                board.add(new Peace(size,player1));
                break;
            default:
        }
    }
    //暂停
    private void pause(){
        gameIO.readLine("请输入任意键继续");
    }
    //清屏
    private void clear(){
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
    //getter
    public Board getCurrentBoard(){
        return currentBoard;
    }
    public Player getCurrentPlayer(){
        return currentPlayer;
    }
    public Player getPlayer(int index){
        return index == 1 ? player1 : player2;  
    }
    //游戏主体逻辑
    public void start() {
         while (true) {
            clear();
            //打印棋盘
            currentBoard.printBoard(player1, player2, currentPlayer,board,currentBoard);
            //结束处理
            currentBoard.ifEndGame(player1,player2);
            if(currentBoard.isGameOver(player1,player2)){
                currentBoard.setGameOver(true);
            }
            //输入处理
            String input = input();
            inputMatch(input);
            pause();    
        }
    }
}
