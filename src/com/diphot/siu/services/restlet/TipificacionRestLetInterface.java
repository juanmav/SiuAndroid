package com.diphot.siu.services.restlet;

import java.util.ArrayList;
import org.restlet.resource.Options;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import com.diphot.siuweb.shared.dtos.AreaDTO;
import com.diphot.siuweb.shared.dtos.TemaDTO;
import com.diphot.siuweb.shared.dtos.TipoRelevamientoDTO;
import com.diphot.siuweb.shared.dtos.UserDTO;

public interface TipificacionRestLetInterface {
	@Options
	public ArrayList<AreaDTO> getAreas(UserDTO userdto);
	@Put
	public ArrayList<TipoRelevamientoDTO> getTiposRelevamiento(UserDTO userdto);
	@Post
	public ArrayList<TemaDTO> getTemas(UserDTO userdto);
}