package com.diphot.siu.views.inspecciones;

import java.util.ArrayList;
import com.diphot.siu.R;
import com.diphot.siu.SiuConstants;
import com.diphot.siu.UserContainer;
import com.diphot.siu.services.WebServiceFactory;
import com.diphot.siu.services.restlet.AuditoriaRestLetInterface;
import com.diphot.siu.services.restlet.InspeccionRestLetInterface;
import com.diphot.siu.services.restlet.InspeccionRestLetInterfaceTwo;
import com.diphot.siu.util.AsyncFunctionWrapper;
import com.diphot.siu.util.AsyncFunctionWrapper.Callable;
import com.diphot.siu.util.Util;
import com.diphot.siu.views.auditorias.AuditoriaAdapter;
import com.diphot.siu.views.auditorias.AuditoriaCreate;
import com.diphot.siu.views.popup.ImagePopup;
import com.diphot.siuweb.shared.dtos.AreaDTO;
import com.diphot.siuweb.shared.dtos.AuditoriaDTO;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import com.diphot.siuweb.shared.dtos.RoleDTO;
import com.diphot.siuweb.shared.dtos.TemaDTO;
import com.diphot.siuweb.shared.dtos.TipoRelevamientoDTO;
import com.diphot.siuweb.shared.dtos.filters.AuditoriaFilterDTO;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class InspeccionDetail extends Activity {

	InspeccionDTO idto;

	TextView inspeccionID;
	TextView riesgoID;
	TextView tipificacionText;
	TextView estadoText;
	TextView calleText;
	TextView alturaText;
	TextView entreCallesText;
	ImageView img1;
	ImageView img2;
	ImageView img3;
	ImageView mapImg;
	TextView observacionText;
	ListView auditoriasList;

	AuditoriaAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inspeccion_detail);
		Bundle b = getIntent().getExtras();
		idto = (InspeccionDTO) b.getSerializable(SiuConstants.INSPECCION_PROPERTY);
		populateData();
		restricRole();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inspeccion_detail, menu);
		return true;
	}

	private void restricRole(){
		RoleDTO role = UserContainer.getUserDTO().getRolesDTO().get(0);
		/*confirmar_btn
		ejecutada_btn
		auditar_btn*/

		Button confirmar = (Button) this.findViewById(R.id.confirmar_btn);
		Button ejecutada = (Button) this.findViewById(R.id.ejecutada_btn);
		Button auditar = (Button) this.findViewById(R.id.auditar_btn);

		if (role.equals(new RoleDTO(SiuConstants.ROLES.ADMIN))){
			// Dejo todos los botones.
		} else if (role.equals(new RoleDTO(SiuConstants.ROLES.INSPECTOR))) {
			// Oculto boton de confirmar
			// Oculto boton de Ejecutada
			// Queda boton de Auditar.
			confirmar.setVisibility(View.INVISIBLE);
			ejecutada.setVisibility(View.INVISIBLE);
		} else if (role.equals(new RoleDTO(SiuConstants.ROLES.SUPERVISOR))) {
			// Oculto boton de Ejecutada
			// Oculto boton de Auditar
			// Queda boton de Confirmar
			auditar.setVisibility(View.INVISIBLE);
			ejecutada.setVisibility(View.INVISIBLE);
		} else if (role.equals(new RoleDTO(SiuConstants.ROLES.SECRETARIA))) {
			// Oculto boton de Confirmar
			// Oculto boton de Auditar
			// Queda boton de Ejecutada
			confirmar.setVisibility(View.INVISIBLE);
			auditar.setVisibility(View.INVISIBLE);
		}
	}

	private void populateData(){
		this.inspeccionID = (TextView) this.findViewById(R.id.inspeccionID);
		this.riesgoID = (TextView) this.findViewById(R.id.riesgoText);
		this.tipificacionText = (TextView) this.findViewById(R.id.tipificacionText);

		this.estadoText = (TextView) this.findViewById(R.id.estadoText);

		this.calleText = (TextView) this.findViewById(R.id.calleText);
		this.alturaText = (TextView) this.findViewById(R.id.alturaText);

		this.entreCallesText = (TextView) this.findViewById(R.id.entreCallesText);

		this.img1 = (ImageView) this.findViewById(R.id.img1);
		this.img1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new ImagePopup(InspeccionDetail.this,Util.getBitmap(idto.getImg1()));

			}
		});
		this.img2 = (ImageView) this.findViewById(R.id.img2);
		this.img2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new ImagePopup(InspeccionDetail.this,Util.getBitmap(idto.getImg2()));

			}
		});
		this.img3 = (ImageView) this.findViewById(R.id.img3);
		this.img3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new ImagePopup(InspeccionDetail.this,Util.getBitmap(idto.getImg3()));

			}
		});
		this.mapImg = (ImageView) this.findViewById(R.id.mapImg);
		this.mapImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new ImagePopup(InspeccionDetail.this,Util.getBitmap(idto.getImgMap()));

			}
		});

		this.observacionText = (TextView) this.findViewById(R.id.observacionText);	
		this.auditoriasList = (ListView) this.findViewById(R.id.auditoriasList);

		this.auditoriasList.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> adap, View arg1, int position,long id) {
				System.out.println("Seleccione Auditoria id: "  + id);
				AuditoriaDTO audto = (AuditoriaDTO)adap.getItemAtPosition(position);
				auditoriaPopup(audto);

			}
		});

		// Escribiendo Datos Inspeccion
		inspeccionID.setText(idto.getId().toString());
		riesgoID.setText(Util.riesgoIDtoString(idto.getRiesgo()));
		observacionText.setText(idto.getObservacion());

		img1.setImageBitmap(Util.getBitmap(idto.getImg1()));
		img2.setImageBitmap(Util.getBitmap(idto.getImg2()));
		img3.setImageBitmap(Util.getBitmap(idto.getImg3()));
		mapImg.setImageBitmap(Util.getBitmap(idto.getImgMap()));

		estadoText.setText(Util.stateIDToString(idto.getLastStateIdentifier()));

		tipificacionText.setText(getTipificacionString(idto.getTema()));

		calleText.setText(idto.getCalle());
		alturaText.setText(idto.getAltura().toString());

		auditAsynkTask(idto.getId());

	}

	private void auditAsynkTask(Long inspeccionID){
		AsyncTask<Long, String, ArrayList<AuditoriaDTO>> t = new AsyncTask<Long, String, ArrayList<AuditoriaDTO>>(){
			@Override
			protected ArrayList<AuditoriaDTO> doInBackground(Long... params) {
				Long filter = params[0];
				ArrayList<AuditoriaDTO> dtos = getAuditoriaList(filter);
				return dtos;
			}
			@Override
			protected void onPostExecute(ArrayList<AuditoriaDTO> result){
				InspeccionDetail.this.adapter = new AuditoriaAdapter(InspeccionDetail.this, result);
				InspeccionDetail.this.auditoriasList.setAdapter(adapter);
				TextView text = (TextView) InspeccionDetail.this.findViewById(R.id.textView5);
				// Se saca el "Auditorias: Procesando...."
				text.setText("Auditorias:");
			}
		};
		t.execute(inspeccionID);
	}

	private ArrayList<AuditoriaDTO> getAuditoriaList(Long inspeccionID){
		ArrayList<AuditoriaDTO> result = null;
		try {
			AuditoriaRestLetInterface resource = WebServiceFactory.getAuditoriaRestLetInterface();
			AuditoriaFilterDTO filter = new AuditoriaFilterDTO();
			filter.inspeccionID = idto.getId();
			filter.token = UserContainer.getUserDTO().getToken();
			result = (ArrayList<AuditoriaDTO>)(resource.getByID(filter));
		}catch (Exception e){
			// TODO
			e.printStackTrace();
		} finally {
			if (result == null){
				result = new ArrayList<AuditoriaDTO>();
			}
		}
		return result;
	}

	private String getTipificacionString(TemaDTO t){
		TipoRelevamientoDTO tipoDTO = t.getTiporelevamientodto();
		AreaDTO adto = tipoDTO.getAreadto();
		String result = adto.getNombre() + " > " + tipoDTO + " > " + t.getNombre();
		return result;
	}

	public void auditar(View view){
		Intent intent = new Intent (InspeccionDetail.this, AuditoriaCreate.class);
		Bundle b = new Bundle();
		b.putSerializable(SiuConstants.INSPECCION_PROPERTY,idto);
		intent.putExtras(b);
		startActivity(intent);
	}

	// Solo envio el id de la inspeccion y el token.
	public InspeccionDTO getSimpleDTO(){
		InspeccionDTO simpleIDTO = new InspeccionDTO();
		simpleIDTO.setId(this.idto.getId());
		simpleIDTO.token = UserContainer.getUserDTO().getToken();
		return simpleIDTO;
	}

	public void confirmar(View view){
		new AsyncFunctionWrapper(this).execute("Confirmando", "Procesando...", new Callable() {
			@Override
			public Integer call() {
				Integer result = Callable.NOTOK;
				try {
					InspeccionRestLetInterfaceTwo resource = WebServiceFactory.getInspeccionRestLetInterfaceTwo();
					// Bloqueante
					resource.confirmar(getSimpleDTO());
					// Poner el resultado depues de la funcion bloqueante por si arroja una excepcion.
					result = Callable.OK;
				} catch (Exception e){
					result = Callable.NOTOK;
				}
				return result;
			}
		}, new Callable(){

			@Override
			public Integer call() {
				Toast.makeText(getBaseContext(),"Inspeccion Confirmada con exito", Toast.LENGTH_LONG).show();
				InspeccionDetail.this.finish();
				return Callable.OK;
			}

		});
	}

	public void ejecutada(View view){
		new AsyncFunctionWrapper(this).execute("Ejecutando", "Procesando...", new Callable() {
			@Override
			public Integer call() {
				Integer result = Callable.NOTOK;
				try {
					InspeccionRestLetInterfaceTwo resource = WebServiceFactory.getInspeccionRestLetInterfaceTwo();
					// Bloqueante
					resource.ejecutadaAuditable(getSimpleDTO());
					// Poner el resultado depues de la funcion bloqueante por si arroja una excepcion.
					result = Callable.OK;
				} catch (Exception e){
					result = Callable.NOTOK;
				}
				return result;
			}
		}, new Callable(){
			@Override
			public Integer call() {
				Toast.makeText(getBaseContext(),"Inspeccion marcada como Ejecutada con exito", Toast.LENGTH_LONG).show();
				InspeccionDetail.this.finish();
				return Callable.OK;
			}

		});
	}

	private void auditoriaPopup(AuditoriaDTO audto){
		// Inflate the popup_layout.xml
		LinearLayout viewGroup = (LinearLayout) InspeccionDetail.this.findViewById(R.id.popup);
		LayoutInflater layoutInflater = (LayoutInflater) InspeccionDetail.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(R.layout.auditoria_detail_popup, viewGroup);


		TextView leyenda = (TextView) layout.findViewById(R.id.leyenda_popup);

		leyenda.setText("Auditoria n°: "+ audto.getId() +" de Inspeccion: " + this.idto.getId());
		EditText observacion = (EditText) layout.findViewById(R.id.observaciones_popup);
		observacion.setText(audto.getObservaciones());
		RadioButton resuelto = (RadioButton) layout.findViewById(R.id.radio0);
		resuelto.setChecked(audto.getResuelto());
		TextView fecha = (TextView) layout.findViewById(R.id.fecha_popup);
		fecha.setText("Fecha: " + audto.getFecha());

		// Imagenes
		
		ImageView img1 = (ImageView) layout.findViewById(R.id.au_img1);
		img1.setImageBitmap(Util.getBitmap(audto.getImg1()));
		ImageView img2 = (ImageView) layout.findViewById(R.id.au_img2);
		img2.setImageBitmap(Util.getBitmap(audto.getImg2()));
		ImageView img3 = (ImageView) layout.findViewById(R.id.au_img3);
		img3.setImageBitmap(Util.getBitmap(audto.getImg3()));
		
		final PopupWindow popup = new PopupWindow(InspeccionDetail.this);
		popup.setContentView(layout);
		popup.setWidth(500);
		popup.setHeight(950);
		popup.setFocusable(true);

		popup.showAtLocation(layout, Gravity.CENTER, 0, 0);

		// Clear the default translucent background
		popup.setBackgroundDrawable(new BitmapDrawable());

		// Getting a reference to Close button, and close the popup when clicked.
		Button close = (Button) layout.findViewById(R.id.close);
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popup.dismiss();
			}
		});

	}

}
