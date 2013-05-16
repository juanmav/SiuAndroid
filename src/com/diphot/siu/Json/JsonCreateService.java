package com.diphot.siu.Json;

import java.io.IOException;
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
import android.os.AsyncTask;
import android.widget.Toast;

import com.diphot.siu.Json.JsonAdapter.ACTION;
import com.diphot.siu.persistence.InspeccionDAO;
import com.diphot.siu.views.SiuConstants;
import com.diphot.siuweb.shared.dtos.InterfaceDTO;
import com.diphot.siuweb.shared.dtos.PostResult;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Deprecated
public abstract class JsonCreateService<O extends InterfaceDTO> extends AsyncTask<String, String, String>{

	private Gson gson;
	private Type type;
	private O dto;
	private Context context;

	public JsonCreateService(O dto, Type listType, Context context){
		this.type = listType;
		this.dto = dto;
		this.context = context;
	}

	public void send(O objeto) {
		GsonBuilder builder = new GsonBuilder();
		JsonAdapter adapter = new JsonAdapter(ACTION.PUT);
		builder.registerTypeAdapter(InterfaceDTO.class,adapter);
		gson = builder.create();
		String jsonDTO = gson.toJson(objeto, InterfaceDTO.class);
		System.out.println("Pedido: ");
		System.out.println(jsonDTO);
		this.type = new TypeToken<PostResult>(){}.getType();
		this.execute(jsonDTO,objeto.getId().toString());
	}

	@Override
	protected String doInBackground(String... jsons) {
		String respuestaString = "";
		try {
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, 10000);
			HttpConnectionParams.setSoTimeout(params, 15000);

			DefaultHttpClient httpclient = new DefaultHttpClient(params);
			HttpPost httpost = new HttpPost(URI.create(SiuConstants.URL_BACKED));
			StringEntity se = new StringEntity(jsons[0]);
			httpost.setEntity(se);
			HttpResponse response = httpclient.execute(httpost);
			respuestaString = EntityUtils.toString(response.getEntity());
			PostResult postResult = gson.fromJson(respuestaString,type);
			if (postResult.getResult() == PostResult.Result.OK){
				new InspeccionDAO(context).updateToSended(Long.valueOf((jsons[1])));
				respuestaString = "OK";
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respuestaString;
	}

	@Override
	protected void onPostExecute(String result) {
		if (result == ""){
			Toast.makeText(context.getApplicationContext(), "FALLO!", Toast.LENGTH_SHORT).show();
			failtoSend();
		} else {
			Toast.makeText(context.getApplicationContext(), "ENVIADO!", Toast.LENGTH_SHORT).show();
			sended();
		}
		
	}
	
	public abstract void sended();
	public abstract void failtoSend();
}

