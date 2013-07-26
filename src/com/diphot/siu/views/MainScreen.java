package com.diphot.siu.views;

import java.util.ArrayList;

import org.restlet.Client;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import com.diphot.siu.Login;
import com.diphot.siu.R;
import com.diphot.siu.SiuConstants;
import com.diphot.siu.services.InspeccionSenderService;
import com.diphot.siu.services.TipificacionSincroService;
import com.diphot.siu.services.restlet.InspeccionRestLetInterface;
import com.diphot.siu.views.inspecciones.InspeccionList;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import com.diphot.siuweb.shared.dtos.filters.InspeccionFilterDTO;

public class MainScreen extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		// Agrego la consola
		//LinearLayout ll = (LinearLayout) this.findViewById(R.id.mainscreenLinerLayout);
		//ll.addView(ConsoleOnScreen.getInstance(this));
		
		// TODO volver activar los servicios de sincronizacion
		this.startSincroServices();
	}

	private void startSincroServices(){
		//InspeccionSenderService iss = InspeccionSenderService.getInstance(this);
		//new Thread(iss).start();
		
		//TipificacionSincroService tss = TipificacionSincroService.getInstance(this);
		//new Thread(tss).start();
		
		ClientResource cr = new ClientResource(SiuConstants.URL_INSPECCIONES);
		Client client = new Client(Protocol.HTTP);
		cr.setNext(client);
		System.out.println(SiuConstants.URL_INSPECCIONES);
		cr.setRequestEntityBuffering(true);
		InspeccionRestLetInterface resource = cr.wrap(InspeccionRestLetInterface.class);
		InspeccionFilterDTO filter = new InspeccionFilterDTO();
		filter.riesgo = 1;
		filter.estadoID = 1;
		
		ArrayList<InspeccionDTO> dtos = resource.getList(filter);
		
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
		Intent intent = new Intent(MainScreen.this, InspeccionFilter.class);
		startActivity(intent);      
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    	Intent intent = new Intent(getApplicationContext(), Login.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	
	public void verAuLista(View view){
		Intent intent = new Intent (MainScreen.this, AuditoriaDetail.class);
		startActivity(intent);
	}
	
	// Workaround for GAE servers to prevent chunk encoding
	//cr.setRequestEntityBuffering(true);
	/*public void testRest(View view){
		try {
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
	}*/
	
	/*public void testRPC (View view){
		
	}*/
}
