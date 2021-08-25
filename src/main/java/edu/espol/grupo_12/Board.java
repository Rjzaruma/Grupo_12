package edu.espol.grupo_12;
import static edu.espol.grupo_12.Mark.BLANK;
import static edu.espol.grupo_12.Mark.O;
import static edu.espol.grupo_12.Mark.X;

public class Board{
    private boolean aiTurn, gameOver;
    private final Mark[][] board;
    private Mark marcaGanadora;
    private Mark marcaAi;
    private Mark marcaPlayer;
    //Tamaño del juego, en este caso 3x3
    private final int LADO_TABLERO = 3;
    private int movimientosDisponibles = LADO_TABLERO * LADO_TABLERO;
    private int utility;
    
    public Board() {
        board = new Mark[LADO_TABLERO][LADO_TABLERO];
        aiTurn = true; //true = comienza maquina; false = comienza persona
        marcaAi = X;
        marcaPlayer = O;
        gameOver = false;
        marcaGanadora = BLANK;
        utility = 0;
        initialiseBoard();
    }
    
    private void initialiseBoard() {
        for (int row = 0; row < LADO_TABLERO; row++) {
            for (int col = 0; col < LADO_TABLERO; col++) {
                board[row][col] = BLANK;
            }
        }
    }  

    public boolean placeMark(int row, int col) {
        if (row < 0 || row >= LADO_TABLERO || col < 0 || col >= LADO_TABLERO
                || isTileMarked(row, col) || gameOver) {
            return false;
        }
        movimientosDisponibles--;
        if(aiTurn)
            board[row][col] = marcaAi;
        else
            board[row][col] = marcaPlayer;
        checkWin(row, col);
        togglePlayer();
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

    private void checkWin(int row, int col) {
        int rowSum = 0;
        // Revisa las filas por el ganador
        for (int c = 0; c < LADO_TABLERO; c++) {
            rowSum += getMarkAt(row, c).getMark();
        }
        if (calcWinner(rowSum) != BLANK) {
            return ;
        }

        // Revisa las columnas por el ganador
        rowSum = 0;
        for (int r = 0; r < LADO_TABLERO; r++) {
            rowSum += getMarkAt(r, col).getMark();
        }
        if (calcWinner(rowSum) != BLANK) {
            return ;
        }

        // Tope izquierdo y derecho en la diagonal
        rowSum = 0;
        for (int i = 0; i < LADO_TABLERO; i++) {
            rowSum += getMarkAt(i, i).getMark();
        }
        if (calcWinner(rowSum) != BLANK) {
            return ;
        }

        //tope derecho a fondo izquierdo por la diagonal 
        rowSum = 0;
        int indexMax = LADO_TABLERO - 1;
        for (int i = 0; i <= indexMax; i++) {
            rowSum += getMarkAt(i, indexMax - i).getMark();
        }
        if (calcWinner(rowSum) != BLANK) {
            return ;
        }

        if (!anyMovesAvailable()) {
            gameOver = true;
        }
    }

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
    
    public void togglePlayer() {
        aiTurn = !aiTurn;
    }

    private boolean anyMovesAvailable() {
        return movimientosDisponibles > 0;
    }

    public Mark getMarkAt(int row, int column) {
        return board[row][column];
    }

    public boolean isTileMarked(int row, int column) {
        return board[row][column].estaMarcado();
    }

    @Override
    public String toString() {
        StringBuilder strBldr = new StringBuilder();
        for (Mark[] row : board) {
            strBldr.append('|');
            for (Mark tile : row) {
                if(tile.estaMarcado())
                    strBldr.append(' ').append(tile).append(' ').append('|');
                else{
                    strBldr.append(' ').append(' ').append(' ').append(' ').append(' ').append('|');
                }
            }
            strBldr.append("\n");
        }
        return strBldr.toString();
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
    
    public void copyBoard(Board b){
        for (int row = 0; row < LADO_TABLERO; row++) {
            for (int col = 0; col < LADO_TABLERO; col++) {
                board[row][col] = b.board[row][col];
            }
        }
        marcaAi = b.marcaAi;
        marcaPlayer = b.marcaPlayer;
        aiTurn = b.aiTurn;
        movimientosDisponibles =  b.movimientosDisponibles;
    } 

    public boolean isAiTurn() {
        return aiTurn;
    }

    public void setAiTurn(boolean aiTurn) {
        this.aiTurn = aiTurn;
    }    

    public Mark getMarcaAi() {
        return marcaAi;
    }

    public void setMarcaAi(Mark marcaAi) {
        this.marcaAi = marcaAi;
    }

    public Mark getMarcaPlayer() {
        return marcaPlayer;
    }

    public void setMarcaPlayer(Mark marcaPlayer) {
        this.marcaPlayer = marcaPlayer;
    }
    
}
