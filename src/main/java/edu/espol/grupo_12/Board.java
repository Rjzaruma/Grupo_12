/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.espol.grupo_12;
import static edu.espol.grupo_12.Mark.BLANK;
import static edu.espol.grupo_12.Mark.O;
import static edu.espol.grupo_12.Mark.X;

/**
 *
 * @author Joao
 */
public class Board implements Cloneable{
    private boolean crossTurn, gameOver;
    private Mark[][] board;
    private Mark marcaGanadora;
    //Tamaño del juego, en este caso 3x3
    private final int LADO_TABLERO = 3;
    private int movimientosDisponibles = LADO_TABLERO * LADO_TABLERO;
    private int utility;
    
    public Board() {
        board = new Mark[LADO_TABLERO][LADO_TABLERO];
        crossTurn = true;   //true = comienza maquina; false = comienza persona
        gameOver = false;
        marcaGanadora = BLANK;
        utility = 0;
        initialiseBoard();
    }
    
    public Board(Board board){
        this(board.getBoard(), board.isCrossTurn(), board.getMovDisp());
    }
    
    public Board(Mark[][] board, boolean crossTurn, int movimientosDisponibles){
        this.board = board;
        this.crossTurn = crossTurn;
        this.movimientosDisponibles = movimientosDisponibles;
        gameOver = false;
        marcaGanadora = BLANK;
        utility = 0;
    }
    
    private void initialiseBoard() {
        for (int row = 0; row < LADO_TABLERO; row++) {
            for (int col = 0; col < LADO_TABLERO; col++) {
                board[row][col] = BLANK;
            }
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }    

    /**
     * Attempt to mark tile at the given coordinates if they are valid and it is
     * possible to do so, toggle the player and check if the placing the mark
     * has resulted in a win.
     *
     * @param row Row coordinate to attempt to mark
     * @param col Column coordinate to attempt to mark
     * @return true if mark was placed successfully
     */
    public boolean placeMark(int row, int col) {
        if (row < 0 || row >= LADO_TABLERO || col < 0 || col >= LADO_TABLERO
                || isTileMarked(row, col) || gameOver) {
            return false;
        }
        movimientosDisponibles--;
        board[row][col] = crossTurn ? X : O;
        togglePlayer();
        checkWin(row, col);
        return true;
    }
    
    public boolean eraseMark(int row, int col) {
        if (row < 0 || row >= LADO_TABLERO || col < 0 || col >= LADO_TABLERO
                || !isTileMarked(row, col) || gameOver) {
            return false;
        }
        movimientosDisponibles++;
        board[row][col] = BLANK;
        return true;
    }

    /**
     * Check row and column provided and diagonals for win.
     *
     * @param row Row to check
     * @param col Column to check
     */
    private void checkWin(int row, int col) {
        int rowSum = 0;
        // Revisa las filas por el ganador
        for (int c = 0; c < LADO_TABLERO; c++) {
            rowSum += getMarkAt(row, c).getMark();
        }
        if (calcWinner(rowSum) != BLANK) {
            System.out.println(marcaGanadora + " gana en la fila " + row);
            return;
        }

        // Revisa las columnas por el ganador
        rowSum = 0;
        for (int r = 0; r < LADO_TABLERO; r++) {
            rowSum += getMarkAt(r, col).getMark();
        }
        if (calcWinner(rowSum) != BLANK) {
            System.out.println(marcaGanadora + " gana en la columna" + col);
            return;
        }

        // Tope izquierdo y derecho en la diagonal
        rowSum = 0;
        for (int i = 0; i < LADO_TABLERO; i++) {
            rowSum += getMarkAt(i, i).getMark();
        }
        if (calcWinner(rowSum) != BLANK) {
            System.out.println(marcaGanadora + " gana arriba a la izquierda "
                    + "derecha-abajo diagonal");
            return;
        }

        //tope derecho a fondo izquierdo por la diagonal 
        rowSum = 0;
        int indexMax = LADO_TABLERO - 1;
        for (int i = 0; i <= indexMax; i++) {
            rowSum += getMarkAt(i, indexMax - i).getMark();
        }
        if (calcWinner(rowSum) != BLANK) {
            System.out.println(marcaGanadora + " gana arriba a la derecha "
                    + "izquierda-abajo diagonal");
            return;
        }

        if (!anyMovesAvailable()) {
            gameOver = true;
            System.out.println("Empate!");
        }
    }

    /**
     * Calculates if provided ASCII sum equates to a win for X or O.
     *
     * @param rowSum Sum of characters' ASCII values in a row
     * @return Mark indicating which player won or a space character if neither
     */
    private Mark calcWinner(int rowSum) {
        int Xwin = X.getMark() * LADO_TABLERO;
        int Owin = O.getMark() * LADO_TABLERO;
        if (rowSum == Xwin) {
            gameOver = true;
            marcaGanadora = X;
            return X;
        } else if (rowSum == Owin) {
            gameOver = true;
            marcaGanadora = O;
            return O;
        }
        return BLANK;
    }

    private void togglePlayer() {
        crossTurn = !crossTurn;
    }

    public boolean anyMovesAvailable() {
        return movimientosDisponibles > 0;
    }

    public Mark getMarkAt(int row, int column) {
        return board[row][column];
    }

    public boolean isTileMarked(int row, int column) {
        return board[row][column].estaMarcado();
    }

    public void setMarkAt(int row, int column, Mark newMark) {
        board[row][column] = newMark;
    }

    @Override
    public String toString() {
        StringBuilder strBldr = new StringBuilder();
        for (Mark[] row : board) {
            for (Mark tile : row) {
                strBldr.append(tile).append(' ');
            }
            strBldr.append("\n");
        }
        return strBldr.toString();
    }

    public boolean isCrossTurn() {
        return crossTurn;
    }

    public int getWidth() {
        return LADO_TABLERO;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Mark getWinningMark() {
        return marcaGanadora;
    }

    public int getUtility() {
        return utility;
    }

    public void setUtility(int u) {
        utility = u;
    }
    
    public Mark[][] getBoard(){
        return board;
    }
    
    public int getMovDisp(){
        return movimientosDisponibles;
    }
}
