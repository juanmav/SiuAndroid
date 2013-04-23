package com.diphot.siu.Json;

import java.io.IOException;
import java.net.URI;
import java.util.Observable;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import android.os.AsyncTask;
import com.diphot.siu.Json.JsonAdapter.ACTION;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import com.diphot.siuweb.shared.dtos.InterfaceDTO;
import com.diphot.siuweb.shared.dtos.PostResult;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonCreateService<O extends InterfaceDTO> extends Observable{

	private static String url = "http://192.168.0.113:8888/mobileendpointService";
	private Gson gson;
	private Type type;
	private O dto;

	public JsonCreateService(O dto, Type listType){
		this.type = listType;
		this.dto = dto;
	}

	public void create(InterfaceDTO objeto) {
		GsonBuilder builder = new GsonBuilder();
		JsonAdapter adapter = new JsonAdapter(ACTION.PUT);
		builder.registerTypeAdapter(InterfaceDTO.class,adapter);
		gson = builder.create();
		String jsonDTO = gson.toJson(objeto, InterfaceDTO.class);
		System.out.println("Pedido: ");
		System.out.println(jsonDTO);
		// Cambio el Type.
		this.type = new TypeToken<PostResult>(){}.getType();
		new JsonServiceAsync().execute(jsonDTO,((InspeccionDTO)objeto).getId().toString());
	}

	private class JsonServiceAsync extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... jsons) {
			String respuestaString = "";
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httpost = new HttpPost(URI.create(url));
				StringEntity se = new StringEntity(jsons[0]);
				httpost.setEntity(se);
				HttpResponse response = httpclient.execute(httpost);
				respuestaString = EntityUtils.toString(response.getEntity());
				//System.out.println("Respuesta: ");
				//System.out.println(respuestaString);
				setChanged();
				Object[] objetos = new Object[2];
				objetos[0] = gson.fromJson(respuestaString,type);
				objetos[1] = jsons[1];
				notifyObservers(objetos);
				// Devuelvo el viejo type, si es que lo cambie.
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return respuestaString;
		}
	}
}
