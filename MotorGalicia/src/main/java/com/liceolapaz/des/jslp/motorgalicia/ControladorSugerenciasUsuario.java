package com.liceolapaz.des.jslp.motorgalicia;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
*
* @author Juan
*/
public class ControladorSugerenciasUsuario extends ControladorConNavegabilidad implements Initializable {
   
   @FXML
   TextField titulo;
   
   @FXML
   TextArea descripcion;
   
   int idSugerenciaSeleccionada = 0;
   SugerenciaDao sugerenciaDao;
   
   public void guardar() {
	   if (titulo.getText().equals("") || descripcion.getText().equals("")) {
			SugerenciaDao.mensajeAlerta("Advertencia", "Debe rellenar todos los campos");
	   } else {
		   Sugerencia sugerencia = new Sugerencia();
		   sugerencia.setId(idSugerenciaSeleccionada);
		   sugerencia.setTitulo(titulo.getText());
		   sugerencia.setDescripcion(descripcion.getText());
       
		   sugerenciaDao.guardar(sugerencia);
		   idSugerenciaSeleccionada = 0;
		   mensajeInformacion("Información", "Sugerencia guardada");
		   limpiarCampos();
		}
   }
   
   @Override
   public void initialize(URL location, ResourceBundle resources) {
       sugerenciaDao = new SugerenciaDao();
   }
   
   public void limpiarCampos() {
       titulo.clear();
       descripcion.clear();
   }
   
   private static void mensajeInformacion(String titulo, String mensaje) {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle(titulo);
       alert.setContentText(mensaje);
       Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
       stage.getIcons().add(new Image("/iconos/informacion.png"));
       alert.showAndWait();
   }
}