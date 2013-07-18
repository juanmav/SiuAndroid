package com.diphot.siuweb.shared.dtos.filters;

import java.io.Serializable;

import com.diphot.siuweb.shared.dtos.AreaDTO;

public class TipoRelevamientoFilterDTO implements FilterInterfaceDTO, Serializable{
	private static final long serialVersionUID = 2853223696392005349L;
	public AreaDTO areadto;
	public String name;
}
