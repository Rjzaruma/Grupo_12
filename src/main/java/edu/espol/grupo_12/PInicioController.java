/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.espol.grupo_12;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author josue
 */
public class PInicioController implements Initializable {

    public static boolean firstPc;
    public static boolean xPlayer;
    @FXML
    private Label lblInfo;
    @FXML
    private ToggleGroup movimiento;
    @FXML
    private RadioButton playPlayer;
    @FXML
    private RadioButton playPc;
    @FXML
    private ComboBox<String> comboBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblInfo.setText("Bienvenido "+App.PlayerPlaying.getUsuario());
        lblInfo.setFont(new Font("Times New Roman Bold", 40));
        ObservableList<String> Marcas  = FXCollections.observableArrayList("X","O");
        comboBox.setItems(Marcas);
    }    
    
    @FXML
    public void sceneGame() throws IOException{
        if(movimiento.getSelectedToggle().equals(playPc)){
            firstPc = true;
        }else if(movimiento.getSelectedToggle().equals(playPlayer)){
            firstPc = false;
        }
        if(comboBox.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Informacion Invalida");
            alert.setContentText("Datos insuficientes: Escoga una marca!");
            alert.showAndWait();
        }else{
            if(comboBox.getValue().equals("X")){
                xPlayer = true;
            }else if(comboBox.getValue().equals("O")){
                xPlayer = false;
            }
            App.setRoot("PGame");
        }
    }
    
}
