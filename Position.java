public class Position {
    private int line; // Número da linha da palavra
    private int column; // A coluna é o número da palavra, isto é, se ela é a terceira palavra que aparece na linha, column = 3

    public Position() {
        this.line = 0;
        this.column = 0;
    }

    public Position(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.column;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
