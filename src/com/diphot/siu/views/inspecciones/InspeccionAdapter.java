package com.diphot.siu.views.inspecciones;

import java.util.ArrayList;

import com.diphot.siu.R;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

public class InspeccionAdapter extends BaseAdapter{

	ArrayList<InspeccionDTO> list;
	
	Context context;
	
	public InspeccionAdapter(Context context, ArrayList<InspeccionDTO> list){
		this.list = list;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		return this.list.get(position);
	}

	@Override
	public long getItemId(int positiion) {
		return this.list.get(positiion).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.inspeccion_item_list_view, null);
		
		TextView id = (TextView) convertView.findViewById(R.id.inspeccionid);
		TextView calle = (TextView) convertView.findViewById(R.id.calle);
		TextView altura = (TextView) convertView.findViewById(R.id.altura);
		TextView observacion = (TextView) convertView.findViewById(R.id.observacion);
		TextView riesgo = (TextView) convertView.findViewById(R.id.riesgo);
		Switch enviado = (Switch) convertView.findViewById(R.id.enviado);
		
		// todo el dto.
		InspeccionDTO dto = this.list.get(position);
		
		
		
		id.setText(dto.getId().toString());
		calle.setText(dto.getCalle());
		altura.setText(dto.getAltura().toString());
		observacion.setText(dto.getObservacion());
		
		riesgo.setText("Grado: " + dto.getRiesgo());
		// TODO verificar esto
		/*if (dto.getEnviado() == 0){
			enviado.setChecked(false);
		} else {
			enviado.setChecked(true);
		}*/
		
		
		return convertView;
	}
}
