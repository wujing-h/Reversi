package src.model;
import java.util.ArrayList;

public abstract class Board {
    private final int boardSize;
    private final Piece[][] board;
    private Player lastPlayer;        //上一个行动玩家
    private boolean gameOver = false; //游戏是否结束

    //构造函数与初始化
    public Board(int size,Player player){
        this.boardSize = size;
        this.board =new Piece[size][size];
        this.lastPlayer = player;
        initialize();
    }
    //测试用例
    protected void initializeTestExample(){
    }
    //初始化棋盘
    protected abstract void initialize();
    //判断游戏结束
    public abstract boolean isGameOver(Player player1,Player player2);
    //结束游戏时的输出
    public abstract void ifEndGame(Player player1,Player player2);
    //落子后的处理
    public abstract boolean makeMove(int i,int j,String input,Player currentPlayer);

    //打印棋盘
    public void printBoard(Player player1, Player player2, Player currentPlayer, ArrayList<Board> boardList,Board currentBoard){
        System.out.println();
        System.out.printf("%17s\n", "A B C D E F G H");
        for(int i = 0;i < boardSize;i ++){
            System.out.print((i+1)+" ");
            for(int j = 0;j < boardSize;j ++){
                //如果此处可以翻转则给予提示
                if(ifReverse(i,j,currentPlayer)&(currentBoard instanceof Reversi)){
                    System.out.print("\u001B[31m+\u001B[0m"+" ");
                }
                else{
                    System.out.print(board[i][j].getSymbol()+" ");
                }
            }
            //打印玩家UI
            printUI(boardList,player1,player2,currentPlayer,currentBoard,i);
            //打印游戏列表
            printList(boardList,i);
            System.out.println();
        }
    }
    private void printUI(ArrayList<Board> boardList,Player player1, Player player2, Player currentPlayer,Board currentBoard,int i){
        if(i == 2){
            System.out.printf("%-15s\t\t",String.format("Game"+(boardList.indexOf(currentBoard)+1)));
        }
        else if(i == 3){
            System.out.printf("%-5s\t",String.format("玩家["+player1.getName()+"]"+
                                                            (currentPlayer == player1 ? player1.getPiece().getSymbol():""))) ;
            System.out.printf("%-10s\t",String.format(" "+numOfChess(player1)));
        }
        else if(i == 4){
            System.out.printf("%-5s\t",String.format("玩家["+player2.getName()+"]"+
                                                            (currentPlayer == player2 ? player2.getPiece().getSymbol():""))) ;
            System.out.printf("%-10s\t",String.format(" "+numOfChess(player2)));
        }
        else if(i == 5){
            printGameRange(player1,player2);
        }
        else {
            System.out.printf("%-15s\t\t"," ");
        }
    }
    protected void printGameRange(Player player1,Player player2){
        System.out.printf("%-15s\t\t"," ");
    }
    private void printList(ArrayList<Board> boardList,int i){
        int length = boardList.size();
        if(i == 2){
            System.out.print("Game List");
        }
        else if(i > 2){
            for(int index = i-3;index < length;index += 5){
                Board board = boardList.get(index);
                String output = String.format((index+1)+"."+board.getClass().getSimpleName());
                System.out.printf("%-10s",output);
            }
        }
    }
    //计算棋子数
    public int numOfChess(Player currentPlayer){
        int result = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j].getSymbol() == currentPlayer.getPiece().getSymbol()) {
                    result++;
                }
            }
        }
        return result;
    }
    //棋盘是否已满
    protected boolean isBoardFull(){
        for(int i = 0;i < boardSize;i ++){
            for(int j = 0;j < boardSize;j ++){
                if(board[i][j].getSymbol()=='·') {
                    return false;
                }
            }
        }
        return true;
    }
    //落子是否在棋盘内，是否重合
    protected boolean isMoveValid(int i,int j){
        if(i >= boardSize || j >=boardSize ||i < 0 ||j < 0){
            return false;
        }
        return board[i][j].getSymbol() == '·';
    }
    //下棋后是否能够反转
    public boolean ifReverse(int i ,int j,Player currentPlayer){
        if(board[i][j].getSymbol()!='·'){
            return false;
        }
        //八向搜索
        int [] x = {1,1,0,-1,-1,-1,0,1};
        int [] y = {0,1,1,1,0,-1,-1,-1};
        for(int dir = 0; dir < 8;dir ++){
            for(int size = 1; size < 8;size ++){
                int newRow = i + size * x[dir];
                int newCol = j + size * y[dir];
                //检查是否在边界内
                if (newRow < 0 || newRow >= boardSize || newCol < 0 || newCol >= boardSize) {
                    break;
                }
                Piece piece = board[newRow][newCol];
                // 如果遇到空位，停止搜索
                if (piece.getSymbol() == '·') {
                    break;
                }
                //搜索成功返回
                if (piece.getSymbol() == currentPlayer.getPiece().getSymbol()) {
                    if (size > 1) {
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }
    //是否转换玩家
    public boolean ifSwitch(Player currentPlayer){
        for(int i = 0;i < boardSize;i ++){
            for(int j = 0;j < boardSize;j ++){
               if(ifReverse(i,j,currentPlayer)){
                   return false;
               }
            }
        }
        return true;
    }

    //getter
    public int getBoardSize(){
        return boardSize;
    }
    public Player getLastPlayer() {
        return lastPlayer;
    }

    public Piece getBoardPiece(int i,int j) {
        return board[i][j];
    }
    public void updateBoard(int i,int j,char input){
        board[i][j] = new Piece(input);
    }
    public void updateBoard(int i, int j, Player player){
        board[i][j] = player.getPiece();
    }
    public void updateLastPlayer(Player player){
        lastPlayer = player;
    }
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    public boolean getGameOver() {
        return gameOver;
    }
}