package com.liceolapaz.des.jslp.motorgalicia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class RegistroAdminDao {

	public RegistroAdminDao() {
		crearTablaSiNoExiste();
		insertarAdmin();
	}
	
	private void crearTablaSiNoExiste() {
		try(Connection conexionDB = DriverManager.getConnection
				(CocheDao.URL_CONEXION, CocheDao.USUARIO_BDD, CocheDao.PASSWORD_BDD)){
			Statement statement = conexionDB.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS registro" + 
					"(id INTEGER auto_increment, " + 
					"usuario VARCHAR(255), " +
					"password VARCHAR(255))";
			statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private void insertarAdmin() {
		boolean existeAdmin = existeAdmin("admin", "admin");
		if(!existeAdmin) {
			try(Connection conexionDB = DriverManager.getConnection
					(CocheDao.URL_CONEXION, CocheDao.USUARIO_BDD, CocheDao.PASSWORD_BDD)){
				Statement statement = conexionDB.createStatement();
				String sql = "INSERT INTO registro (usuario, password) VALUES ('admin', 'admin')";
				statement.executeUpdate(sql);
			}catch(Exception e) {
				mensajeError("Error", "No se ha podido insertar el administrador");
			}
		}
	}
	
	public boolean existeAdmin(String username, String password) {
		try(Connection conexionDB = DriverManager.getConnection
				(CocheDao.URL_CONEXION, CocheDao.USUARIO_BDD, CocheDao.PASSWORD_BDD)){
			Statement statement = conexionDB.createStatement();
			String sql = "SELECT * FROM registro WHERE usuario='" + username + "'AND password='" + password + "'";
			ResultSet resultSet = statement.executeQuery(sql);
			return resultSet.next();
		} catch (Exception e) {
			throw new RuntimeException("Ocurrió un error al consultar la información: " + e.getMessage());
		}	
	}
	
	public void nuevoAdmin(RegistroAdmin registroAdmin) {
		if (registroAdmin.getUsuario().equals("") && registroAdmin.getPassword().equals("")) {
			mensajeAlerta("Advertencia", "Debe rellenar los campos");
		} else {
			try(Connection conexionDB = DriverManager.getConnection
					(CocheDao.URL_CONEXION, CocheDao.USUARIO_BDD, CocheDao.PASSWORD_BDD)){
				Statement statement = conexionDB.createStatement();
				String sql = "INSERT INTO registro (usuario, password)" + "VALUES ('" + registroAdmin.getUsuario() + "', '" + registroAdmin.getPassword() + "')";
				statement.executeUpdate(sql);
				mensajeConfirmacion("Confirmación", "Administrador registrado correctamente");
			}catch(Exception e) {
				mensajeError("Error", "No se ha podido insertar el administrador");
			}
		}
	}
	
	public static void mensajeAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/iconos/alerta.png"));
        alert.showAndWait();
    }
	
	public static void mensajeError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/iconos/error.png"));
        alert.showAndWait();
    }
	
	private static void mensajeConfirmacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/iconos/alerta.png"));
        alert.showAndWait();
    }
}
