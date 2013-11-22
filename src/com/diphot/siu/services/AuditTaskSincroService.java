package com.diphot.siu.services;

import java.util.ArrayList;
import android.content.Context;

import com.diphot.siu.UserContainer;
import com.diphot.siu.connection.LinkChecker;
import com.diphot.siu.persistence.InspeccionDAO;
import com.diphot.siu.services.restlet.AuditoriaRestLetInterfaceTwo;
import com.diphot.siu.services.restlet.InspeccionRestLetInterfaceTwo;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import com.diphot.siuweb.shared.dtos.filters.InspeccionFilterDTO;

public class AuditTaskSincroService extends AbstractService implements Runnable {

	private static Context context;
	private static AuditTaskSincroService instance;
	private AuditTaskSincroService (){}

	public static AuditTaskSincroService getInstance(Context context){
		if (instance != null){
			return instance;
		} else {
			AuditTaskSincroService.context = context;
			instance = new AuditTaskSincroService();
			return instance;
		}
	}

	@Override
	public void run() {
		LinkChecker linkChecker = LinkChecker.getInstance(context);
		while(this.running){
			if (linkChecker.linkOK()){
				try {
					AuditoriaRestLetInterfaceTwo resource = WebServiceFactory.getAuditoriaRestLetInterfaceTwo();
					InspeccionDTO i = new InspeccionDTO();
					i.token = UserContainer.getUserDTO().getToken();
					ArrayList<InspeccionDTO> inspecciones = resource.getInspeccionesToAuditar(i);
					InspeccionDAO idao = new InspeccionDAO(context);
					for (InspeccionDTO ii : inspecciones){
						InspeccionRestLetInterfaceTwo resourceIMG = WebServiceFactory.getInspeccionRestLetInterfaceTwo();
						InspeccionFilterDTO filter = new InspeccionFilterDTO();
						filter.inspeccionID = ii.getId();
						filter.token = UserContainer.getUserDTO().getToken();
						InspeccionDTO aux = resourceIMG.getDTOWithImage(filter);
						ii.setImg1(aux.getImg1());
						ii.setImg2(aux.getImg2());
						ii.setImg2(aux.getImg3());
						idao.createToAudit(ii);
					}
				}catch (Exception e) {
					// TODO
					e.printStackTrace();
				}catch (Error e) {
					//TODO
					e.printStackTrace();
				} finally{
					this.running = false;
				}
			}
			this.running = false;
		}
	}
}
