package es.iessaladillo.pedrojoya.pr048.actividades;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import es.iessaladillo.pedrojoya.pr048.R;
import es.iessaladillo.pedrojoya.pr048.fragmentos.AdaptadorDialogFragment;
import es.iessaladillo.pedrojoya.pr048.fragmentos.AdaptadorDialogFragment.AdaptadorDialogListener;
import es.iessaladillo.pedrojoya.pr048.fragmentos.DatePickerDialogFragment;
import es.iessaladillo.pedrojoya.pr048.fragmentos.LoginDialogFragment;
import es.iessaladillo.pedrojoya.pr048.fragmentos.LoginDialogFragment.LoginDialogListener;
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

public class MainActivity extends Activity implements OnDateSetListener,
        OnTimeSetListener, SiNoDialogListener, SeleccionDirectaDialogListener,
        SeleccionSimpleDialogListener, SeleccionMultipleDialogListener,
        AdaptadorDialogListener, LoginDialogListener, OnClickListener {

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        getVistas();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        ((Button) findViewById(R.id.btnDatePicker)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnTimePicker)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnAlertaSiNo)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnAlertaSeleccionDirecta))
                .setOnClickListener(this);
        ((Button) findViewById(R.id.btnAlertaSeleccionSimple))
                .setOnClickListener(this);
        ((Button) findViewById(R.id.btnAlertaSeleccionMultiple))
                .setOnClickListener(this);
        ((Button) findViewById(R.id.btnAlertaLayout)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnAlertaAdaptador))
                .setOnClickListener(this);
    }

    // Al hacer click sobre un botón.
    @Override
    public void onClick(View v) {
        // Se crea una instancia del fragmento de diálogo correspondiente y se
        // muestro, especificando un tag.
        DialogFragment frgDialogo = null;
        switch (v.getId()) {
        case R.id.btnDatePicker:
            frgDialogo = new DatePickerDialogFragment();
            frgDialogo.show(getFragmentManager(), "DatePickerDialogFragment");
            break;
        case R.id.btnTimePicker:
            frgDialogo = new TimePickerDialogFragment();
            frgDialogo.show(getFragmentManager(), "TimePickerDialogFragment");
            break;
        case R.id.btnAlertaSiNo:
            frgDialogo = new SiNoDialogFragment();
            frgDialogo.show(this.getFragmentManager(), "SiNoDialogFragment");
            break;
        case R.id.btnAlertaSeleccionDirecta:
            frgDialogo = new SeleccionDirectaDialogFragment();
            frgDialogo.show(this.getFragmentManager(),
                    "SeleccionDirectaDialogFragment");
            break;
        case R.id.btnAlertaSeleccionSimple:
            frgDialogo = new SeleccionSimpleDialogFragment();
            frgDialogo.show(this.getFragmentManager(),
                    "SeleccionSimpleDialogFragment");
            break;
        case R.id.btnAlertaSeleccionMultiple:
            frgDialogo = new SeleccionMultipleDialogFragment();
            frgDialogo.show(this.getFragmentManager(),
                    "SeleccionMultipleDialogFragment");
            break;
        case R.id.btnAlertaLayout:
            frgDialogo = new LoginDialogFragment();
            frgDialogo.show(this.getFragmentManager(), "LoginDialogFragment");
            break;
        case R.id.btnAlertaAdaptador:
            frgDialogo = new AdaptadorDialogFragment();
            frgDialogo.show(this.getFragmentManager(),
                    "AdaptadorDialogFragment");
            break;
        }
    }

    // Al establecer la fecha. Interfaz OnDateSetListener.
    // Recibe el DatePicker que lo ha generado, el año, el mes numérico y el día
    // del mes seleccionado.
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
            int dayOfMonth) {
        Toast.makeText(
                this,
                getString(R.string.ha_seleccionado)
                        + String.format("%02d", dayOfMonth) + "/"
                        + String.format("%02d", (monthOfYear + 1)) + "/"
                        + String.format("%04d", year), Toast.LENGTH_SHORT)
                .show();
    }

    // Al establecer la hora. Interfaz OnTimeSetListener.
    // Recibe el TimePicker que lo ha generado, la hora y los minutos.
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        SimpleDateFormat formateador = new SimpleDateFormat("HH:mm");
        Toast.makeText(
                this,
                getString(R.string.ha_seleccionado)
                        + String.format("%02d", hourOfDay) + ":"
                        + String.format("%02d", minute), Toast.LENGTH_SHORT)
                .show();
    }

    // Al hacer click sobre el botón Sí. Interfaz SiNoDialogListener.
    // Recibe el DialogFragment que lo ha generado.
    @Override
    public void onPositiveButtonClick(DialogFragment dialog) {
        Toast.makeText(this, getString(R.string.usuario_borrado),
                Toast.LENGTH_SHORT).show();
    }

    // Al hacer click sobre el botón No. Interfaz SiNoDialogListener.
    // Recibe el DialogFragment que lo ha generado.
    @Override
    public void onNegativeButtonClick(DialogFragment dialog) {
        // No hace nada.
    }

    // Al seleccionar un elemento. Interfaz SeleccionDirectaDialogListener.
    // Recibe el DialogFragment que lo ha generado y el índice de la opción
    // seleccionada.
    @Override
    public void onItemClick(DialogFragment dialog, int which) {
        String[] turnos = getResources().getStringArray(R.array.turnos);
        Toast.makeText(this,
                getString(R.string.ha_seleccionado) + turnos[which],
                Toast.LENGTH_SHORT).show();
    }

    // Al hacer click en el botón neutral. Interfaz
    // SeleccionSimpleDialogListener.
    // Recibe el DialogFragment que lo ha generado y el índice de la opción
    // seleccionada.
    @Override
    public void onNeutralButtonClick(DialogFragment dialog, int which) {
        String[] turnos = getResources().getStringArray(R.array.turnos);
        Toast.makeText(this,
                getString(R.string.ha_seleccionado) + turnos[which],
                Toast.LENGTH_SHORT).show();
    }

    // Al pulsar el botón neutral. Interfaz SeleccionMultipleDialogListener.
    // Recibe el DialogFragment que lo ha generado y un array que indica si
    // cada elemento ha sido seleccionado o no.
    @Override
    public void onNeutralButtonClick(DialogFragment dialog,
            boolean[] optionIsChecked) {
        String[] turnos = getResources().getStringArray(R.array.turnos);
        String mensaje = "";
        boolean primero = true;
        // Se construye la cadena con la lista de elementos seleccionados.
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
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    // Al hacer click sobre un álbum. Interfaz AdaptadorDialogListener.
    // Recibe el DialogFragment que lo ha generado y el album seleccionado.
    @Override
    public void onListItemClick(DialogFragment dialog, Album album) {
        Toast.makeText(this,
                getString(R.string.ha_seleccionado) + album.getNombre(),
                Toast.LENGTH_SHORT).show();
    }

    // Al hacer click sobre el botón Conectar del diálogo de Login.
    @Override
    public void onConectarClick(DialogFragment dialog) {
        Toast.makeText(this, "Usuario conectado", Toast.LENGTH_SHORT).show();
    }

    // Al hacer click sobre el botón Cancelar del diálogo de Login.
    @Override
    public void onCancelarClick(DialogFragment dialog) {
        // No se hace nada.
    }

}
