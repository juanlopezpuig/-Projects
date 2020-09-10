/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liceolapaz.des.jslp.motorgalicia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Juan
 */
public class CocheDao {
    
    public static final String URL_CONEXION = "jdbc:h2:./cochesDB";
    public static final String USUARIO_BDD = "root";
    public static final String PASSWORD_BDD = "root";
    private static String busquedaCoche;
	private static Optional<ButtonType> action;
    
    public CocheDao() {
        crearTablaSiNoExiste();
    }
    
    private void crearTablaSiNoExiste() {
        try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
            Statement statement = conexionDB.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS coche" + 
                    "(id INTEGER auto_increment, " +
                    "marca VARCHAR(100), " + 
                    "modelo VARCHAR(100), " +
                    "anho INT(4), " +
                    "motor VARCHAR(100), " +
                    "otrosDatos VARCHAR(100), " +
                    "idUsuario VARCHAR(100), " + "FOREIGN KEY (idUsuario) REFERENCES usuario (id))";
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void guardarOActualizar(Coche coche) {
        if (coche.getId() == 0) {
            guardar(coche);
        } else {
            actualizar(coche);
        }
    }
    
    public void guardar(Coche coche) {
        try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
            Statement statement = conexionDB.createStatement();
            String sql = "INSERT INTO coche(marca, modelo, anho, motor, otrosDatos, idUsuario)" + "VALUES ('" + coche.getMarca() + "', '" + 
                    coche.getModelo() + "', '" + coche.getAnho() + "', '" + coche.getMotor() + "', '" + 
            		coche.getOtrosDatos() + "', '" + coche.getIdUsuario() + "')";
            statement.executeUpdate(sql);
        } catch (Exception e) {
            mensajeAlerta("Advertencia", "No se ha podido guardar el coche, compruebe que ha cubierto correctamente los campos");
        }
    }
    
    public void actualizar(Coche coche) {
        try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
            Statement statement = conexionDB.createStatement();
            String sql = "UPDATE coche set marca='" + coche.getMarca() +
                    "', modelo='" + coche.getModelo() + 
                    "', anho='" + coche.getAnho() + 
                    "', motor='" + coche.getMotor() + 
                    "', otrosDatos='" + coche.getOtrosDatos() +
                    "', idUsuario='" + coche.getIdUsuario() +
                    "' WHERE id=" + coche.getId();
            statement.executeUpdate(sql);
        } catch (Exception e) {
            mensajeAlerta("Advertencia", "No se ha podido actualizar el coche, compruebe que ha cubierto correctamente los campos");
        }
    }
    
     public void eliminar(Coche coche) {
        mensajeConfirmacion("Confirmación", "¿Está seguro de que quiere eliminar el coche seleccionado?");
        if (action.get() == ButtonType.OK) {
        	try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
        		Statement statement = conexionDB.createStatement();
        		String sql = "DELETE FROM coche WHERE id=" + coche.getId();
        		statement.executeUpdate(sql);
        	} catch (Exception e) {
        		mensajeAlerta("Advertencia", "No se ha podido eliminar el coche, compruebe que ha seleccionado algún elemento");
        	}
        }
    }
     
     public List<Coche> cargarCochesDeLaBase() {
        List<Coche> coches = new ArrayList<>();
        try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
            Statement statement = conexionDB.createStatement();
            String sql = "SELECT * FROM coche";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Coche coche = new Coche();
                coche.setId(resultSet.getInt("id"));
                coche.setMarca(resultSet.getString("marca"));
                coche.setModelo(resultSet.getString("modelo"));
                coche.setAnho(resultSet.getInt("anho"));
                coche.setMotor(resultSet.getString("motor"));
                coche.setOtrosDatos(resultSet.getString("otrosDatos"));
                coche.setIdUsuario(resultSet.getInt("idUsuario"));
                coches.add(coche);
            }
        } catch (Exception e) {
            mensajeError("Error", "No se han podido cargar los vehículos de la base de datos");
        }
        return coches;
    }
     
    public List<Coche> buscar() {
        List<Coche> coches = new ArrayList<>();
        mensajeBusqueda("Buscar vehículo", "Buscar vehículo", "Marca");
        try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
            Statement statement = conexionDB.createStatement();
            String sql = "SELECT * FROM coche WHERE marca = '" + busquedaCoche + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                do {
                    Coche coche = new Coche();
                    coche.setId(resultSet.getInt("id"));
                    coche.setMarca(resultSet.getString("marca"));
                    coche.setModelo(resultSet.getString("modelo"));
                    coche.setAnho(resultSet.getInt("anho"));
                    coche.setMotor(resultSet.getString("motor"));
                    coche.setOtrosDatos(resultSet.getString("otrosDatos"));
                    coche.setIdUsuario(resultSet.getInt("idUsuario"));
                    coches.add(coche);
                } while (resultSet.next()); 
            } else {
                mensajeAlerta("Advertencia", "No se ha encontrado ningún vehículo de esa marca");
            }
        } catch (Exception e) {
            mensajeError("Error", "No se han podido cargar los vehículos de la base de datos");
        }
        return coches;
    }
    
    public void borrarTabla() {
    	try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
            Statement statement = conexionDB.createStatement();
            String sql = "DROP TABLE coche";
            statement.executeUpdate(sql);
        } catch (Exception e) {
            mensajeAlerta("Advertencia", "No se ha podido eliminar la tabla");
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
    
    private static void mensajeError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/iconos/error.png"));
        alert.showAndWait();
    }
    
    private static void mensajeBusqueda(String titulo, String mensaje1, String mensaje2) {
        TextInputDialog busqueda = new TextInputDialog("");
        busqueda.setTitle(titulo);
        busqueda.setHeaderText(mensaje1);
        busqueda.setContentText(mensaje2);
        Stage stage = (Stage) busqueda.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/iconos/buscar.png"));
        busqueda.showAndWait();
        
        busquedaCoche = (busqueda.getEditor().getText()); 
    }
    
    private static void mensajeConfirmacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/iconos/alerta.png"));
        action = alert.showAndWait();
    }
}
