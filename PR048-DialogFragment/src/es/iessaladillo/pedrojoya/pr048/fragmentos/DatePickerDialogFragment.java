package es.iessaladillo.pedrojoya.pr048.fragmentos;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class DatePickerDialogFragment extends DialogFragment {

    // Variables.
    private DatePickerDialog.OnDateSetListener mListener;

    // Al crear el diálogo. Retorna el diálogo configurado.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendario = Calendar.getInstance();
        DatePickerDialog dialogo = new DatePickerDialog(this.getActivity(),
                mListener, calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH));
        return dialogo;
    }

    // Cuando se enlaza el fragmento con la actividad.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Establece la actividad como listener de los eventos del diálogo.
        try {
            mListener = (OnDateSetListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz, se lanza excepción.
            throw new ClassCastException(activity.toString()
                    + " debe implementar DatePickerDialog.OnDateSetListener");
        }
    }

}
