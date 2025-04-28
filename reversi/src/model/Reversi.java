package src.model;

public class Reversi extends Board{
    public Reversi (int size,Player player){
        super(size,player);
    }
    @Override
    public void initialize(){
        for(int i = 0;i < getBoardSize();i ++){
            for(int j = 0;j < getBoardSize();j ++){
                updateBoard(i,j,'·');
            }
        }
        updateBoard(3,3,'●');
        updateBoard(3,4,'○');
        updateBoard(4,4,'●');
        updateBoard(4,3,'○');
    }

    @Override
    public boolean makeMove(int i,int j,String input,Player currentPlayer){
        if (isMoveValid(i, j) && ifReverse(i, j, currentPlayer)) {
            updateBoard(i, j, currentPlayer);
            reverse(i, j, currentPlayer);
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public boolean isGameOver(Player player1,Player player2){
        return isBoardFull()||(ifSwitch(player1)& ifSwitch(player2));
    }
    @Override
    public void ifEndGame(Player player1,Player player2){
        if(isGameOver(player1,player2)&(getGameOver()==false)){
            System.out.println("游戏结束！");
            int numPlayer1 = numOfChess(player1);
            int numPlayer2 = numOfChess(player2);
            System.out.println("玩家[" + player1.getName() + "]"+numPlayer1);
            System.out.println("玩家[" + player2.getName() + "]"+numPlayer2);
            if (numPlayer1 > numPlayer2) {
                System.out.println("玩家[" + player1.getName() + "]获胜！");
            }
            else if (numPlayer1 < numPlayer2) {
                System.out.println("玩家[" + player2.getName() + "]获胜！");
            }
            else {
                System.out.println("两人平局！");
            }
        }
    }
    private void reverse(int i, int j, Player currentPlayer) {
        //八向，从x轴正方向开始逆时针依次记为0到7
        int[] x = {1, 1, 0, -1, -1, -1, 0, 1};
        int[] y = {0, 1, 1, 1, 0, -1, -1, -1};
        for (int dir = 0; dir < 8; dir++) {
            for (int size = 1; size < 8; size++) {
                int newRow = i + size * x[dir];
                int newCol = j + size * y[dir];
                //检查是否在边界内
                if (newRow < 0 || newRow >= getBoardSize() || newCol < 0 || newCol >= getBoardSize()) {
                    break;
                }
                Piece piece = getBoardPiece(newRow,newCol);
                // 如果遇到空位，停止搜索
                if (piece.getSymbol() == '·') {
                    break;
                }
                // 如果遇到当前玩家的棋子，翻转中间的棋子
                if (piece.getSymbol() == currentPlayer.getPiece().getSymbol()) {
                    if (size > 1) {
                        for (int num = 1; num < size; num++) {
                            updateBoard(i + num * x[dir], j + num * y[dir], currentPlayer);
                        }
                    }
                    break;
                }
            }
        }
    }
}
