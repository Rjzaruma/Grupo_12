package ai;

/**
 *
 * @author Joao
 */
import proyecto_2d_parcial.Board;
import proyecto_2d_parcial.Mark;
import static proyecto_2d_parcial.Mark.BLANK;
import static proyecto_2d_parcial.Mark.O;
import static proyecto_2d_parcial.Mark.X;
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
    
//    
//    /**
//     * Play moves on the board alternating between playing as X and O analysing 
//     * the board each time to return the value of the highest value move for the
//     * X player. Return the highest value move when a terminal node or the 
//     * maximum search depth is reached.
//     * @param board Board to play on and evaluate
//     * @param depth The maximum depth of the game tree to search to
//     * @param isMax Maximising or minimising player 
//     * @return Value of the board 
//     */
//    public static int miniMax(Board board, int depth, boolean isMax) {
//        int boardVal = evaluateBoard(board, depth);
//
//        // Terminal node (win/lose/draw) or max depth reached.
//        if (Math.abs(boardVal) > 0 || depth == 0 
//                || !board.anyMovesAvailable()) {
//            return boardVal;
//        }
//
//        // Maximising player, find the maximum attainable value.
//        if (isMax) {
//            int highestVal = Integer.MIN_VALUE;
//            for (int row = 0; row < board.getWidth(); row++) {
//                for (int col = 0; col < board.getWidth(); col++) {
//                    if (!board.isTileMarked(row, col)) {
//                        board.setMarkAt(row, col, X);
//                        highestVal = Math.max(highestVal, miniMax(board,
//                                depth - 1, false));
//                        board.setMarkAt(row, col, BLANK);
//                    }
//                }
//            }
//            return highestVal;
//            // Minimising player, find the minimum attainable value;
//        } else {
//            int lowestVal = Integer.MAX_VALUE;
//            for (int row = 0; row < board.getWidth(); row++) {
//                for (int col = 0; col < board.getWidth(); col++) {
//                    if (!board.isTileMarked(row, col)) {
//                        board.setMarkAt(row, col, O);
//                        lowestVal = Math.min(lowestVal, miniMax(board,
//                                depth - 1, true));
//                        board.setMarkAt(row, col, BLANK);
//                    }
//                }
//            }
//            return lowestVal;
//        }
//    }
//
//    /**
//     * Evaluate every legal move on the board and return the best one.
//     * @param board Board to evaluate
//     * @return Coordinates of best move
//     */
//    public static int[] getBestMove(Board board) {
//        int[] bestMove = new int[]{-1, -1};
//        int bestValue = Integer.MIN_VALUE;
//
//        for (int row = 0; row < board.getWidth(); row++) {
//            for (int col = 0; col < board.getWidth(); col++) {
//                if (!board.isTileMarked(row, col)) {
//                    board.setMarkAt(row, col, X);
//                    int moveValue = miniMax(board, MAX_DEPTH, false);
//                    board.setMarkAt(row, col, BLANK);
//                    if (moveValue > bestValue) {
//                        bestMove[0] = row;
//                        bestMove[1] = col;
//                        bestValue = moveValue;
//                    }
//                }
//            }
//        }
//        return bestMove;
//    }
//
//    /**
//     * Evaluate the given board from the perspective of the X player, return 
//     * 10 if a winning board configuration is found, -10 for a losing one and 0 
//     * for a draw, weight the value of a win/loss/draw according to how many 
//     * moves it would take to realise it using the depth of the game tree the
//     * board configuration is at.
//     * @param board Board to evaluate
//     * @param depth depth of the game tree the board configuration is at
//     * @return value of the board
//     */
//    private static int evaluateBoard(Board board, int depth) {
//        int rowSum = 0;
//        int bWidth = board.getWidth();
//        int Xwin = X.getMark() * bWidth;
//        int Owin = O.getMark() * bWidth;
//
//        // Check rows for winner.
//        for (int row = 0; row < bWidth; row++) {
//            for (int col = 0; col < bWidth; col++) {
//                rowSum += board.getMarkAt(row, col).getMark();
//            }
//            if (rowSum == Xwin) {
//                return 10 + depth;
//            } else if (rowSum == Owin) {
//                return -10 - depth;
//            }
//            rowSum = 0;
//        }
//
//        // Check columns for winner.
//        rowSum = 0;
//        for (int col = 0; col < bWidth; col++) {
//            for (int row = 0; row < bWidth; row++) {
//                rowSum += board.getMarkAt(row, col).getMark();
//            }
//            if (rowSum == Xwin) {
//                return 10 + depth;
//            } else if (rowSum == Owin) {
//                return -10 - depth;
//            }
//            rowSum = 0;
//        }
//
//        // Check diagonals for winner.
//        // Top-left to bottom-right diagonal.
//        rowSum = 0;
//        for (int i = 0; i < bWidth; i++) {
//            rowSum += board.getMarkAt(i, i).getMark();
//        }
//        if (rowSum == Xwin) {
//            return 10 + depth;
//        } else if (rowSum == Owin) {
//            return -10 - depth;
//        }
//
//        // Top-right to bottom-left diagonal.
//        rowSum = 0;
//        int indexMax = bWidth - 1;
//        for (int i = 0; i <= indexMax; i++) {
//            rowSum += board.getMarkAt(i, indexMax - i).getMark();
//        }
//        if (rowSum == Xwin) {
//            return 10 + depth;
//        } else if (rowSum == Owin) {
//            return -10 - depth;
//        }
//
//        return 0;
//    }
    
}