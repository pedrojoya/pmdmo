package es.iessaladillo.pedrojoya.pr048.fragmentos;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class TimePickerDialogFragment extends DialogFragment {

	private TimePickerDialog dialogo;
	private TimePickerDialog.OnTimeSetListener listener;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Calendar calendario = Calendar.getInstance();
		dialogo = new TimePickerDialog(this.getActivity(), listener,
				calendario.get(Calendar.HOUR), calendario.get(Calendar.MINUTE),
				true);
		return dialogo;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Establece la actividad como listener de los eventos del diálogo.
		try {
			listener = (OnTimeSetListener) activity;
		} catch (ClassCastException e) {
			// La actividad no implementa la interfaz, se lanza excepción.
			throw new ClassCastException(activity.toString()
					+ " debe implementar TimePickerDialog.OnTimeSetListener");
		}
	}

}
