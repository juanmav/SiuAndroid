package com.diphot.siuweb.shared.dtos;

import java.io.Serializable;

public class LocalidadDTO implements Serializable, InterfaceDTO{
	
	private static final long serialVersionUID = 8953618823459155729L;
	private Long id;
	private String nombre;
	
	public LocalidadDTO(){
		
	}
	
	public LocalidadDTO(Long id, String nombre){
		this.id = id;
		this.nombre = nombre;
	}
	@Override
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
