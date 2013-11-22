package com.diphot.siu.services;

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
		// TODO Auto-generated method stub
		
	}

	
}
