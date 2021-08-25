package edu.espol.grupo_12;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PLogInController implements Initializable {
    
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassword;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void sceneRegistro() throws IOException{
        App.setRoot("PRegistro");
    }
    
    public void sceneInicio() throws IOException{
        ArrayList<Player> jugadores = Player.jugadoresRegistrados;
        for(int i =0; i<jugadores.size(); i++){
            if(jugadores.get(i).getUsuario().equals(txtUserName.getText()) && jugadores.get(i).getPassword().equals(txtPassword.getText())){
                App.PlayerPlaying =jugadores.get(i);
                App.setRoot("PInicio");
                return;
            }
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Informacion Incorrecta");
        alert.setContentText("El usuario o la contraseña ingresada no es correcta");
        alert.showAndWait();
    }
    
}
