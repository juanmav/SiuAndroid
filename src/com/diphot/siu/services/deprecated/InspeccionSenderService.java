package com.diphot.siu.services.deprecated;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import android.content.Context;
import com.diphot.siu.Json.JsonAdapter;
import com.diphot.siu.Json.JsonAdapter.ACTION;
import com.diphot.siu.connection.LinkChecker;
import com.diphot.siu.custom.ConsoleOnScreen;
import com.diphot.siu.persistence.InspeccionDAO;
import com.diphot.siu.views.SiuConstants;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import com.diphot.siuweb.shared.dtos.InterfaceDTO;
import com.diphot.siuweb.shared.dtos.PostResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class InspeccionSenderService implements Runnable {

	private static InspeccionSenderService instance;
	private static Context context;

	private InspeccionSenderService() {

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
		while (true) {
			// Mientras el link este OK
			InspeccionDTO idto;
			ConsoleOnScreen.addText("Verificando si hay Inspecciones para Enviar");
			do {
				idto = idao.getNotSended();
				if (idto != null && linkChecker.linkOK()){
 					if (send(idto)){
						// Si pude enviar actualizo
						ConsoleOnScreen.addText("Se envio al servidor inspeccion: " + idto.getId());
						idao.updateToSended(idto.getId());
					} else {
						// Si tuve un problema corto el while y espero.
						break;
					}
				} else {
					ConsoleOnScreen.addText("No hay inspecciones para enviar o el Servidor no esta disponible");
					break;
				}
			} while (idto != null);
			ConsoleOnScreen.addText("Esperando nuevas Inspecciones para enviar");
			pause();
		}
	}

	private void pause(){
		try {
			Thread.sleep(60 * 1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		} 
	}

	private boolean send(InspeccionDTO dto){
		GsonBuilder builder = new GsonBuilder();
		JsonAdapter adapter = new JsonAdapter(ACTION.PUT);
		builder.registerTypeAdapter(InterfaceDTO.class,adapter);
		Gson gson = builder.create();
		String jsonDTO = gson.toJson(dto, InterfaceDTO.class);
		System.out.println("Pedido: ");
		System.out.println(jsonDTO);
		Type type = new TypeToken<PostResult>(){}.getType();
		Boolean pude = false; 
		try {
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, 10000);
			HttpConnectionParams.setSoTimeout(params, 15000);

			DefaultHttpClient httpclient = new DefaultHttpClient(params);
			HttpPost httpost = new HttpPost(URI.create(SiuConstants.URL_BACKEND));
			StringEntity se = new StringEntity(jsonDTO);
			httpost.setEntity(se);
			HttpResponse response = httpclient.execute(httpost);
			String respuestaString = EntityUtils.toString(response.getEntity());
			PostResult postResult = gson.fromJson(respuestaString,type);
			if (postResult.getResult() == PostResult.Result.OK){
				pude = true;	
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pude;
	}
}
