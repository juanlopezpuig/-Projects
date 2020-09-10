package com.liceolapaz.des.jslp.motorgalicia;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
*
* @author Juan
*/
public class ControladorQuedada extends ControladorConNavegabilidad implements Initializable {
   
   @FXML
   TextField nombre;
   
   @FXML
   DatePicker fecha;
   
   @FXML
   TextArea descripcion;
   
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
   
   public void editar() {
       Quedada quedada = tablaQuedada.getSelectionModel().getSelectedItem();
       if (quedada == null) {
    	   QuedadaDao.mensajeAlerta("Advertencia", "Debe seleccionar una quedada");
   		
       } else {
    	   nombre.setText(quedada.getNombre());
    	   java.sql.Date date = (java.sql.Date) quedada.getFecha();
    	   LocalDate localDate = date.toLocalDate();
    	   fecha.setValue(localDate);
    	   descripcion.setText(quedada.getDescripcion());
    	   idQuedadaSeleccionada = quedada.getId();
       }
   }
   
   public void guardar() {
	   if (nombre.getText().equals("") || descripcion.getText().equals("")) {
			QuedadaDao.mensajeAlerta("Advertencia", "Debe rellenar todos los campos");
				
	   } else {
		   Quedada quedada = new Quedada();
		   quedada.setId(idQuedadaSeleccionada);
		   quedada.setNombre(nombre.getText());
		   LocalDate localDate = fecha.getValue();
		   quedada.setFecha(java.sql.Date.valueOf(localDate));
		   quedada.setDescripcion(descripcion.getText());
       
		   quedadaDao.guardarOActualizar(quedada);
		   idQuedadaSeleccionada = 0;
		   cargarQuedadasDeLaBase();
		   limpiarCampos();
	   }
   }
   
   public void eliminar() {
      Quedada quedada = tablaQuedada.getSelectionModel().getSelectedItem();
      if (quedada == null) {
     		QuedadaDao.mensajeAlerta("Advertencia", "Debe seleccionar una quedada");

      } else {
    	  quedadaDao.eliminar(quedada);
    	  cargarQuedadasDeLaBase();
      }
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
   
   public void limpiarCampos() {
       nombre.clear();
       fecha.setValue(null);
       descripcion.clear();
   }
   
   private void configurarColumnas() {
       tablaQuedada.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
       ObservableList<TableColumn<Quedada, ?>> columnas = tablaQuedada.getColumns();
       columnas.get(0).setMaxWidth(1f * Integer.MAX_VALUE * 3); //3%
       columnas.get(1).setMaxWidth(1f * Integer.MAX_VALUE * 27); //27%
       columnas.get(2).setMaxWidth(1f * Integer.MAX_VALUE * 10); //10%
       columnas.get(3).setMaxWidth(1f * Integer.MAX_VALUE * 60); //60%
   }
}