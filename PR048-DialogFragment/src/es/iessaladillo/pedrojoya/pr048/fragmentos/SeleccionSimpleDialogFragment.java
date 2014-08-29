package es.iessaladillo.pedrojoya.pr048.fragmentos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import es.iessaladillo.pedrojoya.pr048.R;

public class SeleccionSimpleDialogFragment extends DialogFragment {

    // Variables.
    private SeleccionSimpleDialogListener mListener = null;
    private int mTurnoSeleccionado = 0;

    // Interfaz pública para comunicación con la actividad.
    public interface SeleccionSimpleDialogListener {
        public void onNeutralButtonClick(DialogFragment dialog, int which);
    }

    // Al crear el diálogo. Retorna el diálogo configurado.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.turno);
        b.setSingleChoiceItems(R.array.turnos, mTurnoSeleccionado,
                new OnClickListener() {
                    // Al seleccionar un elemento.
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTurnoSeleccionado = which;
                    }
                });
        b.setIcon(R.drawable.ic_launcher);
        b.setNeutralButton(R.string.aceptar, new OnClickListener() {
            // Al pulsar el botón neutro.
            @Override
            public void onClick(DialogInterface d, int arg1) {
                // Se cierra el diálogo.
                d.dismiss();
                // Se notifica el evento al listener indicando el índice del
                // elemento seleccionado.
                mListener.onNeutralButtonClick(
                        SeleccionSimpleDialogFragment.this, mTurnoSeleccionado);
            }
        });
        return b.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Establece la actividad como listener de los eventos del diálogo.
        try {
            mListener = (SeleccionSimpleDialogListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz, se lanza excepción.
            throw new ClassCastException(activity.toString()
                    + " debe implementar SeleccionSimpleDialogListener");
        }
    }

}
