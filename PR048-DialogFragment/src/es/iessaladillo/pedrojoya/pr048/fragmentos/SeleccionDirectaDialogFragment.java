package es.iessaladillo.pedrojoya.pr048.fragmentos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import es.iessaladillo.pedrojoya.pr048.R;

public class SeleccionDirectaDialogFragment extends DialogFragment {

	private Dialog dialogo = null;
	private SeleccionDirectaDialogListener listener = null;

	// Interfaz pública para comunicación con la actividad.
	public interface SeleccionDirectaDialogListener {
		public void onItemClick(DialogFragment dialog, int which);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
		b.setTitle(R.string.turno);
		b.setItems(R.array.turnos, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Notifico al listener y le paso el fragmento.
				listener.onItemClick(SeleccionDirectaDialogFragment.this, which);
			}
		});
		b.setIcon(R.drawable.ic_launcher);
		dialogo = b.create();
		return dialogo;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Establece la actividad como listener de los eventos del diálogo.
		try {
			listener = (SeleccionDirectaDialogListener) activity;
		} catch (ClassCastException e) {
			// La actividad no implementa la interfaz, se lanza excepción.
			throw new ClassCastException(activity.toString()
					+ " debe implementar SeleccionDirectaDialogListener");
		}
	}

}
