package com.liceolapaz.des.jslp.motorgalicia;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

public class ControladorWebViewMapsUsuario extends ControladorConNavegabilidad implements Initializable {

	@FXML
	private WebView webViewMaps;
	
	public void linkMaps() {
		webViewMaps.getEngine().load("https://www.google.es/maps/preview");
	}
	
	public void linkEarth() {
		webViewMaps.getEngine().load("https://earth.google.com/web/");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		webViewMaps.getEngine().load("https://www.google.es/maps/preview");
	}
}
