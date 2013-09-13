package com.diphot.siu.views.inspecciones;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.diphot.siu.R;
import com.diphot.siu.SiuConstants;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InspeccionAdapter extends BaseAdapter {

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
	public InspeccionDTO getItem(int position) {
		return this.list.get(position);
	}

	@Override
	public long getItemId(int positiion) {
		return this.list.get(positiion).getId();
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.inspeccion_item_list_view, null);

		TextView id = (TextView) convertView.findViewById(R.id.inspeccionid);
		TextView calle = (TextView) convertView.findViewById(R.id.calle);
		TextView altura = (TextView) convertView.findViewById(R.id.altura);
		TextView observacion = (TextView) convertView.findViewById(R.id.observacion);
		ImageView riesgoIcon = (ImageView) convertView.findViewById(R.id.riesgoIcon);
		ImageView estadoIcon = (ImageView) convertView.findViewById(R.id.estadoIcon);
		TextView fecha = (TextView) convertView.findViewById(R.id.fecha);

		// todo el dto.
		InspeccionDTO dto = this.list.get(position);



		id.setText(dto.getId().toString());
		calle.setText(dto.getCalle());
		altura.setText(dto.getAltura().toString());
		observacion.setText(dto.getObservacion());
		Date date = null;
		try {
			date = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy",Locale.US).parse(dto.getFecha());
			fecha.setText((date.getDay() +1 )+ "/" + (date.getMonth() +1 ) + "/"+ (date.getYear()+ 1900));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		switch (dto.getLastStateIdentifier()) {
		case SiuConstants.OBSERVADO:
			estadoIcon.setImageResource(R.drawable.observado);
			break;
		case SiuConstants.CONFIRMADO:
			estadoIcon.setImageResource(R.drawable.confirmado);
			break;
		case SiuConstants.EJECUTADO:
			estadoIcon.setImageResource(R.drawable.auditable);
			break;
		case SiuConstants.RESUELTO:
			estadoIcon.setImageResource(R.drawable.resuelto);
			break;
		default:
			break;
		}


		switch (dto.getRiesgo()) {
		case SiuConstants.ALTO:
			riesgoIcon.setImageResource(R.drawable.btn_red);
			break;
		case SiuConstants.MEDIO:
			riesgoIcon.setImageResource(R.drawable.btn_yellow);
			break;
		case SiuConstants.BAJO:
			riesgoIcon.setImageResource(R.drawable.btn_green);
			break;
		default:
			break;
		}

		return convertView;
	}
}
