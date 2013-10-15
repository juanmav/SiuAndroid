package com.diphot.siu.views.adapters;

import java.util.ArrayList;

import com.diphot.siu.R;
import com.diphot.siuweb.shared.dtos.LocalidadDTO;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LocalidadAdapter extends BaseAdapter{

	ArrayList<LocalidadDTO> list;
	Context context;
	
	public LocalidadAdapter(Context context, ArrayList<LocalidadDTO> list){
		this.list = list;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return this.list.size();
	}

	@Override
	public LocalidadDTO getItem(int position) {
		return this.list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.list.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// todo el dto.
		LocalidadDTO dto = this.list.get(position);
		TextView leyenda =new TextView(context); 
		leyenda.setText(dto.getNombre());
		leyenda.setTextAppearance(context, R.style.tipografiaSpinner);
		return leyenda;
	}

	

}
