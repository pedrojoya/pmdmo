package es.iessaladillo.pedrojoya.galileo.actividades;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseAnalytics;

import es.iessaladillo.pedrojoya.cursogalileotareasemana1.R;
import es.iessaladillo.pedrojoya.galileo.fragmentos.ComentariosFragment;
import es.iessaladillo.pedrojoya.galileo.fragmentos.TiendaInfoFragment;
import es.iessaladillo.pedrojoya.galileo.interfaces.MuestraProgreso;

public class TiendaActivity extends FragmentActivity implements MuestraProgreso {

    // Constantes públicas estáticas.
    public static String EXTRA_TIENDA = "objectIdTienda";

    // Vistas.
    private ActionBar barra;

    // Propiedades.
    private String objectIdTienda;
    private FragmentManager gestorFragmentos;
    private MenuItem mnuRefrescar;

    private boolean cargando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
        // Seguimos la estadísticas de la aplicación en Parse.
        ParseAnalytics.trackAppOpened(getIntent());
        // Se establece que la ActionBar muestre un icono para el conmutador.
        barra = getActionBar();
        barra.setDisplayHomeAsUpEnabled(true);
        barra.setHomeButtonEnabled(true);
        // Se obtiene el extra del Intent con el que ha sido llamada la
        // actividad.
        objectIdTienda = getIntent().getStringExtra(EXTRA_TIENDA);
        // Se obtiene el gestor de fragmentos.
        gestorFragmentos = getSupportFragmentManager();
        // Se carga el fragmento de InfoTienda.
        cargarInfoTiendaFragment();
        // Se carga el framento de comentarios de la tienda.
        cargarComentariosFragment();
    }

    // Carga el fragmento de InfoTienda.
    private void cargarInfoTiendaFragment() {
        // Se crea el fragmento.
        TiendaInfoFragment frg = new TiendaInfoFragment();
        // Se le establece como argumento la tienda.
        Bundle argumentos = new Bundle();
        argumentos.putString(TiendaInfoFragment.EXTRA_TIENDA, objectIdTienda);
        frg.setArguments(argumentos);
        // Se carga el fragmento en el contenedor correspodiente.
        gestorFragmentos.beginTransaction().replace(R.id.frmInfoTienda, frg)
                .commit();
    }

    // Carga el fragmento de comentarios sobre la tienda.
    private void cargarComentariosFragment() {
        // Se crea el fragmento.
        ComentariosFragment frg = new ComentariosFragment();
        // Se le establece como argumento la tienda.
        Bundle argumentos = new Bundle();
        argumentos.putString(ComentariosFragment.EXTRA_PARENT, objectIdTienda);
        frg.setArguments(argumentos);
        // Se carga el fragmento en el contenedor correspodiente.
        gestorFragmentos.beginTransaction()
                .replace(R.id.frmComentariosTienda, frg).commit();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Se infla el menú correspondiente.
        getMenuInflater().inflate(R.menu.activitity_tienda, menu);
        mnuRefrescar = menu.findItem(R.id.mnuRefrescar);
        mnuRefrescar.setActionView(R.layout.actionview_progreso);
        return super.onCreateOptionsMenu(menu);
    }

    public void mostrarProgreso(boolean valor) {
        cargando = valor;
        invalidateOptionsMenu();
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

}
