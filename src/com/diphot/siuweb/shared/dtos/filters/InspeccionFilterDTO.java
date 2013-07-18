package com.diphot.siuweb.shared.dtos.filters;

import java.io.Serializable;
import java.util.Date;

public class InspeccionFilterDTO implements FilterInterfaceDTO, Serializable {
	private static final long serialVersionUID = -1516963583061186062L;
	public Long id;
	public String calle;
	public Integer altura;
	public Date fecha;
	public Long areaID;
	public Long tipoID;
	public Long temaID;
	public int riesgo;
	public int estadoID;
}
