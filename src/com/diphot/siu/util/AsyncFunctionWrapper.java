package com.diphot.siu.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

public class AsyncFunctionWrapper {

	private Context context;
	
	public AsyncFunctionWrapper(Context context){
		this.context = context;
	}

	public interface Callable {
		public static Integer OK = 0 ; 
		public static Integer NOTOK = 1 ;
		public Integer call();
	}
	
	public void execute(final String title, final String message, final Callable callable, final Callable callableOK){
		
		AsyncTask<String, String, Integer> alogin = new AsyncTask<String, String, Integer>(){

			private ProgressDialog pd;
			@Override
			protected void onPreExecute() {
				pd = new ProgressDialog(AsyncFunctionWrapper.this.context);
				pd.setTitle(title);
				pd.setMessage(message);
				pd.setCancelable(false);
				pd.setIndeterminate(true);
				pd.show();
			}
			
			@Override
			protected Integer doInBackground(String... params) {
				return callable.call();
			}
			
			@Override
			protected void onPostExecute(Integer result){
				if (result == Callable.OK){
					pd.dismiss();
					callableOK.call();
				} else {
					pd.dismiss();
					new AlertDialog.Builder(AsyncFunctionWrapper.this.context)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle("ERROR")
					.setMessage("No se puede contactar al servidor en este momento")
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					}).show();
				}
			}
		};
		
		alogin.execute("");
	}
	
}
