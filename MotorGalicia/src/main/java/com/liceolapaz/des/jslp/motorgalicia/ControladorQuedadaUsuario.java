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
public class ControladorQuedadaUsuario extends ControladorConNavegabilidad implements Initializable {
   
   @FXML
   TableView<Quedada> tablaQuedada;
   
   int idQuedadaSeleccionada = 0;
   QuedadaDao quedadaDao;
   
   public void buscar() {
       ObservableList<Quedada> quedadas = FXCollections.observableArrayList();
       List<Quedada> quedadasDeLaBase = quedadaDao.buscar();
       quedadas.addAll(quedadasDeLaBase);
       tablaQuedada.setItems(quedadas);
   }
   
   @Override
   public void initialize(URL location, ResourceBundle resources) {
       quedadaDao = new QuedadaDao();
       cargarQuedadasDeLaBase();
       configurarColumnas();
   }
   
   public void cargarQuedadasDeLaBase() {
       ObservableList<Quedada> quedadas = FXCollections.observableArrayList();
       List<Quedada> quedadasDeLaBase = quedadaDao.cargarQuedadasDeLaBase();
       quedadas.addAll(quedadasDeLaBase);
       tablaQuedada.setItems(quedadas);
   }
   
   public void limpiarListado() {
      tablaQuedada.getItems().clear();
   }
   
   private void configurarColumnas() {
       tablaQuedada.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
       ObservableList<TableColumn<Quedada, ?>> columnas = tablaQuedada.getColumns();
       columnas.get(0).setMaxWidth(1f * Integer.MAX_VALUE * 28); //28%
       columnas.get(1).setMaxWidth(1f * Integer.MAX_VALUE * 12); //12%
       columnas.get(2).setMaxWidth(1f * Integer.MAX_VALUE * 60); //60%
   }
}