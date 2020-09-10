/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liceolapaz.des.jslp.motorgalicia;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Juan
 */
public class MenuPrincipal extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        LayoutPane layoutPane = new LayoutPane();
        layoutPane.cargarPantalla("login", ControladorLogin.class.getResource("FXMLLogin.fxml"));
        layoutPane.cargarPantalla("webView", ControladorWebView.class.getResource("FXMLWebView.fxml"));
        layoutPane.cargarPantalla("coches", ControladorCoche.class.getResource("FXMLCoche.fxml"));
        layoutPane.cargarPantalla("usuarios", ControladorUsuario.class.getResource("FXMLUsuario.fxml"));
        layoutPane.cargarPantalla("chart", ControladorCochesPorUsuario.class.getResource("FXMLChart.fxml"));
        layoutPane.cargarPantalla("quedadas", ControladorQuedada.class.getResource("FXMLQuedadas.fxml"));
        layoutPane.cargarPantalla("maps", ControladorWebViewMaps.class.getResource("FXMLMaps.fxml"));
        layoutPane.cargarPantalla("sugerencias", ControladorSugerencias.class.getResource("FXMLSugerencias.fxml"));
        
        layoutPane.cargarPantalla("informacionUsuario", ControladorInformacionUsuario.class.getResource("FXMLInformacionUsuario.fxml"));
        layoutPane.cargarPantalla("webViewUsuario", ControladorWebViewUsuario.class.getResource("FXMLWebViewUsuario.fxml"));
        layoutPane.cargarPantalla("cochesUsuario", ControladorCocheUsuario.class.getResource("FXMLCocheUsuario.fxml"));
        layoutPane.cargarPantalla("quedadasUsuario", ControladorQuedadaUsuario.class.getResource("FXMLQuedadasUsuario.fxml"));
        layoutPane.cargarPantalla("mapsUsuario", ControladorWebViewMapsUsuario.class.getResource("FXMLMapsUsuario.fxml"));
        layoutPane.cargarPantalla("quedadasApuntarse", ControladorQuedadaApuntarse.class.getResource("FXMLQuedadasApuntarse.fxml"));
        layoutPane.cargarPantalla("sugerenciasUsuario", ControladorSugerenciasUsuario.class.getResource("FXMLSugerenciasUsuario.fxml"));
        
        layoutPane.mostrarComoPantallaActual("login");
        
        Scene escena = new Scene(layoutPane, 1024, 768);
        primaryStage.setScene(escena);
        escena.getStylesheets().addAll(getClass().getResource("/styles/Estilos.css").toExternalForm());
        primaryStage.setTitle("MOTOR GALICIA");
        primaryStage.getIcons().add(new Image("/iconos/principal.png"));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
