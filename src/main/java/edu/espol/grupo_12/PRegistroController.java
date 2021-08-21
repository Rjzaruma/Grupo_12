/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 * FXML Controller class
 *
 * @author josue
 */
public class PRegistroController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtCorreo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void sceneLogIn() throws IOException {
        App.setRoot("PLogIn");
    }
    
    public void validarRegistro() throws IOException{
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        ArrayList<Player> jugadores = Player.jugadoresRegistrados;
        int veces = jugadores.size();
        for(int i =0; i<veces; i++){
            if(jugadores.get(i).getUsuario().equals(txtUsuario.getText())){
                alert.setTitle("Informacion Invalida");
                alert.setContentText("El usuario ya se encuentra registrado");
                alert.showAndWait();
                return;
            }
        }
        Registro();   
    }
    
    private void Registro() throws IOException{
        System.out.println("registrando");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        if(!txtNombre.getText().equals("") && !txtUsuario.getText().equals("") && !txtPassword.getText().equals("") && !txtCorreo.getText().equals("")){
            alert.setTitle("Registro Completo");
            alert.setContentText("Se ha registrado correctamente");
            alert.showAndWait();
            Player nuevo = new Player(txtNombre.getText(), txtUsuario.getText(), txtPassword.getText(), txtCorreo.getText());
            Player.jugadoresRegistrados.add(nuevo);
            App.setRoot("PLogIn");
        }else{
            alert.setTitle("Informacion Invalida");
            alert.setContentText("Datos insuficientes");
            alert.showAndWait();
        }
    }
}
