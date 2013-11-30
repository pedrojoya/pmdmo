package es.iessaladillo.pedrojoya.cursogalileotareasemana1.dialogos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.R;

public class FotoDialogFragment extends DialogFragment {

    private Dialog dialogo = null;
    private Callback listener = null;

    // Interfaz pública para comunicación con la actividad.
    public interface Callback {
        public void onAccionClick(int which);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.seleccionar_accion);
        b.setItems(R.array.acciones_dialogo_foto, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Notifico al listener.
                listener.onAccionClick(which);
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
            listener = (Callback) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz, se lanza excepción.
            throw new ClassCastException(activity.toString()
                    + " debe implementar FotoDialogFragment.Callback");
        }
    }

}