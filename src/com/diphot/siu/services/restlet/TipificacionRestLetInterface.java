package com.diphot.siu.services.restlet;
import java.util.ArrayList;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import com.diphot.siu.SiuConstants;
import com.diphot.siuweb.shared.dtos.AreaDTO;
import com.diphot.siuweb.shared.dtos.TemaDTO;
import com.diphot.siuweb.shared.dtos.TipoRelevamientoDTO;

public interface TipificacionRestLetInterface {
	
	public static String URL = SiuConstants.REST_BACKEND + SiuConstants.REST_TIPIFICACION;
	
	@Get
	public ArrayList<AreaDTO> getAreas();
	@Put
	public ArrayList<TipoRelevamientoDTO> getTiposRelevamiento();
	@Post
	public ArrayList<TemaDTO> getTemas();
}