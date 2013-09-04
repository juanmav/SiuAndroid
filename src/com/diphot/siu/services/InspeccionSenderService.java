package com.diphot.siu.services;

import com.diphot.siu.UserContainer;
import com.diphot.siu.connection.LinkChecker;
import com.diphot.siu.persistence.InspeccionDAO;
import com.diphot.siu.services.restlet.InspeccionRestLetInterface;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import android.content.Context;

public class InspeccionSenderService extends AbstractService implements Runnable{

	private static Context context;
	private static InspeccionSenderService instance;

	private InspeccionSenderService (){
	}

	public static InspeccionSenderService getInstance(Context context){
		if (instance != null){
			return instance;
		} else {
			InspeccionSenderService.context = context;
			instance = new InspeccionSenderService();
			return instance;
		}
	}

	@Override
	public void run() {
		LinkChecker linkChecker = LinkChecker.getInstance(context);
		InspeccionDAO idao = new InspeccionDAO(context);
		InspeccionDTO idto;
		while(this.running){
			while(true){
				if (linkChecker.linkOK()){
					try {
						idto = idao.getNotSent();
						if ( idto != null){
							InspeccionRestLetInterface resource = WebServiceFactory.getInspeccionRestLetInterface();
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
						pause(1);
					}
				}
			}
		}
	}
}
