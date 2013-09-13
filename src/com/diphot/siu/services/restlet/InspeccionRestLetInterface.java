package com.diphot.siu.services.restlet;

import java.util.ArrayList;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import com.diphot.siuweb.shared.dtos.filters.InspeccionFilterDTO;

public interface InspeccionRestLetInterface {
	@Post
    public void create(InspeccionDTO inspeccionDTO);
	@Put
	public ArrayList<InspeccionDTO> getList(InspeccionFilterDTO filter);
	
}
