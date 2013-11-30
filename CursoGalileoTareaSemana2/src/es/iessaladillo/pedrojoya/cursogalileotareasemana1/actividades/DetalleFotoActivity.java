package es.iessaladillo.pedrojoya.cursogalileotareasemana1.actividades;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.R;

public class DetalleFotoActivity extends Activity {

    // Vistas.
    private ActionBar barra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_foto);
        // Se establece que la ActionBar muestre un icono para el conmutador.
        barra = getActionBar();
        barra.setDisplayHomeAsUpEnabled(true);
        barra.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Se infla el menú.
        getMenuInflater().inflate(R.menu.detalle_foto, menu);
        // Se indica que ya se ha tramitado el evento.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Dependiendo del ítem del menú pulsado.
        switch (item.getItemId()) {
        case android.R.id.home:
            // Al pulsar sobre el icono de navegación se vuelve a la actividad
            // anterior.
            onBackPressed();
            break;
        case R.id.mnuFavorito:
            // marcarComoFavorito();
            break;
        case R.id.mnuCompartir:
            // Se comparte la foto.
            compartir();
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    // Envía un intent para compartir la foto mostrada.
    private void compartir() {
        // Se obtiene la uri de la foto a compartir (OJO no poner la extensión).
        Uri uri = Uri.parse("android.resource://" + getPackageName()
                + "/drawable/centro_comercial");
        // Se crea un intent con la acción de enviar, el tipo imagen en jpg y se
        // almacena en el extra EXTRA_STREAM la uri de la Foto.
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpg");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        // Se envía el intent a través de selector de aplicación.
        startActivity(Intent.createChooser(intent,
                getString(R.string.elija_aplicacion)));
    }

}
