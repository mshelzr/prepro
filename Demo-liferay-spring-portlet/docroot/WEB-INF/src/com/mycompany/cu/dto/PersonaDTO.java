package com.mycompany.cu.dto;

import java.io.Serializable;


@SuppressWarnings("serial")
public class PersonaDTO implements Serializable{

	private int idPersona;
	private String nombre;
	private String apellido;
	private int telf;
	private String sexo;
	
	public PersonaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public int getTelf() {
		return telf;
	}
	public void setTelf(int telf) {
		this.telf = telf;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	@Override
	public String toString() {
		return "PersonaDTO [idPersona=" + idPersona + ", nombre=" + nombre
				+ ", apellido=" + apellido + ", telf=" + telf + ", sexo="
				+ sexo + "]";
	}
	
	
	
}
