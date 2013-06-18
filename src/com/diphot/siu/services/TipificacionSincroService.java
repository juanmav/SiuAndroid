package com.diphot.siu.services;

import java.util.ArrayList;
import org.restlet.resource.ClientResource;
import android.content.Context;
import com.diphot.siu.connection.LinkChecker;
import com.diphot.siu.persistence.AreaDAO;
import com.diphot.siu.persistence.TemaDAO;
import com.diphot.siu.persistence.TipoRelevamientoDAO;
import com.diphot.siu.services.restlet.TipificacionRestLetInterface;
import com.diphot.siuweb.shared.dtos.AreaDTO;
import com.diphot.siuweb.shared.dtos.TemaDTO;
import com.diphot.siuweb.shared.dtos.TipoRelevamientoDTO;

public class TipificacionSincroService  extends AbstractService implements Runnable {

	private static Context context;
	private static TipificacionSincroService instance;
	private TipificacionSincroService (){
	}
	
	public static TipificacionSincroService getInstance(Context context){
		if (instance != null){
			return instance;
		} else {
			TipificacionSincroService.context = context;
			instance = new TipificacionSincroService();
			return instance;
		}
	}
	@Override
	public void run() {
		LinkChecker linkChecker = LinkChecker.getInstance(context);
		while(this.running){
			if (linkChecker.linkOK()){
				try {
					ClientResource cr = new ClientResource(TipificacionRestLetInterface.URL);
					TipificacionRestLetInterface resource = cr.wrap(TipificacionRestLetInterface.class);
					// Sincro Areas
					ArrayList<AreaDTO> areas = resource.getAreas();
					AreaDAO areaDAO = new AreaDAO(context);
					// Sincro Tipos
					ArrayList<TipoRelevamientoDTO> tipos = resource.getTiposRelevamiento();
					TipoRelevamientoDAO tipoDAO = new TipoRelevamientoDAO(context);
					// Sincro Temas
					ArrayList<TemaDTO> temas = resource.getTemas();
					TemaDAO temaDAO = new TemaDAO(context);
					// Persisto.
					areaDAO.massiveCreate(areas);
					tipoDAO.massiveCreate(tipos);
					temaDAO.massiveCreate(temas);
				}catch (Exception e) {
					e.printStackTrace();
				} finally{
					pause(60);
				}
			}

		}
	}
	
}
