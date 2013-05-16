package com.diphot.siu;

import com.diphot.siu.custom.ConsoleOnScreen;
import com.diphot.siu.services.DataBaseSincroService;
import com.diphot.siu.services.InspeccionSenderService;
import com.diphot.siu.views.SelectionController;
import com.diphot.siu.views.inspecciones.InspeccionList;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

public class MainScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		// Agrego la consola
		LinearLayout ll = (LinearLayout) this.findViewById(R.id.mainscreenLinerLayout);
		ll.addView(ConsoleOnScreen.getInstance(this));
		this.startSincroServices();
	}

	private void startSincroServices(){
		ConsoleOnScreen.addText("Genere un Hilo - Sincro Inspecciones");
		DataBaseSincroService dbs = DataBaseSincroService.getInstance(this);
		new Thread(dbs).start();
		ConsoleOnScreen.addText("Genere un Hilo - Sincro DB");
		InspeccionSenderService s = InspeccionSenderService.getInstance(this);
		new Thread(s).start();
	}
		
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume(){
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_screen, menu);
		return true;
	}
	
	public void createIns(View view){
		Intent intent = new Intent(MainScreen.this, SelectionController.class);
		startActivity(intent);      
	}
	
	public void verLista(View view){
		Intent intent = new Intent(MainScreen.this, InspeccionList.class);
		startActivity(intent);      
	}
}
