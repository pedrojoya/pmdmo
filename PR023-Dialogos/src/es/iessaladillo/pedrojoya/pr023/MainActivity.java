package es.iessaladillo.pedrojoya.pr023;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity implements OnDateSetListener, OnTimeSetListener {

	// Constantes
    private static final int DLG_DATEPICKER = 0;	// Diálogo DatePicker.
	private static final int DLG_TIMEPICKER = 1;	// Diálogo TimePicker.
	private static final int DLG_ALERTA_SINO = 2;	// Alerta Si / NO.

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

	// Al hacer click sobre btnDatePicker.
	public void btnDatePickerOnClick(View v) {
		// Muestro el diálogo correspondiente.
		this.showDialog(DLG_DATEPICKER);
    	
	}

	// Al hacer click sobre btnTimePicker.
	public void btnTimePickerOnClick(View v) {
		// Muestro el diálogo correspondiente.
		this.showDialog(DLG_TIMEPICKER);
	}
	
	// Al hacer click sobre btnAlertaSiNo.
	public void btnAlertaSiNoOnClick(View v) {
		// Muestro el diálogo correspondiente.
		this.showDialog(DLG_ALERTA_SINO);
	}

	@Override
	protected Dialog onCreateDialog(int idDialogo) {
		// Creo el diálogo correspondiente.
		Dialog dialogo = null;
		Calendar calendario;
		switch (idDialogo) {
		case DLG_DATEPICKER:
			calendario = Calendar.getInstance(); 
			dialogo = new DatePickerDialog(this, this, 
							calendario.get(Calendar.YEAR), 
							calendario.get(Calendar.MONTH), 
							calendario.get(Calendar.DAY_OF_MONTH));
			break;
		case DLG_TIMEPICKER:
			calendario = Calendar.getInstance(); 
			dialogo = new TimePickerDialog(this, this, 
							calendario.get(Calendar.HOUR), 
							calendario.get(Calendar.MINUTE), 
							true);
			break;
		case DLG_ALERTA_SINO:
			AlertDialog.Builder b = new AlertDialog.Builder(this);
			b.setTitle(R.string.eliminar_usuario);
			b.setMessage(R.string.esta_seguro_de_que_quiere_eliminar_el_usuario);
			b.setIcon(R.drawable.ic_launcher);
			b.setPositiveButton(R.string.si, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mostrarTostada(getString(R.string.usuario_borrado), R.drawable.ic_launcher);
				}
			});
			b.setNegativeButton(R.string.no, null);
			dialogo = b.create();
			break;
		}
		return dialogo;
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		mostrarTostada(getString(R.string.ha_seleccionado) + 
				dayOfMonth + "/" +  (monthOfYear + 1) + "/" + year, 
				R.drawable.ic_launcher);
	}
	
	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		mostrarTostada(getString(R.string.ha_seleccionado) + 
				hourOfDay + ":" +  minute, 
				R.drawable.ic_launcher);
	}

	// Muestra un Toast.
	private void mostrarTostada(String mensaje, int drawableResId) {
		// Obtengo el contexto.
		Context contexto = getApplicationContext();
		// Creo un objeto tostada.
		Toast tostada = new Toast(contexto);
		// Creo un LinearLayout como padre del layout para la tostada.
        LinearLayout padre = new LinearLayout(contexto);
        padre.setOrientation(LinearLayout.HORIZONTAL);
        padre.setBackgroundResource(R.drawable.toast_frame);
        // Creo un ImageView, la asigno la imagen pasada como parámetro
        // y se la añado al LinearLayout.
        ImageView imagen = new ImageView(contexto);
        LayoutParams params = new LayoutParams(60, 60, 0);
        params.gravity = Gravity.CENTER_VERTICAL;
        imagen.setLayoutParams(params);
        imagen.setImageResource(drawableResId);
        padre.addView(imagen);
        // Creo un TextView, le asigno el texto del mensaje y
        // se lo añado al LinearLayout.
        TextView texto = new TextView(contexto);
        texto.setText(mensaje);
        texto.setTextAppearance(contexto, android.R.style.TextAppearance_Small);
        params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
        params.gravity = Gravity.CENTER;
        params.leftMargin = 20;
        texto.setLayoutParams(params);
        texto.setShadowLayer(2.75f, 0, 0, Color.parseColor("#BB000000"));
        padre.addView(texto);
        // Establezco el LinarLayout como la vista 
        // que debe mostrar la tostada.
        tostada.setView(padre);
        // Establezco la duración de la tostada.
        tostada.setDuration(Toast.LENGTH_SHORT);
        // Muestro la tostada.
        tostada.show();
	}
		
    
}
