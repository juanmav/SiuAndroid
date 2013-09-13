package com.diphot.siu;

import com.diphot.siu.R;
import com.diphot.siu.services.WebServiceFactory;
import com.diphot.siu.services.restlet.UserRestLetInterface;
import com.diphot.siu.views.MainScreen;
import com.diphot.siuweb.shared.dtos.UserDTO;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Login extends Activity {

	private EditText userText;
	private EditText passwordText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		this.userText = (EditText) this.findViewById(R.id.userText);
		this.passwordText = (EditText) this.findViewById(R.id.passwordText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	// TODO Async Task.
	public void login (View view){
		AsyncTask<String, String, Integer> alogin = new AsyncTask<String, String, Integer>(){

			private ProgressDialog pd;
			@Override
			protected void onPreExecute() {
				pd = new ProgressDialog(Login.this);
				pd.setTitle("Login...");
				pd.setMessage("Please wait.");
				pd.setCancelable(false);
				pd.setIndeterminate(true);
				pd.show();
			}
			@Override
			protected Integer doInBackground(String... params) {
				Integer result = 0;
				Intent intent = new Intent(Login.this, MainScreen.class);
				UserRestLetInterface resource = WebServiceFactory.getUserRestLetInterface();
				String username = userText.getText().toString();
				String password = passwordText.getText().toString();
				UserDTO u = new UserDTO(0L, username, password);
				try {
					u = resource.login(u);
					if (u !=null){
						UserContainer.setUserDTO(u);
						startActivity(intent);
					} else {
						result = 1;
					}
				} catch (Exception e){
					e.printStackTrace();
					result = 1;

				}
				return result;
			}
			@Override
			protected void onPostExecute(Integer result){
				if (result == 0){
					pd.dismiss();
				} else {
					pd.dismiss();
					new AlertDialog.Builder(Login.this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle("ERROR")
					.setMessage("No se puede contactar al servidor en este momento, intente en unos minutos.")
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


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
}
