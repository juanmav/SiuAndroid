package com.diphot.siu.services.deprecated;

import java.util.ArrayList;

import com.diphot.siu.Json.JsonListService;
import com.diphot.siu.connection.deprecated.ConnectionChecker;
import com.diphot.siu.custom.ConsoleOnScreen;
import com.diphot.siuweb.shared.dtos.AreaDTO;
import com.diphot.siuweb.shared.dtos.TemaDTO;
import com.diphot.siuweb.shared.dtos.TipoRelevamientoDTO;
import com.google.gson.reflect.TypeToken;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

@Deprecated
public class DataBaseSincro extends Service{

	private JsonListService<AreaDTO> jsonserviceArea;
	private JsonListService<TipoRelevamientoDTO> jsonserviceTipoRelevamiento;
	private JsonListService<TemaDTO> jsonserviceTema;


	/** Called when the service is being created. */
	@Override
	public void onCreate() {
		ConsoleOnScreen.addText("My DataBaseSincro Created");
		//Toast.makeText(this, "My DataBaseSincro Created", Toast.LENGTH_LONG).show();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		ConsoleOnScreen.addText( "DataBaseSincro Service Started");
		//Toast.makeText(getApplicationContext(), "DataBaseSincro Service Started", Toast.LENGTH_LONG).show();
		new ConnectionChecker(this) {

			@Override
			public void HostOK() {
				jsonserviceTema = new JsonListService<TemaDTO>(new TemaDTO(), new TypeToken<ArrayList<TemaDTO>>(){}.getType(),getApplicationContext()){
					@Override
					protected void sincronized() {
						ConsoleOnScreen.addText("Sincronizacion DB EXITOSA");
					}
				};
				jsonserviceTipoRelevamiento = new JsonListService<TipoRelevamientoDTO>(new TipoRelevamientoDTO(), new TypeToken<ArrayList<TipoRelevamientoDTO>>(){}.getType(),getApplicationContext()){
					@Override
					protected void sincronized() {
						jsonserviceTema.getList();

					}
				};
				jsonserviceArea = new JsonListService<AreaDTO>(new AreaDTO(), new TypeToken<ArrayList<AreaDTO>>(){}.getType(),getApplicationContext()){
					@Override
					protected void sincronized() {
						jsonserviceTipoRelevamiento.getList();	
					}
				};
				jsonserviceArea.getList();
			}

			@Override
			public void HostNotOK() {
				ConsoleOnScreen.addText("No se puede encontrar el HOST");
				//Toast.makeText(getApplicationContext(), "No se puede encontrar el HOST", Toast.LENGTH_LONG).show();	
			}

			@Override
			public void wifiNotOK() {
				// TODO Auto-generated method stub

			}
		}.execute("");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		ConsoleOnScreen.addText("DataBase Sincro Service Stopped");
		//Toast.makeText(getApplicationContext(), "Service Stopped", Toast.LENGTH_LONG).show();
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
