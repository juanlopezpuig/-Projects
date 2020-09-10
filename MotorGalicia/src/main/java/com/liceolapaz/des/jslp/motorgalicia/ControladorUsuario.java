/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liceolapaz.des.jslp.motorgalicia;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Juan
 */
public class ControladorUsuario extends ControladorConNavegabilidad implements Initializable {
    
    @FXML
    TextField nombre;
    
    @FXML
    TextField apellidos;
    
    @FXML
    DatePicker fechaNacimiento;
    
    @FXML
    ComboBox<String> intereses;
    
    @FXML
    TextField localizacion;

    @FXML
    TableView<Usuario> tablaUsuario;
    
    int idUsuarioSeleccionado = 0;
    UsuarioDao usuarioDao;
    
    public void buscar() {
        ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
        List<Usuario> usuariosDeLaBase = usuarioDao.buscar();
        usuarios.addAll(usuariosDeLaBase);
        tablaUsuario.setItems(usuarios);
    }
    
    public void editar() {
        Usuario usuario = tablaUsuario.getSelectionModel().getSelectedItem();
        if (usuario == null) {
    		UsuarioDao.mensajeAlerta("Advertencia", "Debe seleccionar un usuario");
    		
        } else {
        	nombre.setText(usuario.getNombre());
        	apellidos.setText(usuario.getApellidos());
        	java.sql.Date date = (java.sql.Date) usuario.getFechaNacimiento();
        	LocalDate localDate = date.toLocalDate();
        	fechaNacimiento.setValue(localDate);
        	intereses.setValue(usuario.getIntereses());
        	localizacion.setText(usuario.getLocalizacion());
        	idUsuarioSeleccionado = usuario.getId();
    	}
    }
    
    public void guardar() {
    	if (nombre.getText().equals("") || apellidos.getText().equals("") || localizacion.getText().equals("")) {
    		UsuarioDao.mensajeAlerta("Advertencia", "Debe rellenar todos los campos");
    			
        } else {
        	Usuario usuario = new Usuario();
        	usuario.setId(idUsuarioSeleccionado);
        	usuario.setNombre(nombre.getText());
        	usuario.setApellidos(apellidos.getText());
        	LocalDate localDate = fechaNacimiento.getValue();
        	usuario.setFechaNacimiento(java.sql.Date.valueOf(localDate));
        	usuario.setIntereses(intereses.getValue());
        	usuario.setLocalizacion(localizacion.getText());
        	idUsuarioSeleccionado = 0;
        
        	usuarioDao.guardarOActualizar(usuario);
        	cargarUsuariosDeLaBase();
        	limpiarCampos();
        }
    }
    
    public void eliminar() {
       Usuario usuario = tablaUsuario.getSelectionModel().getSelectedItem();
       if (usuario == null) {
      		UsuarioDao.mensajeAlerta("Advertencia", "Debe seleccionar un usuario");

       } else {
    	   usuarioDao.eliminar(usuario);
    	   cargarUsuariosDeLaBase();
       }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usuarioDao = new UsuarioDao();
        cargarUsuariosDeLaBase();
        configurarColumnas();
    }
    
    public void cargarUsuariosDeLaBase() {
        ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
        List<Usuario> usuariosDeLaBase = usuarioDao.cargarUsuariosDeLaBase();
        usuarios.addAll(usuariosDeLaBase);
        tablaUsuario.setItems(usuarios);
    }
    
    public void limpiarListado() {
       tablaUsuario.getItems().clear();
    }
    
    public void limpiarCampos() {
        nombre.clear();
        apellidos.clear();
        fechaNacimiento.setValue(null);
        intereses.setValue(null);
        localizacion.clear();
    }
    
    private void configurarColumnas() {
        tablaUsuario.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<TableColumn<Usuario, ?>> columnas = tablaUsuario.getColumns();
        columnas.get(0).setMaxWidth(1f * Integer.MAX_VALUE * 3); //3%
        columnas.get(1).setMaxWidth(1f * Integer.MAX_VALUE * 15); //15%
        columnas.get(2).setMaxWidth(1f * Integer.MAX_VALUE * 15); //15%
        columnas.get(3).setMaxWidth(1f * Integer.MAX_VALUE * 15); //15%
        columnas.get(4).setMaxWidth(1f * Integer.MAX_VALUE * 15); //15%
        columnas.get(5).setMaxWidth(1f * Integer.MAX_VALUE * 15); //15%
    }
}
