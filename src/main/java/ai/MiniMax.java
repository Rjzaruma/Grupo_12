package ai;

import edu.espol.grupo_12.Board;
import edu.espol.grupo_12.Mark;
import static edu.espol.grupo_12.Mark.BLANK;
import tree.MPTree;

public  class MiniMax {
    private int row;
    private int col;
    private Board board;

    public MiniMax(Board board){
        this.board = miniMax(board);
    }
    
    public Board miniMax(Board board){
        MPTree<Board> arbol = new MPTree(board);
        Board jugada = new Board();
        // se agregan al arbol las posibles jugadas para X si isCrossTurn=true
        int maximo = Integer.MIN_VALUE;//maxima utilidad de los hijos
        for (int rowX = 0; rowX < board.getWidth(); rowX++) {
            for (int colX = 0; colX < board.getWidth(); colX++) {
                if (!board.isTileMarked(rowX, colX)) {
                    Board hijo = new Board();
                    hijo.copyBoard(board);
                    // la marca del jugador que quiere predecir el turno
                    Mark mX = board.isAiTurn() ? board.getMarcaAi() : board.getMarcaPlayer();
                    hijo.placeMark(rowX, colX);
                    arbol.addHijo(new MPTree(hijo));
                    // se agregan al arbol las posibles jugadas para O
                    // para cada una de las jugadas de X si isCrossTurn=true
                    int minimo = Integer.MAX_VALUE;//minima utilidad de los nietos
                    for (int rowO = 0; rowO < board.getWidth(); rowO++) {
                        for (int colO = 0; colO < board.getWidth(); colO++) {
                            if (!hijo.isTileMarked(rowO, colO)) {
                                Board nieto = new Board();
                                nieto.copyBoard(hijo);
                                // la marca del jugador contrario al que quiere predecir el turno
                                Mark mO = board.isAiTurn() ? board.getMarcaPlayer() : board.getMarcaAi();
                                nieto.placeMark(rowO, colO);
                                arbol.getLastHijo().addHijo(new MPTree(nieto));
                                int utilidad = P(nieto, mX) - P(nieto, mO);
                                if(nieto.getWinningMark()==mO && hijo.getWinningMark()!=mX) utilidad=Integer.MIN_VALUE;
                                minimo = Math.min(minimo, utilidad);//minima utilidad de los nietos
                            }
                        }
                    }
                    hijo.setUtility(minimo);
                    jugada = hijo.getUtility()>maximo ? hijo : jugada;
                    row = hijo.getUtility()>maximo ? rowX: row;
                    col = hijo.getUtility()>maximo ? colX: col;
                    maximo = Math.max(maximo, hijo.getUtility());
                }
            }
        }
        return jugada;
    }

    public static int P(Board board, Mark marca){
        int valorP = 0;
        int rowSum = 0;
        int Bwin = BLANK.getMark() * board.getWidth();
        // Revisa las filas disponibles
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
        // Revisa las columnas disponibles
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
        // Revisa las diagonales disponibles
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