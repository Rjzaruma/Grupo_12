package edu.espol.grupo_12;

import ai.MiniMax;
import static ai.MiniMax.P;
import javafx.scene.control.Button;
import javafx.scene.text.TextAlignment;

public final class Celda extends Button {
    private int row;
    private int col;
    private Mark mark;

    public Celda(int initRow, int initCol, Mark initMark) {
        row = initRow;
        col = initCol;
        mark = initMark;
        initialiseTile();
    }

    private void initialiseTile() {
        this.setOnMouseClicked(e -> {
            if (!PGameController.boardPlaying.isAiTurn()) {
                PGameController.boardPlaying.placeMark(this.row, this.col);
                this.update();
            }
        });
        this.setStyle("-fx-font-size:70");
        this.setTextAlignment(TextAlignment.CENTER);
        this.setMinSize(150.0, 150.0);
        this.setText("" + this.mark);
    }
    
    public void update(){
        this.mark = PGameController.boardPlaying.getMarkAt(this.row, this.col);
        this.setText("" + mark);   

        // SUGERENCIA DE MOVIMIENTO PARA EL HUMANO 
        // CALCULO DE UTULIDAD PARA CADA JUGADOR
        if(PGameController.boardPlaying.isAiTurn()){
            System.out.println("Turno de la máquina");
        }else{
            System.out.println("Turno del humano");
            System.out.println("Mejor jugada para el humano");
            MiniMax move = new MiniMax(PGameController.boardPlaying);
            int r = move.getRow()+1;
            int c = move.getCol()+1;
            System.out.print(move.getBoard());
            System.out.println("fila: "+r+"  columna: "+c);
        }
        System.out.println("Ux:"+(P(PGameController.boardPlaying, Mark.X)-P(PGameController.boardPlaying, Mark.O))+"   Uo:"+(P(PGameController.boardPlaying, Mark.O)-P(PGameController.boardPlaying, Mark.X)));
        System.out.println("");
    }
}
