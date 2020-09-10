/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liceolapaz.des.jslp.motorgalicia;

/**
 *
 * @author Juan
 */
public class Coche {
    
    private int id;
    private String marca;
    private String modelo;
    private int anho;
    private String motor;
    private String otrosDatos;
    private int idUsuario;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getMarca() {
        return marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnho() {
        return anho;
    }

    public void setAnho(int anho) {
        this.anho = anho;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }
    
    public String getOtrosDatos() {
        return otrosDatos;
    }
    
    public void setOtrosDatos(String otrosDatos) {
        this.otrosDatos = otrosDatos;
    }

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
}
