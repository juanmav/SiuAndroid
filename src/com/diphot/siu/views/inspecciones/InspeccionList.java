package com.diphot.siu.views.inspecciones;

import java.util.ArrayList;
import com.diphot.siu.R;
import com.diphot.siu.SiuConstants;
import com.diphot.siu.UserContainer;
import com.diphot.siu.services.WebServiceFactory;
import com.diphot.siu.services.restlet.InspeccionRestLetInterface;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import com.diphot.siuweb.shared.dtos.UserDTO;
import com.diphot.siuweb.shared.dtos.filters.InspeccionFilterDTO;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class InspeccionList extends ListActivity {

	InspeccionAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inspeccion_list);
		Bundle b = getIntent().getExtras();
		int riesgo = b.getInt(SiuConstants.RIESGO_PROPERTY);
		int estado = b.getInt(SiuConstants.ESTADO_PROPERTY);

		//InspeccionDAO idao = new InspeccionDAO(this);
		//ArrayList<InspeccionDTO> dtos = idao.getList();
		//ArrayList<InspeccionDTO> dtos = getlist(estado, riesgo); 
		//InspeccionAdapter adapter = new InspeccionAdapter(this, dtos);
		//setListAdapter(adapter);
		InspeccionFilterDTO filtro = new InspeccionFilterDTO();
		filtro.riesgo = riesgo;
		filtro.estadoID = estado;
		asyntask(filtro);
	}

	private void asyntask(InspeccionFilterDTO filter){
		AsyncTask<InspeccionFilterDTO, String, ArrayList<InspeccionDTO>> t = new AsyncTask<InspeccionFilterDTO, String, ArrayList<InspeccionDTO>>(){
			@Override
			protected ArrayList<InspeccionDTO> doInBackground(InspeccionFilterDTO... params) {
				InspeccionFilterDTO filter = params[0];
				ArrayList<InspeccionDTO> dtos = getlist(filter.estadoID, filter.riesgo);
				return dtos;
			}
			@Override
			protected void onPostExecute(ArrayList<InspeccionDTO> result){
				InspeccionList.this.adapter = new InspeccionAdapter(InspeccionList.this, result);
				InspeccionList.this.setListAdapter(adapter);
			}
		};
		t.execute(filter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inspeccion_list, menu);
		return true;
	}

	private ArrayList<InspeccionDTO> getlist(int estado, int riesgo){
		ArrayList<InspeccionDTO> result = null;
		try {
			InspeccionRestLetInterface resource = WebServiceFactory.getInspeccionRestLetInterface();
			InspeccionFilterDTO filter = new InspeccionFilterDTO();
			UserDTO u = UserContainer.getUserDTO();
			filter.riesgo = riesgo;
			filter.estadoID = estado;
			filter.token = u.getToken();
			result = (ArrayList<InspeccionDTO>)(resource.getList(filter));
		}catch (Exception e){
			// TODO
			e.printStackTrace();
		} finally {
			if (result == null){
				result = new ArrayList<InspeccionDTO>();
			}
		}
		return result;
	}

	@Override
	public void onListItemClick(ListView parent, View view, int position, long id)  {
		InspeccionDTO i = this.adapter.getItem(position);
		Bundle b = new Bundle();
		b.putSerializable(SiuConstants.INSPECCION_PROPERTY,i);
		Intent intent = new Intent(InspeccionList.this, InspeccionDetail.class);
		intent.putExtras(b);
		startActivity(intent);
	}
}
