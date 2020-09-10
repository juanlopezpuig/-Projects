/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liceolapaz.des.jslp.motorgalicia;

import static com.liceolapaz.des.jslp.motorgalicia.CocheDao.PASSWORD_BDD;
import static com.liceolapaz.des.jslp.motorgalicia.CocheDao.URL_CONEXION;
import static com.liceolapaz.des.jslp.motorgalicia.CocheDao.USUARIO_BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class UsuarioDao {
    
    private static String busquedaUsuario;
	private static Optional<ButtonType> action;
    
    public UsuarioDao() {
        crearTablaSiNoExiste();
    }
    
    private void crearTablaSiNoExiste() {
    	try (Connection conexionBD = DriverManager.getConnection(CocheDao.URL_CONEXION,
                    CocheDao.USUARIO_BDD, CocheDao.PASSWORD_BDD)) {
            Statement statement = conexionBD.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS usuario" + "(id INTEGER auto_increment primary key, "
			+ "nombre VARCHAR(100) NOT NULL, " + "apellidos VARCHAR(100) NOT NULL, "
			+ "fechaNacimiento TIMESTAMP NOT NULL, " 
            + "intereses ENUM('Mecanica','Detailing','Modificaciones','Quedadas') NOT NULL, " 
            + "localizacion VARCHAR(100) NOT NULL)";
            statement.executeUpdate(sql);
		} catch (Exception e) {
            e.printStackTrace();
		}
    }
    
    public Map<String, Integer> contarCochesPorUsuarios(){
		List<Usuario> usuarios = cargarUsuariosDeLaBase();
		Map<String, Integer> cochesPorUsuario = new HashMap<>();
		
		try(Connection conexionBD = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)){
			Statement statement = conexionBD.createStatement();
			String sql = "SELECT idUsuario, count(*) as cantidad FROM " + 
					"coche GROUP BY idUsuario";
			ResultSet resultado = statement.executeQuery(sql);
			while(resultado.next()) {
				int idUsuario = resultado.getInt("idUsuario");
				int cantidadCoches = resultado.getInt("cantidad");
				
				for(Usuario usuario : usuarios) {
					if(usuario.getId() == idUsuario) {
						cochesPorUsuario.put(usuario.getNombre(), cantidadCoches);
						break;
					}
				}
			}
		}catch(Exception e) {
			throw new RuntimeException("Ocurrió un error al consultar la información: " + e.getMessage());
		}
		return cochesPorUsuario;
	}
    
    public void guardarOActualizar(Usuario usuario) {
        if (usuario.getId() == 0) {
            guardar(usuario);
        } else {
            actualizar(usuario);
        }
    }
    
    public void guardar(Usuario usuario) {
        try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
            Statement statement = conexionDB.createStatement();
            String sql = "INSERT INTO usuario (nombre, apellidos, fechaNacimiento, intereses, localizacion) "
					+ "VALUES ('" + usuario.getNombre() + "', '" + usuario.getApellidos() + "', '"
					+ usuario.getFechaNacimiento() + "', '" + usuario.getIntereses() + "', '"
					+ usuario.getLocalizacion() + "')";
            statement.executeUpdate(sql);
        } catch (Exception e) {
            mensajeAlerta("Advertencia", "No se ha podido guardar el usuario, compruebe que ha cubierto correctamente los campos");
        }
    }
    
    public void actualizar(Usuario usuario) {
        try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
            Statement statement = conexionDB.createStatement();
            String sql = "UPDATE usuario set nombre='" + usuario.getNombre() + "', apellidos='"
				+ usuario.getApellidos() + "', fechaNacimiento='" + usuario.getFechaNacimiento()
				+ "', intereses='" + usuario.getIntereses()
				+ "', localizacion='" + usuario.getLocalizacion() + "' WHERE id=" + usuario.getId();
            statement.executeUpdate(sql);
        } catch (Exception e) {
            mensajeAlerta("Advertencia", "No se ha podido actualizar el usuario, compruebe que ha cubierto correctamente los campos");
        }
    }
    
     public void eliminar(Usuario usuario) {
        mensajeConfirmacion("Confirmación", "¿Está seguro de que quiere eliminar el usuario seleccionado?");
        if (action.get() == ButtonType.OK) {
        	try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
        		Statement statement = conexionDB.createStatement();
        		String sql = "DELETE FROM usuario WHERE id=" + usuario.getId();
        		statement.executeUpdate(sql);
        	} catch (Exception e) {
        		mensajeAlerta("Advertencia", "No se ha podido eliminar el usuario, compruebe que ha seleccionado algún elemento");
        	}
        }
    }
     
     public List<Usuario> cargarUsuariosDeLaBase() {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
            Statement statement = conexionDB.createStatement();
            String sql = "SELECT * FROM usuario";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getInt("id"));
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setApellidos(resultSet.getString("apellidos"));
                usuario.setFechaNacimiento(resultSet.getDate("fechaNacimiento"));
                usuario.setIntereses(resultSet.getString("intereses"));
                usuario.setLocalizacion(resultSet.getString("localizacion"));
                usuarios.add(usuario);
            }
        } catch (Exception e) {
            mensajeError("Error", "No se han podido cargar los usuarios de la base de datos");
        }
        return usuarios;
    }
     
    public List<Usuario> buscar() {
        List<Usuario> usuarios = new ArrayList<>();
        mensajeBusqueda("Buscar Usuario", "Buscar Usuario", "Nombre");
        try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
            Statement statement = conexionDB.createStatement();
            String sql = "SELECT * FROM usuario WHERE nombre = '" + busquedaUsuario + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                do {
                    Usuario usuario = new Usuario();
                    usuario.setId(resultSet.getInt("id"));
                    usuario.setNombre(resultSet.getString("nombre"));
                    usuario.setApellidos(resultSet.getString("apellidos"));
                    usuario.setFechaNacimiento(resultSet.getDate("fechaNacimiento"));
                    usuario.setIntereses(resultSet.getString("intereses"));
                    usuario.setLocalizacion(resultSet.getString("localizacion"));
                    usuarios.add(usuario);
                } while (resultSet.next()); 
            } else {
                mensajeAlerta("Advertencia", "No se ha encontrado ningún usuario con ese nombre");
            }
        } catch (Exception e) {
            mensajeError("Error", "No se han podido cargar los usuarios de la base de datos");
        }
        return usuarios;
    }
    
    public void borrarTabla() {
    	try (Connection conexionDB = DriverManager.getConnection(URL_CONEXION, USUARIO_BDD, PASSWORD_BDD)) {
            Statement statement = conexionDB.createStatement();
            String sql = "DROP TABLE usuario";
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
        
        busquedaUsuario = (busqueda.getEditor().getText()); 
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
