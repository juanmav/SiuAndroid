package com.diphot.siu.services.restlet;

import java.util.ArrayList;
import org.restlet.resource.Get;
import org.restlet.resource.Options;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import com.diphot.siuweb.shared.dtos.filters.InspeccionFilterDTO;

public interface InspeccionRestLetInterface {
	
	@Post
    public void create(InspeccionDTO inspeccionDTO);
	@Get
	public ArrayList<InspeccionDTO> getList(InspeccionFilterDTO filter);
	@Put
	public void confirmar(Long id);
	@Options
	public void ejecutadaAuditable(Long id);
}