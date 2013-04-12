package com.diphot.siuweb.shared.dtos;


public class AreaDTO implements  InterfaceDTO {
	private Long id;
	private String nombre;

	public AreaDTO(){

	}

	public AreaDTO(Long id, String nombre){
		this.id = id;
		this.nombre = nombre;
	}

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
