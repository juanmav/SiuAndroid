package com.diphot.siu.model;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

import com.diphot.siu.Json.JsonAdapter;
import com.diphot.siu.Json.JsonAdapter.ACTION;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import com.diphot.siuweb.shared.dtos.InterfaceDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonService {

	private static String url = "http://192.168.0.113:8888/mobileendpointService";
	
	public ArrayList<InterfaceDTO> getList(InterfaceDTO dto){
		return null;
	}
	
	public void createInspeccion(InspeccionDTO dto) {
		Gson gson;
		GsonBuilder builder = new GsonBuilder();
		JsonAdapter adapter = new JsonAdapter(ACTION.PUT);
		builder.registerTypeAdapter(InterfaceDTO.class,adapter);
		gson = builder.create();
		String jsonDTO = gson.toJson(dto, InterfaceDTO.class);
		System.out.println("Pedido: ");
		System.out.println(jsonDTO);
		new JsonServiceAsync().execute(jsonDTO);
		//System.out.println(stringResponse);
	}
	
	private class JsonServiceAsync extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... jsons) {
			String respuesta = "";
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httpost = new HttpPost(URI.create(url));
				StringEntity se = new StringEntity(jsons[0]);
				httpost.setEntity(se);
				HttpResponse response = httpclient.execute(httpost);
				respuesta = EntityUtils.toString(response.getEntity());
				System.out.println("Respuesta: ");
				System.out.println(respuesta);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return respuesta;
		}
	}
	
}
