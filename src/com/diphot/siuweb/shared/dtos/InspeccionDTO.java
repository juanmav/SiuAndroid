package com.diphot.siuweb.shared.dtos;

import java.io.Serializable;

public class InspeccionDTO implements Serializable, InterfaceDTO {

	private static final long serialVersionUID = -359471250000224506L;
	private Long id;
	private String calle;
	private Integer altura;
	private TemaDTO tema;
	private Double latitude;
	private Double longitude;
	private String fecha;
	private String img1;
	private String img2;
	private String img3;
	private String imgMap;
	private String observacion;
	private int riesgo;
	private int lastStateIdentifier;
	public String token;
	public String UUID;
	private LocalidadDTO localidad;
	
	private String entreCalleUno;
	private String entreCalleDos;
	
	public InspeccionDTO(){
		
	}
	
	public InspeccionDTO(Long id){
		this.id = id;
	}
	
	public InspeccionDTO(Long id, String calle, Integer altura, TemaDTO tema,
			 Double latitude, Double longitude,String fecha) {
		super();
		this.id = id;
		this.calle = calle;
		this.altura = altura;
		this.tema = tema;
		this.longitude = longitude;
		this.latitude = latitude;
		this.fecha = fecha;
	}
	
	public InspeccionDTO(Long id, String calle, Integer altura, String observacion, TemaDTO tema,
			 Double latitude, Double longitude, String fecha, String img1, String img2, String img3, int riesgo) {
		super();
		this.id = id;
		this.calle = calle;
		this.altura = altura;
		this.tema = tema;
		this.longitude = longitude;
		this.latitude = latitude;
		this.fecha = fecha;
		this.img1 = img1;
		this.img2 = img2;
		this.img3 = img3;
		this.observacion = observacion;
		this.riesgo = riesgo;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCalle() {
		return calle;
	}
	
	public void setCalle(String calle) {
		this.calle = calle;
	}
	
	public Integer getAltura() {
		return altura;
	}
	
	public void setAltura(Integer altura) {
		this.altura = altura;
	}
	
	public TemaDTO getTema() {
		return tema;
	}
	
	public void setTema(TemaDTO tema) {
		this.tema = tema;
	}
	
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg3() {
		return img3;
	}

	public void setImg3(String img3) {
		this.img3 = img3;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public int getRiesgo() {
		return riesgo;
	}

	public void setRiesgo(int riesgo) {
		this.riesgo = riesgo;
	}

	public int getLastStateIdentifier() {
		return lastStateIdentifier;
	}

	public void setLastStateIdentifier(int estado) {
		this.lastStateIdentifier = estado;
	}

	public String getImgMap() {
		return imgMap;
	}

	public void setImgMap(String imgMap) {
		this.imgMap = imgMap;
	}

	public LocalidadDTO getLocalidad() {
		return localidad;
	}

	public void setLocalidad(LocalidadDTO localidad) {
		this.localidad = localidad;
	}
	
	public String getEntreCalleUno() {
		return entreCalleUno;
	}

	public void setEntreCalleUno(String entreCalleUno) {
		this.entreCalleUno = entreCalleUno;
	}

	public String getEntreCalleDos() {
		return entreCalleDos;
	}
	
	public void setEntreCalleDos(String entreCalleDos) {
		this.entreCalleDos = entreCalleDos;
	}
}
