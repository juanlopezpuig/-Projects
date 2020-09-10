package com.liceolapaz.des.jslp.motorgalicia;


public abstract class ControladorConNavegabilidad {

	protected LayoutPane layout;
	
	public void setLayout(LayoutPane layout) {
		this.layout = layout;
	}
	
	public void mostrarPantallaLogin() {
    	this.layout.mostrarComoPantallaActual("login");
    }
	
	public void mostrarPantallaNoticias() {
    	this.layout.mostrarComoPantallaActual("webView");
    }
    
    public void mostrarPantallaCoches() {
    	this.layout.mostrarComoPantallaActual("coches");
    }

    public void mostrarPantallaUsuarios() {
    	this.layout.mostrarComoPantallaActual("usuarios");
    }
    
    public void mostrarPantallaChart() {
    	this.layout.mostrarComoPantallaActual("chart");
    }
    
    public void mostrarPantallaQuedadas() {
	   	this.layout.mostrarComoPantallaActual("quedadas");
    }
    
    public void mostrarPantallaMaps() {
	   	this.layout.mostrarComoPantallaActual("maps");
    }
    
    public void mostrarPantallaSugerencias() {
	   	this.layout.mostrarComoPantallaActual("sugerencias");
    }
    
    public void mostrarPantallaInformacionUsuario() {
	   	this.layout.mostrarComoPantallaActual("informacionUsuario");
    }
    
    public void mostrarPantallaNoticiasUsuario() {
    	this.layout.mostrarComoPantallaActual("webViewUsuario");
    }
    
    public void mostrarPantallaCochesUsuario() {
    	this.layout.mostrarComoPantallaActual("cochesUsuario");
    }
    
    public void mostrarPantallaQuedadasUsuario() {
	   	this.layout.mostrarComoPantallaActual("quedadasUsuario");
    }
    
    public void mostrarPantallaMapsUsuario() {
	   	this.layout.mostrarComoPantallaActual("mapsUsuario");
    }
    
    public void mostrarPantallaQuedadasApuntarse() {
	   	this.layout.mostrarComoPantallaActual("quedadasApuntarse");
    }
    
    public void mostrarPantallaSugerenciasUsuario() {
	   	this.layout.mostrarComoPantallaActual("sugerenciasUsuario");
    }
}
