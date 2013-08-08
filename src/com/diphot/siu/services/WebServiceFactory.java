package com.diphot.siu.services;

import com.diphot.siu.SiuConstants;
import com.diphot.siu.services.restlet.AuditoriaRestLetInterface;
import com.diphot.siu.services.restlet.ClientResource;
import com.diphot.siu.services.restlet.InspeccionRestLetInterface;
import com.diphot.siu.services.restlet.TipificacionRestLetInterface;

public class WebServiceFactory {

	public static InspeccionRestLetInterface getInspeccionRestLetInterface(){
		ClientResource cr = new ClientResource(SiuConstants.URL_INSPECCIONES);
		InspeccionRestLetInterface resource = cr.wrap(InspeccionRestLetInterface.class);
		return resource;
	}
	
	public static TipificacionRestLetInterface getTipificacionRestLetInterface(){
		ClientResource cr = new ClientResource(SiuConstants.URL_TIPIFICACION);
		TipificacionRestLetInterface resource = cr.wrap(TipificacionRestLetInterface.class);
		return resource;
	}
	
	public static AuditoriaRestLetInterface getAuditoriaRestLetInterface(){
		ClientResource cr = new ClientResource(SiuConstants.URL_AUDITORIAS);
		AuditoriaRestLetInterface resource = cr.wrap(AuditoriaRestLetInterface.class);
		return resource;
	}

}
