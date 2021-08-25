package edu.espol.grupo_12;

import static edu.espol.grupo_12.Mark.O;
import static edu.espol.grupo_12.Mark.X;
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

public class PInicioController implements Initializable {
    public static int rondasGanadasPC;
    public static int rondasGanadasPlayer;
    public static int rondasParaGanar;
    public static boolean firstPc;
    public static Mark Player;
    public static Mark Ai;
    
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
    @FXML
    private ComboBox<Integer> comboBoxRondas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblInfo.setText("Bienvenido "+App.PlayerPlaying.getUsuario());
        lblInfo.setFont(new Font("Times New Roman Bold", 40));
        ObservableList<String> Marcas  = FXCollections.observableArrayList("X","O");
        comboBox.setItems(Marcas);
        ObservableList<Integer> rondas  = FXCollections.observableArrayList(1,3,5);
        comboBoxRondas.setItems(rondas);
    }    
    
    @FXML
    public void sceneGame() throws IOException{
        if(movimiento.getSelectedToggle().equals(playPc)){
            firstPc = true;
        }else if(movimiento.getSelectedToggle().equals(playPlayer)){
            firstPc = false;
        }
        if(comboBox.getValue()==null || comboBoxRondas.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Informacion Invalida");
            alert.setContentText("Datos insuficientes: Escoga una marca!");
            alert.showAndWait();
        }else{
            if(comboBox.getValue().equals("X")){
                Player = X;
                Ai = O;
            }else if(comboBox.getValue().equals("O")){
                Player = O;
                Ai = X;
            }
            rondasGanadasPC = 0;
            rondasGanadasPlayer = 0;
            rondasParaGanar = comboBoxRondas.getValue();
            App.setRoot("PGame");
        }
    }
}
