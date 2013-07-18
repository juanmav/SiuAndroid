package com.diphot.siu.services.restlet;

import java.util.ArrayList;

import org.restlet.resource.Get;
import org.restlet.resource.Options;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import com.diphot.siu.views.SiuConstants;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import com.diphot.siuweb.shared.dtos.filters.InspeccionFilterDTO;

public interface InspeccionRestLetInterface {
	public static String URL = SiuConstants.REST_BACKEND + SiuConstants.REST_INSPECCION;
	@Get
    public void create(InspeccionDTO inspeccionDTO);
	@Post
	public void confirmar(Long id);
	@Put
	public void ejecutadaAuditable(Long id);
	@Options
	public ArrayList<InspeccionDTO> getDTOByQuery(InspeccionFilterDTO filter);
}