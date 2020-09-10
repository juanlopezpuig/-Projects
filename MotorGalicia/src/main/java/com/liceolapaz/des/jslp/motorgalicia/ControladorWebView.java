package com.liceolapaz.des.jslp.motorgalicia;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

public class ControladorWebView extends ControladorConNavegabilidad implements Initializable {

	@FXML
	private WebView webView;
	
	private CocheDao cocheDao;

	public void mostrarCoches() throws Exception {
		new CocheHtml(cocheDao.cargarCochesDeLaBase()).generarArchivoHtml();
		String url = Paths.get("coches.html").toUri().toURL().toExternalForm();
		webView.getEngine().load(url);
	}
	
	public void linkKm77() {
		webView.getEngine().load("https://www.km77.com");
	}
	
	public void linkCochesNet() {
		webView.getEngine().load("https://www.coches.net");
	}
	
	public void linkCarAndDriver() {
		webView.getEngine().load("https://www.caranddriver.com/es/");
	}
	
	public void volverBuscador() {
		webView.getEngine().load("http://www.google.com");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		cocheDao = new CocheDao();
		webView.getEngine().load("http://www.google.com");
	}
}
