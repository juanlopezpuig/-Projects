package com.liceolapaz.des.jslp.motorgalicia;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ControladorLogin extends ControladorConNavegabilidad {

	RegistroAdminDao registroAdminDao;
	int idAdminSeleccionado = 0;
	@FXML
	TextField usuario;
	@FXML
	PasswordField password;
	
	public ControladorLogin() {
		registroAdminDao = new RegistroAdminDao();
	}
	
	public void login() {
		boolean loginOk = registroAdminDao.existeAdmin(usuario.getText(), password.getText());
		
		if (usuario.getText().equals("") && password.getText().equals("")) {
			RegistroAdminDao.mensajeAlerta("Advertencia", "Debe rellenar los campos");
			limpiarCampos();
			
		} if (loginOk && !usuario.getText().equals("")) {
			 Stage stage = (Stage) layout.getScene().getWindow();
			 stage.hide();
			
			 layout.mostrarComoPantallaActual("webView");
			 stage = new Stage();
			 stage.setScene(layout.getScene());
			 stage.setTitle("MOTOR GALICIA");
			 stage.getIcons().add(new Image("/iconos/principal.png"));
			 stage.show();
			 limpiarCampos();
			
		} if (loginOk == false) {
			 RegistroAdminDao.mensajeAlerta("Advertencia", "No se ha encontrado ningún administrador con esos datos");
			 limpiarCampos();
		}
	}
	
	public void entrarUsuario() {
		Stage stage = (Stage) layout.getScene().getWindow();
		stage.hide();
		
		layout.mostrarComoPantallaActual("informacionUsuario");
		stage = new Stage();
		stage.setScene(layout.getScene());
		stage.setTitle("MOTOR GALICIA");
        stage.getIcons().add(new Image("/iconos/principal.png"));
		stage.show();
		limpiarCampos();
	}
	
	public void cancelar() {
		Platform.exit();
	}
	
	public void nuevoAdmin() {
		RegistroAdmin registroAdmin = new RegistroAdmin();
		registroAdmin.setId(idAdminSeleccionado);
        registroAdmin.setUsuario(usuario.getText());
        registroAdmin.setPassword(usuario.getText());
        
        registroAdminDao.nuevoAdmin(registroAdmin);
        idAdminSeleccionado = 0;
        
        limpiarCampos();
	}
	
	public void limpiarCampos() {
        usuario.clear();
        password.clear();
    }
}
