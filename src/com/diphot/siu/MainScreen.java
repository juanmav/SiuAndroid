package com.diphot.siu;

import com.diphot.siu.model.JsonService;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MainScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_screen, menu);
		return true;
	}
	
	public void addName(View view) {
		System.out.println("Apretaron el boton");
		InspeccionDTO idto = new InspeccionDTO();
		idto.setCalle("Android Calle");
		idto.setAltura(1000);
		
		System.out.println("Creacion Realizada");
		
	}

}
