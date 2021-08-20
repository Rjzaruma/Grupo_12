package ai;

/**
 *
 * @author Joao
 */
import edu.espol.grupo_12.Board;
import edu.espol.grupo_12.Mark;
import static edu.espol.grupo_12.Mark.BLANK;
import static edu.espol.grupo_12.Mark.O;
import static edu.espol.grupo_12.Mark.X;
import tree.MPTree;

public  class MiniMax {
    //private static final int MAX_DEPTH = 6;
    private int row;
    private int col;
    private Board board;

    public MiniMax(Board board) throws CloneNotSupportedException {
        this.board = miniMax(board);
    }
    
    public Board miniMax(Board board) throws CloneNotSupportedException{
        MPTree<Board> arbol = new MPTree(board);
        //System.out.println(arbol);
        Board jugada = new Board();
        //System.out.println(board);
        // se agregan al arbol las posibles jugadas para X si isCrossTurn=true
        int maximo = Integer.MIN_VALUE;
        for (int rowX = 0; rowX < board.getWidth(); rowX++) {
            for (int colX = 0; colX < board.getWidth(); colX++) {
                if (!board.isTileMarked(rowX, colX)) {
                    //Board hijo = new Board(board.getBoard(), board.isCrossTurn(), board.getMovDisp());//deberia ser un deep clone
                    Board hijo = (Board) board.clone();
                    hijo.setMarkAt(rowX, colX, board.isCrossTurn()? X : O);
                    arbol.addHijo(new MPTree(hijo));
                    // se agregan al arbol las posibles jugadas para O
                    // para cada una de las jugadas de X si isCrossTurn=true
                    int minimo = Integer.MAX_VALUE;
                    for (int rowO = 0; rowO < board.getWidth(); rowO++) {
                        for (int colO = 0; colO < board.getWidth(); colO++) {
                            if (!board.isTileMarked(rowO, colO)) {
                                Board nieto = (Board) hijo.clone();//deberia ser un deep clone
                                nieto.setMarkAt(rowO, colO, board.isCrossTurn()? O : X);
                                arbol.getLastHijo().addHijo(new MPTree(hijo));
                                int utilidad = P(nieto, board.isCrossTurn()?X:O) - P(nieto, board.isCrossTurn()?O:X);
                                minimo = Math.min(minimo, utilidad);
                                //nieto.setUtility(Math.min(nieto.getUtility(), utilidad));
                                nieto.eraseMark(rowO, colO);
//                                System.out.println(board);
//                                System.out.println(utilidad);
//                                System.out.println("--------------------------------------");
                            }
                        }
                    }
                    hijo.setUtility(minimo);
                    jugada = hijo.getUtility()>maximo ? hijo : jugada;
                    row = hijo.getUtility()>maximo ? rowX: row;
                    col = hijo.getUtility()>maximo ? colX: col;
                    maximo = Math.max(maximo, hijo.getUtility());
                    hijo.eraseMark(rowX, colX);
                    //hijo.clear();
                    // P(this, X) - P(this, O)
                }
                
            }
        }
        return jugada;
    }

    public static int P(Board board, Mark marca){
        int valorP = 0;
        int rowSum = 0;
        int Bwin = BLANK.getMark() * board.getWidth();
        // Check available rows.
        for (int row = 0; row < board.getWidth(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                if(board.getMarkAt(row, col).getMark() == marca.getMark()){
                    rowSum += BLANK.getMark();
                }else{
                    rowSum += board.getMarkAt(row, col).getMark();
                }
            }
            if (rowSum == Bwin) {
                valorP++;
            }
            rowSum = 0;
        }
        // Check available columns.
        rowSum = 0;
        for (int col = 0; col < board.getWidth(); col++) {
            for (int row = 0; row < board.getWidth(); row++) {
                if(board.getMarkAt(row, col).getMark() == marca.getMark()){
                    rowSum += BLANK.getMark();
                }else{
                    rowSum += board.getMarkAt(row, col).getMark();
                }
            }
            if (rowSum == Bwin) {
                valorP++;
            }
            rowSum = 0;
        }
        // Check available diagonals.
        // Top-left to bottom-right diagonal.
        rowSum = 0;
        for (int i = 0; i < board.getWidth(); i++) {
            if(board.getMarkAt(i, i).getMark() == marca.getMark()){
                rowSum += BLANK.getMark();
            }else{
                rowSum += board.getMarkAt(i, i).getMark();
            }
        }
        if (rowSum == Bwin){
            valorP++;
        }
        // Top-right to bottom-left diagonal.
        rowSum = 0;
        int indexMax = board.getWidth() - 1;
        for (int i = 0; i <= indexMax; i++) {
            if(board.getMarkAt(i, indexMax - i).getMark() == marca.getMark()){
                rowSum += BLANK.getMark();
            }else{
                rowSum += board.getMarkAt(i, indexMax - i).getMark();
            }
        }
        if (rowSum == Bwin){
            valorP++;
        }

        return valorP;
    }
    
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
    
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }    
    
}