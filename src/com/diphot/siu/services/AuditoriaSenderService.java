package com.diphot.siu.services;

import com.diphot.siu.UserContainer;
import com.diphot.siu.connection.LinkChecker;
import com.diphot.siu.persistence.AuditoriaDAO;
import com.diphot.siu.services.restlet.AuditoriaRestLetInterface;
import com.diphot.siuweb.shared.dtos.AuditoriaDTO;
import android.content.Context;

public class AuditoriaSenderService  extends AbstractService implements Runnable{

	private static Context context;
	private static AuditoriaSenderService instance;
	private AuditoriaSenderService (){}

	public static AuditoriaSenderService getInstance(Context context){
		if (instance != null){
			return instance;
		} else {
			AuditoriaSenderService.context = context;
			instance = new AuditoriaSenderService();
			return instance;
		}
	}
	
	@Override
	public void run() {
		LinkChecker linkChecker = LinkChecker.getInstance(context);
		AuditoriaDAO idao = new AuditoriaDAO(context);
		AuditoriaDTO idto;
		while(this.running){
			while(true){
				if (linkChecker.linkOK()){
					try {
						idto = idao.getNotSent();
						if ( idto != null){
							AuditoriaRestLetInterface resource = WebServiceFactory.getAuditoriaRestLetInterface();
							idto.token = UserContainer.getUserDTO().getToken();
							resource.create(idto);
							idao.removeSent(idto.getId());
						}
					}catch (Exception e){
						// TODO ver porque viene en null.
						if (e != null){
							e.printStackTrace();
						}
					} finally{
						pause(5);
					}
				}
			}
		}
	}
}
