package src.model;

public class Gomoku extends Board {
    public Gomoku(int size,Player player){
        super(size,player);
    }
    @Override
    public void initialize(){
        for(int i = 0;i < getBoardSize();i ++){
            for(int j = 0;j < getBoardSize();j ++){
                updateBoard(i,j,'·');
            }
        }
    }

    @Override
    public boolean makeMove(int i,int j,String input,Player currentPlayer){
        if (isMoveValid(i, j)) {
            updateBoard(i, j, currentPlayer);
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public boolean isGameOver(Player player1,Player player2){
        for(int i = 0;i < getBoardSize();i ++){
            for(int j = 0;j < getBoardSize();j ++){
                if(searchLine(i,j)|isBoardFull()){
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public void ifEndGame(Player player1,Player player2){
        if(isGameOver(player1,player2)&(getGameOver()==false)){
            System.out.println("游戏结束！");
            Piece piece = new Piece('·');
            for(int i = 0;i < getBoardSize();i ++){
                for(int j = 0;j < getBoardSize();j ++){
                    if(searchLine(i,j)){
                        piece = getBoardPiece(i,j);
                    }
                }
            }
            if(piece == player1.getPiece()){
                System.out.println("玩家[" + player1.getName() + "]获胜！");
            }
            else{
                System.out.println("玩家[" + player2.getName() + "]获胜！");
            }
        }
    }
    public void printGameRange(Player player1,Player player2){
        int round = Math.min(numOfChess(player1),numOfChess(player2))+1;
        System.out.printf("%-15s\t\t",String.format("Current round: "+round));
    }
    private boolean searchLine(int i,int j){
        boolean result = false;
        Piece piece = getBoardPiece(i,j);
        int[] x = {1, 1, 0, -1, -1, -1, 0, 1};
        int[] y = {0, 1, 1, 1, 0, -1, -1, -1};
        for (int dir = 0; dir < 8; dir++) {
            for (int size = 1; size < 5; size++) {
                int newRow = i + size * x[dir];
                int newCol = j + size * y[dir];
                //检查是否在边界内
                if (newRow < 0 || newRow >= getBoardSize() || newCol < 0 || newCol >= getBoardSize()) {
                    break;
                }
                Piece newPiece = getBoardPiece(newRow,newCol);
                // 起始位置没有棋子或是搜索到的位置棋子与起始位置不同，则跳出循环判断下个方向
                if ((newPiece.getSymbol() != piece.getSymbol())|(piece.getSymbol() == '·')) {
                    break;
                }
                // 成功搜索到五个相同棋子
                if (size == 4) {
                    result = true;
                }
            }
        }
        return result;
    }
}
