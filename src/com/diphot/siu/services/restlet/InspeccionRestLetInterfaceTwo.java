package com.diphot.siu.services.restlet;

import org.restlet.resource.Options;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import com.diphot.siuweb.shared.dtos.filters.InspeccionFilterDTO;

public interface InspeccionRestLetInterfaceTwo {
	@Put
	public void confirmar(InspeccionDTO inspeccionDTO);
	@Post
	public void ejecutadaAuditable(InspeccionDTO inspeccionDTO);
	@Options
	public InspeccionDTO getDTOWithImage(InspeccionFilterDTO filter);
}
