package es.iessaladillo.pedrojoya.pr048.actividades;

import static es.iessaladillo.pedrojoya.pr048.utils.Utils.mostrarTostada;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import es.iessaladillo.pedrojoya.pr048.R;
import es.iessaladillo.pedrojoya.pr048.fragmentos.AdaptadorDialogFragment;
import es.iessaladillo.pedrojoya.pr048.fragmentos.AdaptadorDialogFragment.AdaptadorDialogListener;
import es.iessaladillo.pedrojoya.pr048.fragmentos.DatePickerDialogFragment;
import es.iessaladillo.pedrojoya.pr048.fragmentos.SeleccionDirectaDialogFragment;
import es.iessaladillo.pedrojoya.pr048.fragmentos.SeleccionDirectaDialogFragment.SeleccionDirectaDialogListener;
import es.iessaladillo.pedrojoya.pr048.fragmentos.SeleccionMultipleDialogFragment;
import es.iessaladillo.pedrojoya.pr048.fragmentos.SeleccionMultipleDialogFragment.SeleccionMultipleDialogListener;
import es.iessaladillo.pedrojoya.pr048.fragmentos.SeleccionSimpleDialogFragment;
import es.iessaladillo.pedrojoya.pr048.fragmentos.SeleccionSimpleDialogFragment.SeleccionSimpleDialogListener;
import es.iessaladillo.pedrojoya.pr048.fragmentos.SiNoDialogFragment;
import es.iessaladillo.pedrojoya.pr048.fragmentos.SiNoDialogFragment.SiNoDialogListener;
import es.iessaladillo.pedrojoya.pr048.fragmentos.TimePickerDialogFragment;
import es.iessaladillo.pedrojoya.pr048.modelos.Album;

public class MainActivity extends FragmentActivity implements
		OnDateSetListener, OnTimeSetListener, SiNoDialogListener,
		SeleccionDirectaDialogListener, SeleccionSimpleDialogListener,
		SeleccionMultipleDialogListener, AdaptadorDialogListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	// Al hacer click sobre cualquier botón.
	public void btnOnClick(View v) {
		// Creo una instancia del fragmento de diálogo correspondiente y lo
		// muestro, especificando un tag.
		DialogFragment frgDialogo = null;
		switch (v.getId()) {
		case R.id.btnDatePicker:
			frgDialogo = new DatePickerDialogFragment();
			frgDialogo.show(this.getSupportFragmentManager(),
					"DatePickerDialogFragment");
			break;
		case R.id.btnTimePicker:
			frgDialogo = new TimePickerDialogFragment();
			frgDialogo.show(this.getSupportFragmentManager(),
					"TimePickerDialogFragment");
			break;
		case R.id.btnAlertaSiNo:
			frgDialogo = new SiNoDialogFragment();
			frgDialogo.show(this.getSupportFragmentManager(),
					"SiNoDialogFragment");
			break;
		case R.id.btnAlertaSeleccionDirecta:
			frgDialogo = new SeleccionDirectaDialogFragment();
			frgDialogo.show(this.getSupportFragmentManager(),
					"SeleccionDirectaDialogFragment");
			break;
		case R.id.btnAlertaSeleccionSimple:
			frgDialogo = new SeleccionSimpleDialogFragment();
			frgDialogo.show(this.getSupportFragmentManager(),
					"SeleccionSimpleDialogFragment");
			break;
		case R.id.btnAlertaSeleccionMultiple:
			frgDialogo = new SeleccionMultipleDialogFragment();
			frgDialogo.show(this.getSupportFragmentManager(),
					"SeleccionMultipleDialogFragment");
			break;
		case R.id.btnAlertaAdaptador:
			frgDialogo = new AdaptadorDialogFragment();
			frgDialogo.show(this.getSupportFragmentManager(),
					"AdaptadorDialogFragment");
			break;
		}
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		mostrarTostada(getApplicationContext(),
				getString(R.string.ha_seleccionado) + dayOfMonth + "/"
						+ (monthOfYear + 1) + "/" + year,
				R.drawable.ic_launcher);
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		mostrarTostada(getApplicationContext(),
				getString(R.string.ha_seleccionado) + hourOfDay + ":" + minute,
				R.drawable.ic_launcher);
	}

	// Al hacer click sobre el botón Sí.
	@Override
	public void onPositiveButtonClick(DialogFragment dialog) {
		mostrarTostada(getApplicationContext(),
				getString(R.string.usuario_borrado), R.drawable.ic_launcher);
	}

	// Al hacer click sobre el botón No.
	@Override
	public void onNegativeButtonClick(DialogFragment dialog) {
		// No hace nada.
	}

	// Al seleccionar un elemento en selección directa.
	@Override
	public void onItemClick(DialogFragment dialog, int which) {
		String[] turnos = getResources().getStringArray(R.array.turnos);
		mostrarTostada(getApplicationContext(),
				getString(R.string.ha_seleccionado) + turnos[which],
				R.drawable.ic_launcher);
	}

	// Al hacer click en el botón neutral en selección simple.
	@Override
	public void onNeutralButtonClick(DialogFragment dialog, int which) {
		String[] turnos = getResources().getStringArray(R.array.turnos);
		mostrarTostada(getApplicationContext(),
				getString(R.string.ha_seleccionado) + turnos[which],
				R.drawable.ic_launcher);
	}

	// Al pulsar el botón neutral en selección múltiple.
	@Override
	public void onNeutralButtonClick(DialogFragment dialog,
			boolean[] optionIsChecked) {
		String[] turnos = getResources().getStringArray(R.array.turnos);
		String mensaje = "";
		boolean primero = true;
		for (int i = 0; i < optionIsChecked.length; i++) {
			if (optionIsChecked[i]) {
				if (primero) {
					mensaje += getString(R.string.ha_seleccionado);
					primero = false;
				} else {
					mensaje += ", ";
				}
				mensaje += turnos[i];
			}
		}
		if (mensaje.equals("")) {
			mensaje = getString(R.string.no_ha_seleccionado_ningun_turno);
		}
		mostrarTostada(getApplicationContext(), mensaje, R.drawable.ic_launcher);
	}

	// Al hacer click sobre un álbum.
	@Override
	public void onListItemClick(DialogFragment dialog, Album album) {
		mostrarTostada(getApplicationContext(),
				getString(R.string.ha_seleccionado) + album.getNombre(),
				R.drawable.ic_launcher);
	}

}
