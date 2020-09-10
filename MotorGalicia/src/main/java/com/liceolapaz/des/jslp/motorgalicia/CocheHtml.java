package com.liceolapaz.des.jslp.motorgalicia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CocheHtml {
	
	private String textoHtml;
	
	public CocheHtml(List<Coche> coches) {
		
		textoHtml = "<!DOCTYPE html> <html><body><style>" + 
				"div{" +
				"width: 670px; padding-top: 8px; padding-bottom: 15px;" + 
				"margin: 0 auto 20px auto; background-color: #999999; border-radius: 15px;" + 
				"-moz-border-radius: 15px; -webkit-border-radius: 15px; color: #000000; padding:10px;}" + 
				"table { background-color: #000000; color: #000000; width=670px; cellpadding=2; cellsapcing=2;}" + 
				"thead { background-color: #999999; color: #000000; font: bold 15px verdana; padding:4px; line-height:30px}" + 
				"tbody tr:nth-child(even) {background: #999999;}" + 
				"tbody tr:nth-child(odd) {background: #FFF;}" + 
				"th, td {width: 325px; } </style>" + 
				"<h2 style=\"text-align:center;\">COCHES EN LA BASE DE DATOS</h2>" +
				"<div><table>" +
				"<thead><tr><th>MARCA</th><th>MODELO</th></tr></thead>" + 
				"<tbody>" + 
				"CONTENIDO_TABLA" + 
				"</tbody>" + 
				"</table></div></body></html>";
		
		String contenidoTabla = "";
		for (Coche coche : coches) {
			contenidoTabla += "<tr><td>" + coche.getMarca() + "</td><td>" + coche.getModelo() + "</td></tr>";
		}
		textoHtml = textoHtml.replace("CONTENIDO_TABLA", contenidoTabla);
	}

	public void generarArchivoHtml() {
		try {
			Files.write(Paths.get("coches.html"), textoHtml.getBytes());
		} catch (IOException ex) {
			throw new RuntimeException("No se pudo generar el archivo", ex);
		}
	}
}
