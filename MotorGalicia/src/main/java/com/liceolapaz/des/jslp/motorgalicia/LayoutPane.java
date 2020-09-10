package com.liceolapaz.des.jslp.motorgalicia;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class LayoutPane extends BorderPane {

    private Map<String, Node> pantallasDeLaAplicacion;

    public LayoutPane() {
	this.pantallasDeLaAplicacion = new HashMap<>();
    }

    public void cargarPantalla(String nombreDeLaPantalla, URL urlArchivoFxml) throws IOException {
	FXMLLoader cargadorPantallas = new FXMLLoader(urlArchivoFxml);
	Parent pantalla = cargadorPantallas.load();

	ControladorConNavegabilidad controladorConNavegabilidad = cargadorPantallas.getController();
	controladorConNavegabilidad.setLayout(this);

	pantallasDeLaAplicacion.put(nombreDeLaPantalla, pantalla);
    }

    public void mostrarComoPantallaActual(String nombreDeLaPantalla) {
	this.setCenter(pantallasDeLaAplicacion.get(nombreDeLaPantalla));
    }
}
