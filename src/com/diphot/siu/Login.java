package com.diphot.siu;

import com.diphot.siu.R;
import com.diphot.siu.views.MainScreen;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;

public class Login extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void login (View view){
		Intent intent = new Intent(Login.this, MainScreen.class);
		startActivity(intent); 
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    this.finish();
	    return super.onKeyDown(keyCode, event);
	}
}
