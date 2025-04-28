package src.model;

public class Peace extends Board{
    public Peace (int size,Player player){
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
        return isBoardFull();
    }
    @Override
    public void ifEndGame(Player player1,Player player2){
        if(isGameOver(player1,player2)&(getGameOver()==false)){
            System.out.println("游戏结束！");
        }
    }
}
