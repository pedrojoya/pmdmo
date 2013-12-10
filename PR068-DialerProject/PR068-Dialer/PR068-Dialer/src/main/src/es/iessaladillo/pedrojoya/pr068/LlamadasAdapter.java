package es.iessaladillo.pedrojoya.pr068;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LlamadasAdapter extends ArrayAdapter<Llamada> {

	// Variables.
	private LayoutInflater inflador;
	private ArrayList<Llamada> datos;
	private SimpleDateFormat formateador;

	// Clase interna contenedor.
	class Contenedor {
		TextView lblFecha;
		TextView lblNumero;
		ImageView imgTipo;

	}

	// Constructor.
	@SuppressLint("SimpleDateFormat")
	public LlamadasAdapter(Context context, ArrayList<Llamada> datos) {
		// Se llama al constructor del padre.
		super(context, R.layout.activity_registro_item, datos);
		this.datos = datos;
		// Se obtiene un inflador de layouts.
		inflador = LayoutInflater.from(context);
		// Se obtiene un formateador de fechas.
		formateador = new SimpleDateFormat("dd/MM HH:mm");
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Contenedor contenedor;
		// Si no se puede reciclar.
		if (convertView == null) {
			// Se infla el layout.
			convertView = inflador.inflate(R.layout.activity_registro_item,
					parent, false);
			// Se obtienen las vistas y se guardan en un contenedor que se
			// almacenar en la propiedad tag del layout.
			contenedor = new Contenedor();
			contenedor.lblFecha = (TextView) convertView
					.findViewById(R.id.lblFecha);
			contenedor.lblNumero = (TextView) convertView
					.findViewById(R.id.lblNumero);
			contenedor.imgTipo = (ImageView) convertView
					.findViewById(R.id.imgTipo);
			convertView.setTag(contenedor);
		} else {
			contenedor = (Contenedor) convertView.getTag();
		}
		// Se escriben los datos correspondientes en las vistas.
		Llamada llamada = datos.get(position);
		contenedor.lblFecha.setText(formateador.format(llamada.getFecha()));
		contenedor.lblNumero.setText(llamada.getNumero());
		int resIdTipoLLamada;
		switch (llamada.getTipo()) {
		case Llamada.LLAMADA_ENTRANTE:
			resIdTipoLLamada = R.drawable.sym_call_incoming;
			break;
		case Llamada.LLAMADA_PERDIDA:
			resIdTipoLLamada = R.drawable.sym_call_missed;
			break;
		default:
			resIdTipoLLamada = R.drawable.sym_call_outgoing;
			break;
		}
		contenedor.imgTipo.setImageResource(resIdTipoLLamada);
		// Se retorna la vista que debe mostrar el ítem.
		return convertView;
	}
}
