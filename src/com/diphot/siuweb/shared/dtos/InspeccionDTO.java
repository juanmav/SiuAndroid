package com.diphot.siuweb.shared.dtos;

import java.util.Date;


public class InspeccionDTO implements InterfaceDTO {

	private Long id;
	private String calle;
	private Integer altura;
	private TemaDTO tema;
	private Double latitude;
	private Double longitude;
	private Date fecha;
	private String img1;
	private String img2;
	private String img3;
	
	
	public InspeccionDTO(){
		
	}
	
	public InspeccionDTO(Long id, String calle, Integer altura, TemaDTO tema,
			 Double latitude, Double longitude,Date fecha) {
		super();
		this.id = id;
		this.calle = calle;
		this.altura = altura;
		this.tema = tema;
		this.longitude = longitude;
		this.latitude = latitude;
		this.fecha = fecha;
	}
	
	public InspeccionDTO(Long id, String calle, Integer altura, TemaDTO tema,
			 Double latitude, Double longitude, Date fecha, String img1, String img2, String img3) {
		super();
		this.id = id;
		this.calle = calle;
		this.altura = altura;
		this.tema = tema;
		this.longitude = longitude;
		this.latitude = latitude;
		this.fecha = fecha;
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
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
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
}
