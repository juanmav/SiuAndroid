package com.diphot.siu.views.auditorias;

import java.util.ArrayList;
import com.diphot.siu.R;
import com.diphot.siuweb.shared.dtos.AuditoriaDTO;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class AuditoriaAdapter extends BaseAdapter{

	ArrayList<AuditoriaDTO> list;
	Context context;
	
	public AuditoriaAdapter(Context context, ArrayList<AuditoriaDTO> list){
		this.list = list;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return this.list.size();
	}

	@Override
	public AuditoriaDTO getItem(int position) {
		return this.list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.list.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.auditoria_item_list_view, null);
		
		TextView id = (TextView) convertView.findViewById(R.id.auID);
		TextView fecha = (TextView) convertView.findViewById(R.id.auFecha);
		CheckBox resuelto = (CheckBox)convertView.findViewById(R.id.auResuelto);
		TextView auObservacion = (TextView)convertView.findViewById(R.id.auObservacion);
		
		AuditoriaDTO audto = this.list.get(position);
		
		id.setText(audto.getId().toString());
		fecha.setText(audto.getFecha());
		resuelto.setChecked(audto.getResuelto());
		auObservacion.setText(audto.getObservaciones());
		
		return convertView;
	}
}
