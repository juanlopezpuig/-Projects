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
public class ControladorSugerencias extends ControladorConNavegabilidad implements Initializable {
   
   @FXML
   TableView<Sugerencia> tablaSugerencia;
   
   int idSugerenciaSeleccionada = 0;
   SugerenciaDao sugerenciaDao;
   
   public void buscar() {
       ObservableList<Sugerencia> sugerencias = FXCollections.observableArrayList();
       List<Sugerencia> sugerenciasDeLaBase = sugerenciaDao.buscar();
       sugerencias.addAll(sugerenciasDeLaBase);
       tablaSugerencia.setItems(sugerencias);
   }
   
   public void eliminar() {
      Sugerencia sugerencia = tablaSugerencia.getSelectionModel().getSelectedItem();
      if (sugerencia == null) {
     		SugerenciaDao.mensajeAlerta("Advertencia", "Debe seleccionar una sugerencia");

      } else {
    	 sugerenciaDao.eliminar(sugerencia);
    	 cargarSugerenciasDeLaBase();
      }
   }
   
   @Override
   public void initialize(URL location, ResourceBundle resources) {
       sugerenciaDao = new SugerenciaDao();
       cargarSugerenciasDeLaBase();
       configurarColumnas();
   }
   
   public void cargarSugerenciasDeLaBase() {
       ObservableList<Sugerencia> sugerencias = FXCollections.observableArrayList();
       List<Sugerencia> sugerenciasDeLaBase = sugerenciaDao.cargarSugerenciasDeLaBase();
       sugerencias.addAll(sugerenciasDeLaBase);
       tablaSugerencia.setItems(sugerencias);
   }
   
   public void limpiarListado() {
      tablaSugerencia.getItems().clear();
   }
   
   private void configurarColumnas() {
       tablaSugerencia.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
       ObservableList<TableColumn<Sugerencia, ?>> columnas = tablaSugerencia.getColumns();
       columnas.get(0).setMaxWidth(1f * Integer.MAX_VALUE * 3); //3%
       columnas.get(1).setMaxWidth(1f * Integer.MAX_VALUE * 25); //25%
       columnas.get(2).setMaxWidth(1f * Integer.MAX_VALUE * 72); //72%
   }
}