package es.iessaladillo.pedrojoya.cursogalileotareasemana1.actividades;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseAnalytics;

import es.iessaladillo.pedrojoya.cursogalileotareasemana1.R;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.fragmentos.ComentariosFragment;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.fragmentos.FotoInfoFragment;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.interfaces.MuestraProgreso;

public class FotoActivity extends FragmentActivity implements MuestraProgreso {

    // Constantes.
    public static final String EXTRA_FOTO = "foto";

    // Vistas.
    private ActionBar barra;

    private FragmentManager gestorFragmentos;

    private String objectIdFoto;

    private boolean cargando;

    private MenuItem mnuRefrescar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);
        // Seguimos la estadísticas de la aplicación en Parse.
        ParseAnalytics.trackAppOpened(getIntent());
        // Se establece que la ActionBar muestre un icono para el conmutador.
        barra = getActionBar();
        barra.setDisplayHomeAsUpEnabled(true);
        barra.setHomeButtonEnabled(true);
        // Se obtiene el extra del Intent con el que ha sido llamada la
        // actividad.
        objectIdFoto = getIntent().getStringExtra(EXTRA_FOTO);
        // Se obtiene el gestor de fragmentos.
        gestorFragmentos = getSupportFragmentManager();
        // Se carga el fragmento de info de la foto.
        cargarInfoFotoFragment();
        // Se carga el framento de comentarios de la foto.
        cargarComentariosFragment();
    }

    // Carga el fragmento de info de la foto.
    private void cargarInfoFotoFragment() {
        // Se crea el fragmento.
        FotoInfoFragment frg = new FotoInfoFragment();
        // Se le establece como argumento la tienda.
        Bundle argumentos = new Bundle();
        argumentos.putString(FotoInfoFragment.EXTRA_FOTO, objectIdFoto);
        frg.setArguments(argumentos);
        // Se carga el fragmento en el contenedor correspodiente.
        gestorFragmentos.beginTransaction().replace(R.id.frmInfoFoto, frg)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Se infla el menú.
        getMenuInflater().inflate(R.menu.activity_foto, menu);
        mnuRefrescar = menu.findItem(R.id.mnuRefrescar);
        mnuRefrescar.setActionView(R.layout.actionview_progreso);
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
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    // Llamado automáticamente tras cada llamada a invalidateOptionsMenu()
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (cargando) {
            mnuRefrescar.setVisible(true);
        } else {
            mnuRefrescar.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void mostrarProgreso(boolean valor) {
        cargando = valor;
        invalidateOptionsMenu();
    }

    // Carga el fragmento de comentarios sobre la foto.
    private void cargarComentariosFragment() {
        // Se crea el fragmento.
        ComentariosFragment frg = new ComentariosFragment();
        // Se le establece como argumento la tienda.
        Bundle argumentos = new Bundle();
        argumentos.putString(ComentariosFragment.EXTRA_PARENT, objectIdFoto);
        frg.setArguments(argumentos);
        // Se carga el fragmento en el contenedor correspodiente.
        gestorFragmentos.beginTransaction()
                .replace(R.id.frmComentariosFoto, frg).commit();
    }

}
