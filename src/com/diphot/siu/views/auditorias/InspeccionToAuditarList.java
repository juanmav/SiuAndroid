package com.diphot.siu.views.auditorias;

import java.util.ArrayList;

import com.diphot.siu.R;
import com.diphot.siu.SiuConstants;
import com.diphot.siu.UserContainer;
import com.diphot.siu.persistence.InspeccionDAO;
import com.diphot.siu.services.WebServiceFactory;
import com.diphot.siu.services.restlet.AuditoriaRestLetInterfaceTwo;
import com.diphot.siu.util.AsyncFunctionWrapper;
import com.diphot.siu.util.AsyncFunctionWrapper.Callable;
import com.diphot.siu.views.inspecciones.InspeccionAdapter;
import com.diphot.siu.views.inspecciones.InspeccionDetail;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;

public class InspeccionToAuditarList extends ListActivity {

	private InspeccionAdapter adapter;
	private ArrayList<InspeccionDTO> inspecciones; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inspeccion_list);
		
		// Buscando en el servidor.
		// Se deja de ejemplo de AsyncFunctionWrapper.
		//goToServer();
		
		// Buscando local.
		goToLocalStorge();
	}
	
	private void goToLocalStorge(){
		InspeccionDAO idao = new InspeccionDAO(this);
		inspecciones = idao.getToAudit();
		this.adapter = new InspeccionAdapter(this, this.inspecciones);
		this.setListAdapter(adapter);
	}
	
	@SuppressWarnings("unused")
	private void goToServer(){
		new AsyncFunctionWrapper(this).execute("Listado para Auditar", "Procesando...", new Callable() {
			@Override
			public Integer call() {
				Integer result = Callable.NOTOK;
				try {
					AuditoriaRestLetInterfaceTwo resource = WebServiceFactory.getAuditoriaRestLetInterfaceTwo();
					InspeccionDTO idto = new InspeccionDTO();
					idto.token = UserContainer.getUserDTO().getToken();
					// Bloqueante 
					ArrayList<InspeccionDTO> aux = resource.getInspeccionesToAuditar(idto);
					InspeccionToAuditarList.this.inspecciones = aux; 
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
				//Toast.makeText(getBaseContext(),"Inspeccion Marcada para Auditar con exito", Toast.LENGTH_LONG).show();
				//InspeccionToAuditarList.this.finish();
				InspeccionToAuditarList.this.adapter = new InspeccionAdapter(InspeccionToAuditarList.this, InspeccionToAuditarList.this.inspecciones);
				InspeccionToAuditarList.this.setListAdapter(adapter);
				return Callable.OK;
			}
		});
	}

	@Override
	public void onListItemClick(ListView parent, View view, int position, long id)  {
		InspeccionDTO i = this.adapter.getItem(position);
		Bundle b = new Bundle();
		b.putSerializable(SiuConstants.INSPECCION_PROPERTY,i);
		Intent intent = new Intent(InspeccionToAuditarList.this, InspeccionDetail.class);
		intent.putExtras(b);
		startActivity(intent);
	}

}
