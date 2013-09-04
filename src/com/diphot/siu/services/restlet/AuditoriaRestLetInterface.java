package com.diphot.siu.services.restlet;

import java.util.ArrayList;

import org.restlet.resource.Get;
import org.restlet.resource.Post;

import com.diphot.siuweb.shared.dtos.AuditoriaDTO;
import com.diphot.siuweb.shared.dtos.filters.AuditoriaFilterDTO;

public interface AuditoriaRestLetInterface {
	@Get
    public void create(AuditoriaDTO auditoriaDTO);
	@Post
	public ArrayList<AuditoriaDTO> getByID(AuditoriaFilterDTO filter);
}