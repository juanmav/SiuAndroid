package com.diphot.siuweb.shared.dtos;

import java.io.Serializable;

public class AuditoriaDTO implements Serializable, InterfaceDTO{

	private static final long serialVersionUID = 3146877714180406714L;

	private Long id;

	private Long inspeccionID;
	private String Img1;
	private String Img2;
	private String Img3;
	private Boolean resuelto;
	private String observaciones;
	private String fecha;

	public AuditoriaDTO(){
		
	}
	
	public AuditoriaDTO(Long id, Long inspeccionID, String Img1,
			String Img2, String Img3,
			Boolean resuelto, String observaciones) {
		this.id = id;
		this.inspeccionID = inspeccionID;
		this.Img1 = Img1;
		this.Img2 = Img2;
		this.Img3 = Img3;
		this.resuelto = resuelto;
		this.observaciones = observaciones;
	}


	public Long getInspeccionID() {
		return inspeccionID;
	}

	public void setInspeccionID(Long inspeccionID) {
		this.inspeccionID = inspeccionID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImg1() {
		return Img1;
	}

	public void setImg1(String Img1) {
		this.Img1 = Img1;
	}

	public String getImg2() {
		return Img2;
	}

	public void setImg2(String Img2) {
		this.Img2 = Img2;
	}

	public String getImg3() {
		return Img3;
	}

	public void setImg3(String Img3) {
		this.Img3 = Img3;
	}

	public Boolean getResuelto() {
		return resuelto;
	}

	public void setResuelto(Boolean resuelto) {
		this.resuelto = resuelto;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
