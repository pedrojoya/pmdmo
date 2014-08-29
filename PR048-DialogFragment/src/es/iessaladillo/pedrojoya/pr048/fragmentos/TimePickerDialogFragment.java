package es.iessaladillo.pedrojoya.pr048.fragmentos;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;

public class TimePickerDialogFragment extends DialogFragment {

    // Variables.
    private TimePickerDialog.OnTimeSetListener listener;

    // Al crear el diálogo. Retorna el diálogo configurado.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendario = Calendar.getInstance();
        TimePickerDialog dialogo = new TimePickerDialog(this.getActivity(),
                listener, calendario.get(Calendar.HOUR),
                calendario.get(Calendar.MINUTE), true);
        return dialogo;
    }

    // Al enlazar el fragmento con la actividad.
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
