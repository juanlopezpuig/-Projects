package com.liceolapaz.des.jslp.motorgalicia;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

public class ControladorWebViewUsuario extends ControladorConNavegabilidad implements Initializable {

	@FXML
	private WebView webViewUsuario;
	
	public void linkKm77() {
		webViewUsuario.getEngine().load("https://www.km77.com");
	}
	
	public void linkCochesNet() {
		webViewUsuario.getEngine().load("https://www.coches.net");
	}
	
	public void linkCarAndDriver() {
		webViewUsuario.getEngine().load("https://www.caranddriver.com/es/");
	}
	
	public void volverBuscador() {
		webViewUsuario.getEngine().load("http://www.google.com");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		webViewUsuario.getEngine().load("http://www.google.com");
	}
}
