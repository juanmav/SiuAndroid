package com.diphot.siu.services.restlet;

import org.restlet.resource.Get;

import com.diphot.siu.views.SiuConstants;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;

public interface InspeccionRestLetInterface {
	
	public static String URL = SiuConstants.REST_BACKEND + SiuConstants.REST_INSPECCION;
	
	@Get
    public void create(InspeccionDTO inspeccionDTO);
}