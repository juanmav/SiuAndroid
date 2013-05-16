package com.diphot.siu.services.deprecated;

import java.util.ArrayList;

import com.diphot.siu.Json.JsonCreateService;
import com.diphot.siu.connection.deprecated.ConnectionChecker;
import com.diphot.siu.custom.ConsoleOnScreen;
import com.diphot.siu.persistence.InspeccionDAO;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import com.google.gson.reflect.TypeToken;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

@Deprecated
public class InspeccionSincro extends Service{

	/* Called when the service is being created. */
	@Override
	public void onCreate() {
		Toast.makeText(this, "InspeccionSincro Service Created", Toast.LENGTH_LONG).show();
	}
	
	JsonCreateService<InspeccionDTO> jsonserviceInspeccion;
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		ConsoleOnScreen.addText("InspeccionSincro Service Started");
		//Toast.makeText(this, "InspeccionSincro Service Started", Toast.LENGTH_LONG).show();
		new ConnectionChecker(this) {
			@Override
			public void wifiNotOK() {
				ConsoleOnScreen.addText("No esta conectado el WIFI");
				//Toast.makeText(getApplicationContext(), "No esta conectado el WIFI", Toast.LENGTH_LONG).show();	
			}
			@Override
			public void HostOK() {
				jsonserviceInspeccion = new JsonCreateService<InspeccionDTO>(new InspeccionDTO(), new TypeToken<ArrayList<InspeccionDTO>>(){}.getType(),getApplicationContext()){
					@Override
					public void sended() {
						// Sigo tratando de enviar.
						enviarInspeccion();
					}
					@Override
					public void failtoSend() {
						ConsoleOnScreen.addText("Se detuvo la sincronizacion");
						//Toast.makeText(getApplicationContext(), "Se detuvo la sincronizacion", Toast.LENGTH_LONG).show();
					}
				};
				// Disparo el primer envio
				// Esto lo que va hacer es llamar al metodo send del servicio
				// cuando el servicio termine de enviar la inspeccion
				// llama al metodo sended que vuelve a disparar un envio.
				enviarInspeccion();
			}

			@Override
			public void HostNotOK() {
				ConsoleOnScreen.addText("No se puede encontrar el HOST");
				//Toast.makeText(getApplicationContext(), "No se puede encontrar el HOST", Toast.LENGTH_LONG).show();
			}
		}.execute("");
		return START_STICKY;
	}

	private void enviarInspeccion(){
		InspeccionDAO idao = new InspeccionDAO(getApplicationContext());
		InspeccionDTO idto = idao.getNotSended();
		if (idto != null ){
			ConsoleOnScreen.addText("Enviando Inspeccion: " + idto.getId());
			jsonserviceInspeccion.send(idto);
		} else {
			ConsoleOnScreen.addText("No hay mas inspecciones para Sincronizar");
			//Toast.makeText(getApplicationContext(), "No hay mas inspecciones para Sincronizar", Toast.LENGTH_LONG).show();
		}
	}
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
