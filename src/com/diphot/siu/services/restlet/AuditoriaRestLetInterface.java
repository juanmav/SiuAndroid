package com.diphot.siu.services.restlet;

import java.util.ArrayList;

import org.restlet.resource.Options;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import com.diphot.siuweb.shared.dtos.AuditoriaDTO;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import com.diphot.siuweb.shared.dtos.filters.AuditoriaFilterDTO;

public interface AuditoriaRestLetInterface {
	@Put
    public void create(AuditoriaDTO auditoriaDTO);
	@Post
	public ArrayList<AuditoriaDTO> getByID(AuditoriaFilterDTO filter);
	@Options
	public void createAuditTask(InspeccionDTO inspeccionDTO);

}