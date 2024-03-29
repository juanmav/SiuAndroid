package com.diphot.siu.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.diphot.siu.Login;
import com.diphot.siu.R;
import com.diphot.siu.SiuConstants;
import com.diphot.siu.UserContainer;
import com.diphot.siu.services.AuditTaskSincroService;
import com.diphot.siu.services.AuditoriaSenderService;
import com.diphot.siu.services.InspeccionSenderService;
import com.diphot.siu.services.TipificacionSincroService;
import com.diphot.siu.views.auditorias.AuditoriaCreate;
import com.diphot.siu.views.auditorias.InspeccionToAuditarList;
import com.diphot.siuweb.shared.dtos.RoleDTO;

public class MainScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		// Agrego la consola
		//LinearLayout ll = (LinearLayout) this.findViewById(R.id.mainscreenLinerLayout);
		//ll.addView(ConsoleOnScreen.getInstance(this));

		
		if (UserContainer.isUserOnline()){
			// TODO volver activar los servicios de sincronizacion
			this.startSincroServices();
		} else {
			// Apago el Boton de Inspecciones y solo dejo Crear.
			Button inspeccionesbtn = (Button) this.findViewById(R.id.btn_verListaIns);
			inspeccionesbtn.setVisibility(View.INVISIBLE);
		}
		
		restricRole();
		
	}

	private void restricRole(){
		RoleDTO role = UserContainer.getUserDTO().getRolesDTO().get(0);
		if (role.equals(new RoleDTO(SiuConstants.ROLES.ADMIN))){
			// Dejo todos los botones.
		} else if (role.equals(new RoleDTO(SiuConstants.ROLES.INSPECTOR))) {
			// Dejo todos los botones.
		} else if (role.equals(new RoleDTO(SiuConstants.ROLES.SUPERVISOR))) {
			// Oculto el boton de creaci�n.
			Button btn_crearIns = (Button) findViewById(R.id.btn_crearIns);
			btn_crearIns.setVisibility(View.INVISIBLE);
		} else if (role.equals(new RoleDTO(SiuConstants.ROLES.SECRETARIA))) {
			// Oculto el boton de creaci�n.
			Button btn_crearIns = (Button) findViewById(R.id.btn_crearIns);
			btn_crearIns.setVisibility(View.INVISIBLE);
			// Oculto el boton de AuditTask
			Button auTODO_btn = (Button) findViewById(R.id.auTODO_btn);
			auTODO_btn.setVisibility(View.INVISIBLE);
		}
	}
	
	private void startSincroServices(){
		// TODO Descomentar
		// TODO isAlive para ver que solo este un hilo de ejecuccion.

		TipificacionSincroService tss = TipificacionSincroService.getInstance(this);
		Thread ttsThread = new Thread(tss);
		ttsThread.start();
		
		InspeccionSenderService iss = InspeccionSenderService.getInstance(this);
		Thread issThread =new Thread(iss); 
		issThread.start();
		
		AuditTaskSincroService atss = AuditTaskSincroService.getInstance(this);
		Thread atssThread = new Thread(atss);
		atssThread.start();
		
		AuditoriaSenderService auss = AuditoriaSenderService.getInstance(this);
		Thread aussThread = new Thread(auss);
		aussThread.start();
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

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_screen, menu);
		return true;
	}*/

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
			new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle("Salir")
			.setMessage("Realmente desea salir?")
			.setPositiveButton("Si", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(getApplicationContext(), Login.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent); 
				}
			}).setNegativeButton("No", null).show();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	/*public void verAuLista(View view){
		Intent intent = new Intent (MainScreen.this, AuditoriaCreate.class);
		startActivity(intent);
	}*/
	
	public void auditTODO(View view){
		Intent intent = new Intent (MainScreen.this, InspeccionToAuditarList.class);
		startActivity(intent);
	}
}
