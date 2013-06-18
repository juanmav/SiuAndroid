package com.diphot.siuweb.shared.dtos;

import java.io.Serializable;


public class TipoRelevamientoDTO implements Serializable,  InterfaceDTO{

	private static final long serialVersionUID = -8947341252532490026L;
	private Long id;
	private String nombre;
	private AreaDTO areadto;

	public TipoRelevamientoDTO(){

	}

	public String toString(){
		return this.nombre;
	}

	public TipoRelevamientoDTO(Long id, String nombre, AreaDTO areaDTO){
		this.id = id;
		this.nombre = nombre;
		this.areadto = areaDTO;
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

	public AreaDTO getAreadto() {
		return areadto;
	}

	public void setAreadto(AreaDTO areadto) {
		this.areadto = areadto;
	}

}
