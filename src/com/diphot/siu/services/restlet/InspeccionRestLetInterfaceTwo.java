package com.diphot.siu.services.restlet;

import org.restlet.resource.Post;
import org.restlet.resource.Put;

import com.diphot.siuweb.shared.dtos.InspeccionDTO;

public interface InspeccionRestLetInterfaceTwo {
	@Put
	public void confirmar(InspeccionDTO inspeccionDTO);
	@Post
	public void ejecutadaAuditable(InspeccionDTO inspeccionDTO);
}
