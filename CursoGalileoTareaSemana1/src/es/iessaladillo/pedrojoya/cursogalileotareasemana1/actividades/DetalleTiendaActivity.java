package es.iessaladillo.pedrojoya.cursogalileotareasemana1.actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.R;

public class DetalleTiendaActivity extends Activity implements OnClickListener {

    // Constantes públicas estáticas.
    public static String EXTRA_TIENDA = "tienda";

    // Vistas.
    private TextView lblNombre;
    private ImageButton btnLlamar;
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
        btnLlamar = (ImageButton) findViewById(R.id.btnLlamar);
        lblTelefono = (TextView) findViewById(R.id.lblTelefono);
        lblWebsite = (TextView) findViewById(R.id.lblWebsite);
        lblEmail = (TextView) findViewById(R.id.lblEmail);
        // Algunos textos se muestran como enlaces.
        Linkify.addLinks(lblTelefono, Linkify.PHONE_NUMBERS);
        Linkify.addLinks(lblWebsite, Linkify.WEB_URLS);
        Linkify.addLinks(lblEmail, Linkify.EMAIL_ADDRESSES);
        // La propia actividad será quien responda a la pulsación del botón.
        btnLlamar.setOnClickListener(this);

        // Se obtiene el extra del Intent con el que ha sido llamada la
        // actividad.
        Intent i = getIntent();
        if (i != null && i.hasExtra(EXTRA_TIENDA)) {
            lblNombre.setText(i.getStringExtra(EXTRA_TIENDA));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detalle_tienda, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btnLlamar:
            // Se crea un intent implícito para mostrar el dial, que recibe como
            // data la URI con el número.
            /*
             * String telefono = lblTelefono.getText().toString(); Intent i =
             * new Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + telefono));
             * startActivity(i);
             */
            // Se pone este código SOLO para poder probar la actividad de
            // detalle de foto.
            Intent intent = new Intent(getApplicationContext(),
                    DetalleFotoActivity.class);
            startActivity(intent);
            break;
        default:
            break;
        }

    }

}
