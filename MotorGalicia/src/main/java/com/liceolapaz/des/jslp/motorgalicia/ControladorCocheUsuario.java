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

/**
 *
 * @author Juan
 */
public class ControladorCocheUsuario extends ControladorConNavegabilidad implements Initializable {
    
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
    
    private void configurarColumnas() {
        tablaCoche.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<TableColumn<Coche, ?>> columnas = tablaCoche.getColumns();
        columnas.get(0).setMaxWidth(1f * Integer.MAX_VALUE * 43); //43%
        columnas.get(1).setMaxWidth(1f * Integer.MAX_VALUE * 43); //43%
        columnas.get(2).setMaxWidth(1f * Integer.MAX_VALUE * 4); //4%
    }
}
