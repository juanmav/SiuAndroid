package com.diphot.siu.services.deprecated;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import com.diphot.siu.Json.JsonAdapter;
import com.diphot.siu.Json.JsonAdapter.ACTION;
import com.diphot.siu.connection.LinkChecker;
import com.diphot.siu.custom.ConsoleOnScreen;
import com.diphot.siu.persistence.DAOFactory;
import com.diphot.siu.persistence.DAOInterface;
import com.diphot.siu.views.SiuConstants;
import com.diphot.siuweb.shared.dtos.AreaDTO;
import com.diphot.siuweb.shared.dtos.InterfaceDTO;
import com.diphot.siuweb.shared.dtos.TemaDTO;
import com.diphot.siuweb.shared.dtos.TipoRelevamientoDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import android.content.Context;

@Deprecated
public class DataBaseSincroService implements Runnable{

	private static DataBaseSincroService instance;
	private static Context context;

	private DataBaseSincroService() {

	}

	public static DataBaseSincroService getInstance(Context context){
		if (instance != null){
			return instance;
		} else {
			DataBaseSincroService.context = context;
			instance = new DataBaseSincroService();
			return instance;
		}
	}

	@Override
	public void run() {
		LinkChecker linkChecker = LinkChecker.getInstance(context);
		while (true){
			if (linkChecker.linkOK()){
				if (checkDBVersion()){
					ConsoleOnScreen.addText("Sincronizando Areas");
					if (getSincronize(new AreaDTO(), new TypeToken<ArrayList<AreaDTO>>(){}.getType())){
						ConsoleOnScreen.addText("Sincronizando Tipos Relevamietnos");
						if (getSincronize(new TipoRelevamientoDTO(), new TypeToken<ArrayList<TipoRelevamientoDTO>>(){}.getType())){
							ConsoleOnScreen.addText("Sincronizando Temas");
							getSincronize(new TemaDTO(), new TypeToken<ArrayList<TemaDTO>>(){}.getType());
						};
					}
				}
			}
			ConsoleOnScreen.addText("Esperando cambios en la DB");
			pause();
		}
	}

	private void pause(){
		try {
			Thread.sleep(60 * 60 * 1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		} 
	}

	
	public Boolean checkDBVersion(){
		// TODO checkear la version de la DB contra el servidor.
		return true;
	}

	public Boolean getSincronize(InterfaceDTO dto, Type type) {
		GsonBuilder builder = new GsonBuilder();
		JsonAdapter adapter = new JsonAdapter(ACTION.LIST);
		builder.registerTypeAdapter(InterfaceDTO.class,adapter);
		Gson gson = builder.create();
		String jsonDTO = gson.toJson(dto, InterfaceDTO.class);
		System.out.println("Pedido: ");
		System.out.println(jsonDTO);
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
			ArrayList<InterfaceDTO> list = gson.fromJson(respuestaString,type);
			DAOInterface<InterfaceDTO> dao = DAOFactory.getDAOImpl(list.get(0), context);
			dao.massiveCreate(list);
			pude = true;
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
