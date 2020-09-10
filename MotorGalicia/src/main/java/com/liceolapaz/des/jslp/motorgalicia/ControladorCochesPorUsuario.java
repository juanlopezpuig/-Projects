package com.liceolapaz.des.jslp.motorgalicia;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

public class ControladorCochesPorUsuario extends ControladorConNavegabilidad implements Initializable {

	UsuarioDao usuarioDao;
	
	@FXML
	private PieChart chart;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		usuarioDao = new UsuarioDao();
		cargarDatosEnElChart();
	}
	
	public void cargarDatosEnElChart() {
		Map<String, Integer> cochesPorUsuario = usuarioDao.contarCochesPorUsuarios();
		ObservableList<PieChart.Data> datosParaElChart = FXCollections.observableArrayList();
		cochesPorUsuario.forEach((nombreUsuario, cantidad) -> {
			PieChart.Data data = new PieChart.Data(nombreUsuario, cantidad);
			datosParaElChart.add(data);
		});
		chart.setData(datosParaElChart);
	}
}
