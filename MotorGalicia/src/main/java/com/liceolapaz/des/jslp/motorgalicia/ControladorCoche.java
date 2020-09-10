/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liceolapaz.des.jslp.motorgalicia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author Juan
 */
public class ControladorCoche extends ControladorConNavegabilidad implements Initializable {
    
    @FXML
    TextField marca;
    
    @FXML
    TextField modelo;
    
    @FXML
    TextField anho;
    
    @FXML
    TextField motor;
    
    @FXML
    TextArea otrosDatos;
    
    @FXML
    TextField idUsuario;

    @FXML
    TableView<Coche> tablaCoche;
    
    int idCocheSeleccionado = 0;
    CocheDao cocheDao;
    
    public void buscar() {
        ObservableList<Coche> coches = FXCollections.observableArrayList();
        List<Coche> cochesDeLaBase = cocheDao.buscar();
        coches.addAll(cochesDeLaBase);
        tablaCoche.setItems(coches);
    }
    
    public void editar() {
    	Coche coche = tablaCoche.getSelectionModel().getSelectedItem();
    	if (coche == null) {
    		CocheDao.mensajeAlerta("Advertencia", "Debe seleccionar un vehículo");

    	} else {
    		marca.setText(coche.getMarca());
    		modelo.setText(coche.getModelo());
    		anho.setText(Integer.toString(coche.getAnho()));
    		motor.setText(coche.getMotor());
    		otrosDatos.setText(coche.getOtrosDatos());
    		idUsuario.setText(Integer.toString(coche.getIdUsuario()));
    		idCocheSeleccionado = coche.getId();
    	}
    }
    
    public void guardar() {
    	if (marca.getText().equals("") || marca.getText().equals("") || anho.getText().equals("") 
    		|| motor.getText().equals("") || otrosDatos.getText().equals("") || idUsuario.getText().equals("")) {
				CocheDao.mensajeAlerta("Advertencia", "Debe rellenar todos los campos");
			
    	} else {
    		Coche coche = new Coche();
    		coche.setId(idCocheSeleccionado);
    		coche.setMarca(marca.getText());
    		coche.setModelo(modelo.getText());
    		coche.setAnho(Integer.parseInt(anho.getText()));
    		coche.setMotor(motor.getText());
    		coche.setOtrosDatos(otrosDatos.getText());
    		coche.setIdUsuario(Integer.parseInt(idUsuario.getText()));
        
    		cocheDao.guardarOActualizar(coche);
    		idCocheSeleccionado = 0;
    		cargarCochesDeLaBase();
    		limpiarCampos();
    	}
    }
    
    public void eliminar() {
       Coche coche = tablaCoche.getSelectionModel().getSelectedItem();
       if (coche == null) {
   		CocheDao.mensajeAlerta("Advertencia", "Debe seleccionar un vehículo");

   		} else {
   			cocheDao.eliminar(coche);
   			cargarCochesDeLaBase();
   		}
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cocheDao = new CocheDao();
        cargarCochesDeLaBase();
        configurarColumnas();
    }
    
    public void cargarCochesDeLaBase() {
        ObservableList<Coche> coches = FXCollections.observableArrayList();
        List<Coche> cochesDeLaBase = cocheDao.cargarCochesDeLaBase();
        coches.addAll(cochesDeLaBase);
        tablaCoche.setItems(coches);
    }
    
    public void limpiarListado() {
       tablaCoche.getItems().clear();
    }
    
    public void limpiarCampos() {
        marca.clear();
        modelo.clear();
        anho.clear();
        motor.clear();
        otrosDatos.clear();
        idUsuario.clear();
    }
    
    private void configurarColumnas() {
        tablaCoche.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<TableColumn<Coche, ?>> columnas = tablaCoche.getColumns();
        columnas.get(0).setMaxWidth(1f * Integer.MAX_VALUE * 3); //3%
        columnas.get(1).setMaxWidth(1f * Integer.MAX_VALUE * 15); //15%
        columnas.get(2).setMaxWidth(1f * Integer.MAX_VALUE * 15); //15%
        columnas.get(3).setMaxWidth(1f * Integer.MAX_VALUE * 4); //4%
        columnas.get(4).setMaxWidth(1f * Integer.MAX_VALUE * 8); //8%
        columnas.get(5).setMaxWidth(1f * Integer.MAX_VALUE * 45); //45%
        columnas.get(6).setMaxWidth(1f * Integer.MAX_VALUE * 10); //10%
    }
}
