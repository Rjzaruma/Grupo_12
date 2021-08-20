/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.espol.grupo_12;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author josue
 */
public class Player implements Serializable{
    private String nombre;
    private String usuario;
    private String password;
    private String email;
    private int partidasG;
    private int partidasT;
    public static ArrayList<Player> jugadoresRegistrados=new ArrayList<>();
   
    public Player(String nombre, String usuario, String password, String email) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.password = password;
        this.email = email;
    }
    
    public float winRate(int totales, int ganadas){
        return totales/ganadas;
    }
    public static void serializar(){
        try(ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("jugadores"))){
            o.writeObject(jugadoresRegistrados);
        }catch(IOException e){
            System.out.println("Error al serealizar: "+e);
        }
        
    }
    
    public static void deserializar(){
        try(ObjectInputStream i = new ObjectInputStream(new FileInputStream("jugadores"))){
            jugadoresRegistrados =  (ArrayList<Player>)i.readObject();  // Casteo porque es la unica forma accesible para serealizar.
        }catch(IOException e){
            System.out.println("Error al desserealizar: "+e);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPartidasG() {
        return partidasG;
    }

    public void setPartidasG(int partidasG) {
        this.partidasG = partidasG;
    }

    public int getPartidasT() {
        return partidasT;
    }

    public void setPartidasT(int partidasT) {
        this.partidasT = partidasT;
    }

    public static ArrayList<Player> getJugadoresRegistrados() {
        return jugadoresRegistrados;
    }

    public static void setJugadoresRegistrados(ArrayList<Player> jugadoresRegistrados) {
        Player.jugadoresRegistrados = jugadoresRegistrados;
    }
    
}
