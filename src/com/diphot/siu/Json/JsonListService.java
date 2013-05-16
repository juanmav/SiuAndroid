package com.diphot.siu.Json;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Observable;

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
import com.diphot.siu.persistence.DAOFactory;
import com.diphot.siu.persistence.DAOInterface;
import com.diphot.siu.views.SiuConstants;
import com.diphot.siuweb.shared.dtos.InterfaceDTO;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Deprecated
public abstract class JsonListService<O extends InterfaceDTO> extends AsyncTask<String, String, String>{

	private Gson gson;
	private Type type;
	private O dto;
	private Context context;

	public JsonListService(O dto, Type listType, Context context){
		this.type = listType;
		this.dto = dto;
		this.context = context;
	}

	public void getList(){
		GsonBuilder builder = new GsonBuilder();
		JsonAdapter adapter = new JsonAdapter(ACTION.LIST);
		builder.registerTypeAdapter(InterfaceDTO.class,adapter);
		gson = builder.create();
		String jsonDTO = gson.toJson(dto, InterfaceDTO.class);
		System.out.println("Pedido: ");
		System.out.println(jsonDTO);
		this.execute(jsonDTO);
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
			ArrayList<InterfaceDTO> list = gson.fromJson(respuestaString,type);
			DAOInterface<InterfaceDTO> dao = DAOFactory.getDAOImpl(list.get(0), context);
			dao.massiveCreate(list);
			sincronized();
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
		} else {
			Toast.makeText(context.getApplicationContext(), "Sincronizado!", Toast.LENGTH_SHORT).show();
		}
	}
	
	protected abstract void sincronized();
}
