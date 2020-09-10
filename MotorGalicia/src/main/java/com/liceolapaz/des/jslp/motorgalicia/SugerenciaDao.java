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
public class SugerenciaDao {

private static String busquedaSugerencia;
private static Optional<ButtonType> action;
    
    public SugerenciaDao() {
        crearTablaSiNoExiste();
    }
    
    private void crearTablaSiNoExiste() {
    	try (Connection conexionBD = DriverManager.getConnection(CocheDao.URL_CONEXION,
                    CocheDao.USUARIO_BDD, CocheDao.PASSWORD_BDD)) {
            Statement statement = conexionBD.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS sugerencia" + "(id INTEGER auto_increment primary key, "
			+ "titulo VARCHAR(100) NOT NULL, " 
            + "descripcion VARCHAR(300) NOT NULL)";
            statement.executeUpdate(sql);
		} catch (Exception e) {
            e.printStackTrace();
		}
    }
    
    public void guardar(Sugerencia sugerencia) {
        try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
            Statement statement = conexionDB.createStatement();
            String sql = "INSERT INTO sugerencia (titulo, descripcion) "
					+ "VALUES ('" + sugerencia.getTitulo() + "', '"
					+ sugerencia.getDescripcion() + "')";
            statement.executeUpdate(sql);
        } catch (Exception e) {
            mensajeAlerta("Advertencia", "No se ha podido guardar la sugerencia, compruebe que ha cubierto correctamente los campos");
        }
    }
    
     public void eliminar(Sugerencia sugerencia) {
    	mensajeConfirmacion("Confirmación", "¿Está seguro de que quiere eliminar la sugerencia seleccionada?");
    	if (action.get() == ButtonType.OK) {
    		try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
    			Statement statement = conexionDB.createStatement();
    			String sql = "DELETE FROM sugerencia WHERE id=" + sugerencia.getId();
    			statement.executeUpdate(sql);
    		} catch (Exception e) {
    			mensajeAlerta("Advertencia", "No se ha podido eliminar la sugerencia, compruebe que ha seleccionado algún elemento");
    		}
    	}
    }
     
     public List<Sugerencia> cargarSugerenciasDeLaBase() {
        List<Sugerencia> sugerencias = new ArrayList<>();
        try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
            Statement statement = conexionDB.createStatement();
            String sql = "SELECT * FROM sugerencia";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Sugerencia sugerencia = new Sugerencia();
                sugerencia.setId(resultSet.getInt("id"));
                sugerencia.setTitulo(resultSet.getString("titulo"));
                sugerencia.setDescripcion(resultSet.getString("descripcion"));
                sugerencias.add(sugerencia);
            }
        } catch (Exception e) {
            mensajeError("Error", "No se han podido cargar las sugerencias de la base de datos");
        }
        return sugerencias;
    }
     
    public List<Sugerencia> buscar() {
        List<Sugerencia> sugerencias = new ArrayList<>();
        mensajeBusqueda("Buscar Sugerencias", "Buscar Sugerencias", "Título");
        try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
            Statement statement = conexionDB.createStatement();
            String sql = "SELECT * FROM sugerencia WHERE titulo = '" + busquedaSugerencia + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                do {
                	Sugerencia sugerencia = new Sugerencia();
                    sugerencia.setId(resultSet.getInt("id"));
                    sugerencia.setTitulo(resultSet.getString("titulo"));
                    sugerencia.setDescripcion(resultSet.getString("descripcion"));
                    sugerencias.add(sugerencia);
                } while (resultSet.next()); 
            } else {
                mensajeAlerta("Advertencia", "No se ha encontrado ninguna sugerencia con ese nombre");
            }
        } catch (Exception e) {
            mensajeError("Error", "No se han podido cargar las sugerencias de la base de datos");
        }
        return sugerencias;
    }
    
    public void borrarTabla() {
    	try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
            Statement statement = conexionDB.createStatement();
            String sql = "DROP TABLE sugerencia";
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
        
        busquedaSugerencia = (busqueda.getEditor().getText()); 
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
