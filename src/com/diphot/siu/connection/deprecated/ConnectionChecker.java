package com.diphot.siu.connection.deprecated;

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

import com.diphot.siu.views.SiuConstants;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

// Los genericos que se completan son para las siguientes funciones/metodos.
// Tipo entrada execute, algo mas, tipo entrada postresult.
@Deprecated
public abstract class ConnectionChecker extends AsyncTask<String, String, Boolean[]>{

	private Context context;
	
	public ConnectionChecker(Context context) {
		this.context = context;
		
	} 

	@Override
	protected Boolean[] doInBackground(String... arg0) {
		ConnectivityManager connManager = (ConnectivityManager) this.context.getSystemService(this.context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		Boolean[] result = {false, false};
		if (mWifi.isConnected()) {
			result[0] = true;
			try {
				HttpParams params = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(params, 10000);
				HttpConnectionParams.setSoTimeout(params, 15000);

				DefaultHttpClient httpclient = new DefaultHttpClient(params);
				HttpGet httpget = new HttpGet(URI.create(SiuConstants.URL_BACKED));
				httpclient.execute(httpget);
				HttpResponse response = httpclient.execute(httpget);
				String respuestaString = EntityUtils.toString(response.getEntity());
				System.out.println(respuestaString);
				// Si llega a esta linea el servidor se encuentra activo del otro lado
				result[1] = true;
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	protected void onPostExecute(Boolean[] result) {
		if (result[0]){
			if (result[1]){
				HostOK();
			} else {
				HostNotOK();
			}
		} else {
			wifiNotOK();
		}
	}

	public abstract void HostOK();
	public abstract void HostNotOK();
	public abstract void wifiNotOK();

}
