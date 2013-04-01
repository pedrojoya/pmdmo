package es.iessaladillo.pedrojoya.pr048.fragmentos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import es.iessaladillo.pedrojoya.pr048.R;

public class SeleccionSimpleDialogFragment extends DialogFragment {

	private Dialog dialogo = null;
	private SeleccionSimpleDialogListener listener = null;
	private int turnoSeleccionado = 0;

	// Interfaz pública para comunicación con la actividad.
	public interface SeleccionSimpleDialogListener {
		public void onNeutralButtonClick(DialogFragment dialog, int which);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
		b.setTitle(R.string.turno);
		b.setSingleChoiceItems(R.array.turnos, turnoSeleccionado,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						turnoSeleccionado = which;
					}
				});
		b.setIcon(R.drawable.ic_launcher);
		b.setNeutralButton(R.string.aceptar, new OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int arg1) {
				// Cierro el diálogo.
				d.dismiss();
				// Notifico al listener.
				listener.onNeutralButtonClick(
						SeleccionSimpleDialogFragment.this, turnoSeleccionado);
			}
		});
		dialogo = b.create();
		return dialogo;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Establece la actividad como listener de los eventos del diálogo.
		try {
			listener = (SeleccionSimpleDialogListener) activity;
		} catch (ClassCastException e) {
			// La actividad no implementa la interfaz, se lanza excepción.
			throw new ClassCastException(activity.toString()
					+ " debe implementar SeleccionSimpleDialogListener");
		}
	}

}
