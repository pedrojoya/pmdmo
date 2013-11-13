package es.iessaladillo.pedrojoya.cursogalileotareasemana1.actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.R;

public class DetalleTiendaActivity extends Activity {

    // Constantes p�blicas est�ticas.
    public static String EXTRA_TIENDA = "tienda";

    // Vistas.
    private TextView lblNombre;
    private TextView lblTelefono;
    private TextView lblWebsite;
    private TextView lblEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_tienda);
        // Se obtienen e inicializan las vistas.
        getVistas();
    }

    private void getVistas() {
        // Se obtienen las vistas.
        lblNombre = (TextView) findViewById(R.id.lblNombre);
        lblTelefono = (TextView) findViewById(R.id.lblTelefono);
        lblWebsite = (TextView) findViewById(R.id.lblWebsite);
        lblEmail = (TextView) findViewById(R.id.lblEmail);
        // Algunos textos se muestran como enlaces.
        Linkify.addLinks(lblTelefono, Linkify.PHONE_NUMBERS);
        Linkify.addLinks(lblWebsite, Linkify.WEB_URLS);
        Linkify.addLinks(lblEmail, Linkify.EMAIL_ADDRESSES);
        // Se obtiene el extra del Intent con el que ha sido llamada la
        // actividad.
        Intent i = getIntent();
        if (i != null && i.hasExtra(EXTRA_TIENDA)) {
            lblNombre.setText(i.getStringExtra(EXTRA_TIENDA));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Dependiendo del �tem del men� pulsado.
        switch (item.getItemId()) {
        case R.id.mnuFavorito:
            // marcarComoFavorito();
            // Se pone este c�digo SOLO para poder probar la actividad de
            // detalle de foto.
            Intent intent = new Intent(getApplicationContext(),
                    DetalleFotoActivity.class);
            startActivity(intent);
            break;
        case R.id.mnuCompartir:
            compartir();
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    // Env�a un intent para compartir texto con los datos de la tienda.
    private void compartir() {
        // Se obtiene la cadena de recomendaci�n.
        String recomendacion = getString(R.string.te_recomiendo_la_tienda)
                + lblNombre.getText().toString()
                + getString(R.string.del_centro_comercial);
        // Se crea un intent con la acci�n de enviar, el tipo texto plano y se
        // almacena en el extra EXTRA_TEXT la recomendaci�n
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, recomendacion);
        startActivity(Intent.createChooser(intent,
                getString(R.string.compartir_recomendacion)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detalle_tienda, menu);
        return true;
    }

}
