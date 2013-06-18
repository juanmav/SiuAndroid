package com.diphot.siu;

import java.util.ArrayList;

import org.restlet.resource.ClientResource;
import com.diphot.siu.custom.ConsoleOnScreen;
import com.diphot.siu.services.TipificacionSincroService;
import com.diphot.siu.services.InspeccionSenderService;
import com.diphot.siu.services.restlet.TipificacionRestLetInterface;
import com.diphot.siu.views.SelectionController;
import com.diphot.siu.views.inspecciones.InspeccionList;
import com.diphot.siuweb.shared.dtos.AreaDTO;
import com.diphot.siuweb.shared.dtos.TemaDTO;
import com.diphot.siuweb.shared.dtos.TipoRelevamientoDTO;
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
		/*ConsoleOnScreen.addText("Genere un Hilo - Sincro Inspecciones");
		DataBaseSincroService dbs = DataBaseSincroService.getInstance(this);
		new Thread(dbs).start();
		ConsoleOnScreen.addText("Genere un Hilo - Sincro DB");
		InspeccionSenderService s = InspeccionSenderService.getInstance(this);
		new Thread(s).start();*/
		InspeccionSenderService iss = InspeccionSenderService.getInstance(this);
		new Thread(iss).start();
		
		TipificacionSincroService tss = TipificacionSincroService.getInstance(this);
		new Thread(tss).start();
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
	protected void onDestroy(){
		LinearLayout ll = (LinearLayout) this.findViewById(R.id.mainscreenLinerLayout);
		ll.removeAllViews();
		super.onDestroy();
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
	
	// Workaround for GAE servers to prevent chunk encoding
	//cr.setRequestEntityBuffering(true);
	public void testRest(View view){
		/*ClientResource cr = new ClientResource("http://192.168.0.132:8888/rest/inspecciones");
		InspeccionRestLetInterface resource = cr.wrap(InspeccionRestLetInterface.class);
		System.out.println(resource.getInspeccionDTO());
		resource.store("asdasdasd");*/
		try {
			//ClientResource cr2 = new ClientResource("http://192.168.0.133:8888/rest/tipificacion");
			ClientResource cr2 = new ClientResource(TipificacionRestLetInterface.URL);
			TipificacionRestLetInterface resource2 = cr2.wrap(TipificacionRestLetInterface.class);
			ArrayList<AreaDTO> a = resource2.getAreas();
			ArrayList<TipoRelevamientoDTO> t = resource2.getTiposRelevamiento();
			ArrayList<TemaDTO> tt = resource2.getTemas();
			System.out.println(a);
			System.out.println(t);
			System.out.println(tt);
		}catch (Exception e){	
		
		} finally {
			
		}
	}
	
	public void testRPC (View view){
		
	}
}
