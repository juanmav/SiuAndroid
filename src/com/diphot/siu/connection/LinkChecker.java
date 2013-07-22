package com.diphot.siu.connection;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.diphot.siu.SiuConstants;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class LinkChecker {

	private static LinkChecker instance;
	private static Context context;
	
	private LinkChecker(){
		
	}
	
	public static LinkChecker getInstance(Context context){
		if (instance != null){
			return instance;
		} else {
			LinkChecker.context = context;
			instance = new LinkChecker();
			return instance;
		}
	}
	
	public static Boolean linkOK(){
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean result = false;		
		if (SiuConstants.debug == true){
			result = true;
		} else {
			if (mWifi.isConnected()) {
				try {
					HttpParams params = new BasicHttpParams();
					HttpConnectionParams.setConnectionTimeout(params, 10000);
					HttpConnectionParams.setSoTimeout(params, 15000);

					DefaultHttpClient httpclient = new DefaultHttpClient(params);
					HttpGet httpget = new HttpGet(URI.create(SiuConstants.HostIP));
					httpclient.execute(httpget);
					HttpResponse response = httpclient.execute(httpget);
					String respuestaString = EntityUtils.toString(response.getEntity());
					System.out.println(respuestaString);
					// Si llega a esta linea el servidor se encuentra activo del otro lado
					result = true;
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
				return result;
	}
}
