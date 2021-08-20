/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.espol.grupo_12;

import ai.MiniMax;
import static ai.MiniMax.P;
import javafx.scene.control.Button;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author josue
 */
public final class Celda extends Button {
    private final int row;
    private final int col;
    private Mark mark;

    public Celda(int initRow, int initCol, Mark initMark) {
        row = initRow;
        col = initCol;
        mark = initMark;
        initialiseTile();
    }

    private void initialiseTile() {
        this.setOnMouseClicked(e -> {
            if (!PGameController.boardPlaying.isCrossTurn()) {
                PGameController.boardPlaying.placeMark(this.row, this.col);
                try {
                    this.update();
                } catch (CloneNotSupportedException ex) {
                    System.out.println(ex);
//                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.setStyle("-fx-font-size:70");
        this.setTextAlignment(TextAlignment.CENTER);
        this.setMinSize(150.0, 150.0);
        this.setText("" + this.mark);
    }
    
    public void update() throws CloneNotSupportedException{
        this.mark = PGameController.boardPlaying.getMarkAt(this.row, this.col);
        this.setText("" + mark);

        // SUGERENCIA DE MOVIMIENTO PARA EL HUMANO 
        // CALCULO DE UTULIDAD PARA CADA JUGADOR
        if(PGameController.boardPlaying.isCrossTurn()){
            System.out.println("Turno de la máquina");
        }else{
            System.out.println("Turno del humano");
            System.out.println("Mejor jugada para el humano");
            MiniMax move = new MiniMax(PGameController.boardPlaying);
            int row = move.getRow()+1;
            int col = move.getCol()+1;
            System.out.println("fila: "+row+"  columna: "+col);
        }
        System.out.println("Ux:"+(P(PGameController.boardPlaying, Mark.X)-P(PGameController.boardPlaying, Mark.O))+"   Uo:"+(P(PGameController.boardPlaying, Mark.O)-P(PGameController.boardPlaying, Mark.X)));
        System.out.println("");
    }
}
