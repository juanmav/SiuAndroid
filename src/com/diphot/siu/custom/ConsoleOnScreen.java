package com.diphot.siu.custom;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;

public class ConsoleOnScreen extends EditText{
	
	private static ConsoleOnScreen instance;
	private static int count = 0;
	private static Context context;
	
	private ConsoleOnScreen(Context context) {
		super(context);
		ConsoleOnScreen.context = context;
	}
	
	public static ConsoleOnScreen getInstance(Context context){
		if (instance == null){
			instance = new ConsoleOnScreen(context);
			instance.setEnabled(false);
			instance.setMaxLines(15);
			return instance;
		} else {
			return instance;
		}
	}
	
	public static synchronized void addText(final String string){
		if (instance != null){
			Activity a = (Activity)context;
			count++;
			final int moment = count;
			a.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					instance.append(moment + " " + string + System.getProperty("line.separator"));
				}
			});
		}
	}
}
