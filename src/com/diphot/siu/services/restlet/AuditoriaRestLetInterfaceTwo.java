package com.diphot.siu.services.restlet;

import java.util.ArrayList;

import org.restlet.resource.Put;

import com.diphot.siuweb.shared.dtos.InspeccionDTO;

public interface AuditoriaRestLetInterfaceTwo {
	@Put
	public ArrayList<InspeccionDTO> getInspeccionesToAuditar(InspeccionDTO inspeccionDTO);
	
}
