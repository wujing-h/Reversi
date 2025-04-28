package src.model;
public class Piece {
    private final char symbol;
    // 可以扩展更多属性，比如颜色、类型等

    public Piece(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

}