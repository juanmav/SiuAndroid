package com.diphot.siuweb.shared.dtos.filters;

import java.io.Serializable;

public class InspeccionFilterDTO implements FilterInterfaceDTO, Serializable {
	private static final long serialVersionUID = -1516963583061186062L;
	public String token;
	public int riesgo;
	public int estadoID;
	public Long inspeccionID;
}
