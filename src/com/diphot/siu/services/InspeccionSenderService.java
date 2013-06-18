package com.diphot.siu.services;

import org.restlet.resource.ClientResource;

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
						ClientResource cr = new ClientResource(InspeccionRestLetInterface.URL);
						InspeccionRestLetInterface resource = cr.wrap(InspeccionRestLetInterface.class);
						idto = idao.getNotSended();
						if ( idto != null){
							resource.create(idto);
							idao.updateToSended(idto.getId());
						}
					} finally{
						pause(1);
					}
				}
			}
		}
	}

	
}
