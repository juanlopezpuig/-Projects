package com.liceolapaz.des.jslp.motorgalicia;

import static com.liceolapaz.des.jslp.motorgalicia.CocheDao.PASSWORD_BDD;
import static com.liceolapaz.des.jslp.motorgalicia.CocheDao.URL_CONEXION;
import static com.liceolapaz.des.jslp.motorgalicia.CocheDao.USUARIO_BDD;

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
public class QuedadaDao {

private static String busquedaQuedada;
private static Optional<ButtonType> action;
    
    public QuedadaDao() {
        crearTablaSiNoExiste();
    }
    
    private void crearTablaSiNoExiste() {
    	try (Connection conexionBD = DriverManager.getConnection(CocheDao.URL_CONEXION,
                    CocheDao.USUARIO_BDD, CocheDao.PASSWORD_BDD)) {
            Statement statement = conexionBD.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS quedada" + "(id INTEGER auto_increment primary key, "
			+ "nombre VARCHAR(100) NOT NULL, " 
            + "fecha TIMESTAMP NOT NULL, " 
            + "descripcion VARCHAR(300) NOT NULL)";
            statement.executeUpdate(sql);
		} catch (Exception e) {
            e.printStackTrace();
		}
    }
    
    public void guardarOActualizar(Quedada quedada) {
        if (quedada.getId() == 0) {
            guardar(quedada);
        } else {
            actualizar(quedada);
        }
    }
    
    public void guardar(Quedada quedada) {
        try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
            Statement statement = conexionDB.createStatement();
            String sql = "INSERT INTO quedada (nombre, fecha, descripcion) "
					+ "VALUES ('" + quedada.getNombre() + "', '" + quedada.getFecha() + "', '"
					+ quedada.getDescripcion() + "')";
            statement.executeUpdate(sql);
        } catch (Exception e) {
            mensajeAlerta("Advertencia", "No se ha podido guardar la quedada, compruebe que ha cubierto correctamente los campos");
        }
    }
    
    public void actualizar(Quedada quedada) {
        try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
            Statement statement = conexionDB.createStatement();
            String sql = "UPDATE quedada set nombre='" + quedada.getNombre() 
            	+ "', fecha='" + quedada.getFecha()
				+ "', descripcion='" + quedada.getDescripcion() + "' WHERE id=" + quedada.getId();
            statement.executeUpdate(sql);
        } catch (Exception e) {
            mensajeAlerta("Advertencia", "No se ha podido actualizar la quedada, compruebe que ha cubierto correctamente los campos");
        }
    }
    
     public void eliminar(Quedada quedada) {
    	mensajeConfirmacion("Confirmación", "¿Está seguro de que quiere eliminar la quedada seleccionada?");
    	if (action.get() == ButtonType.OK) {
    		try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
    			Statement statement = conexionDB.createStatement();
    			String sql = "DELETE FROM quedada WHERE id=" + quedada.getId();
    			statement.executeUpdate(sql);
    		} catch (Exception e) {
    			mensajeAlerta("Advertencia", "No se ha podido eliminar la quedada, compruebe que ha seleccionado algún elemento");
    		}
    	}
    }
     
     public List<Quedada> cargarQuedadasDeLaBase() {
        List<Quedada> quedadas = new ArrayList<>();
        try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
            Statement statement = conexionDB.createStatement();
            String sql = "SELECT * FROM quedada";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Quedada quedada = new Quedada();
                quedada.setId(resultSet.getInt("id"));
                quedada.setNombre(resultSet.getString("nombre"));
                quedada.setFecha(resultSet.getDate("fecha"));
                quedada.setDescripcion(resultSet.getString("descripcion"));
                quedadas.add(quedada);
            }
        } catch (Exception e) {
            mensajeError("Error", "No se han podido cargar las quedadas de la base de datos");
        }
        return quedadas;
    }
     
    public List<Quedada> buscar() {
        List<Quedada> quedadas = new ArrayList<>();
        mensajeBusqueda("Buscar Quedadas", "Buscar Quedadas", "Nombre");
        try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
            Statement statement = conexionDB.createStatement();
            String sql = "SELECT * FROM quedada WHERE nombre = '" + busquedaQuedada + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                do {
                	Quedada quedada = new Quedada();
                    quedada.setId(resultSet.getInt("id"));
                    quedada.setNombre(resultSet.getString("nombre"));
                    quedada.setFecha(resultSet.getDate("fecha"));
                    quedada.setDescripcion(resultSet.getString("descripcion"));
                    quedadas.add(quedada);
                } while (resultSet.next()); 
            } else {
                mensajeAlerta("Advertencia", "No se ha encontrado ninguna quedada con ese nombre");
            }
        } catch (Exception e) {
            mensajeError("Error", "No se han podido cargar las quedadas de la base de datos");
        }
        return quedadas;
    }
    
    public void borrarTabla() {
    	try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
            Statement statement = conexionDB.createStatement();
            String sql = "DROP TABLE quedada";
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
        
        busquedaQuedada = (busqueda.getEditor().getText()); 
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
