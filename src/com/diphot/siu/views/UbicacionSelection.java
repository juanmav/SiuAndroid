package com.diphot.siu.views;

import java.io.IOException;
import java.util.List;

import com.diphot.siu.Login;
import com.diphot.siu.R;
import com.diphot.siu.SiuConstants;
import com.diphot.siu.persistence.LocalidadDAO;
import com.diphot.siu.views.adapters.LocalidadAdapter;

import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UbicacionSelection extends Activity implements LocationListener {
	private TextView latituteField;
	private TextView longitudeField;
	private LocationManager locationManager;
	private EditText calle;
	private EditText altura;
	private String provider;
	private Spinner localidades;
	private LocalidadAdapter ladapter;
	private TextView dirSugerida;
	private TextView calle1;
	private TextView calle2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ubicacion_selection);
		activeGPS();
		latituteField = (TextView) findViewById(R.id.TextView01);
		longitudeField = (TextView) findViewById(R.id.TextView02);
		calle = (EditText) findViewById(R.id.calle);
		altura = (EditText)findViewById(R.id.altura);
		dirSugerida = (TextView) findViewById(R.id.dirSugerida);

		localidades = (Spinner) findViewById(R.id.localidades);
		ladapter = new LocalidadAdapter(this, new LocalidadDAO(this).getList());
		localidades.setAdapter(ladapter);
		
		calle1 = (EditText) findViewById(R.id.calle1); 
		calle2 = (EditText) findViewById(R.id.calle2);
		
		// Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// Define the criteria how to select the locatioin provider -> use
		// default
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);

		// Initialize the location fields
		if (location != null) {
			System.out.println("Provider " + provider + " has been selected.");
			onLocationChanged(location);
		} else {
			latituteField.setText("0.0");
			longitudeField.setText("0.0");
		}
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ubicacion_selection, menu);
		return true;
	}*/

	private void activeGPS(){
		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = service
				.isProviderEnabled(LocationManager.GPS_PROVIDER);

		// Check if enabled and if not send user to the GSP settings
		// Better solution would be to display a dialog and suggesting to 
		// go to the settings
		if (!enabled) {
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
		} 
	}

	/* Request updates at startup */
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}

	/* Remove the locationlistener updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		latituteField.setText(String.valueOf(location.getLatitude()));
		longitudeField.setText(String.valueOf(location.getLongitude()));
		try {
			Geocoder geoCoder = new Geocoder(this);
			List<Address> matches;
			matches = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
			Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
			if (bestMatch != null){
				System.out.print("Direccion: " + bestMatch.getAddressLine(0));
				this.dirSugerida.setText(bestMatch.getAddressLine(0));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Enabled new provider " + provider, Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Disabled provider " + provider, Toast.LENGTH_SHORT).show();
	}

	public void salvar(View view){
		if (validateForm()){
			Intent returnIntent = new Intent();
			returnIntent.putExtra(SiuConstants.CALLE_PROPERTY,calle.getText().toString());
			returnIntent.putExtra(SiuConstants.ALTURA_PROPERTY,altura.getText().toString());
			returnIntent.putExtra(SiuConstants.LATITUDE_PROPERTY,latituteField.getText().toString());
			returnIntent.putExtra(SiuConstants.LONGITUDE_PROPERTY,longitudeField.getText().toString());
			returnIntent.putExtra(SiuConstants.LOCALIDAD_PROPERTY, this.ladapter.getItem(localidades.getSelectedItemPosition()).getId());
			returnIntent.putExtra(SiuConstants.ENTRE_CALLE_UNO, calle1.getText().toString());
			returnIntent.putExtra(SiuConstants.ENTRE_CALLE_DOS, calle2.getText().toString());
			setResult(RESULT_OK,returnIntent);        
			finish();
		}
	}
	
	private Boolean validateForm(){
		Boolean result = true;
		if ( "".equalsIgnoreCase(calle.getText().toString())){
			new AlertDialog.Builder(UbicacionSelection.this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle("ERROR")
			.setMessage("Debe Completar el campo Calle")
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			}).show();
			result = false;
		}
		return result;
	}
	
}
