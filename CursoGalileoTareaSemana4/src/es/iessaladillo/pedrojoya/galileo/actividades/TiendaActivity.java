package es.iessaladillo.pedrojoya.galileo.actividades;

import java.lang.reflect.Field;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import com.parse.ParseAnalytics;

import es.iessaladillo.pedrojoya.galileo.R;
import es.iessaladillo.pedrojoya.galileo.datos.Tienda;
import es.iessaladillo.pedrojoya.galileo.fragmentos.ComentariosFragment;
import es.iessaladillo.pedrojoya.galileo.fragmentos.TiendaInfoFragment;

public class TiendaActivity extends ActionBarActivity {

    // Constantes públicas estáticas.
    public static String EXTRA_TIENDA = "objectIdTienda";

    // Vistas.
    private ActionBar barra;

    // Propiedades.
    private Tienda tienda;
    private FragmentManager gestorFragmentos;

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
        tienda = getIntent().getParcelableExtra(EXTRA_TIENDA);
        // Se obtiene el gestor de fragmentos.
        gestorFragmentos = getSupportFragmentManager();
        // Se carga el fragmento de InfoTienda.
        cargarInfoTiendaFragment();
        // Se carga el framento de comentarios de la tienda.
        cargarComentariosFragment();
        // Se establece que se muestre el menú de overflow incluso en
        // dispositivos que tengan tecla física de menú.
        overflowEnDispositivoConTeclaMenu();
    }

    // Carga el fragmento de InfoTienda.
    private void cargarInfoTiendaFragment() {
        // Se crea el fragmento.
        TiendaInfoFragment frg = new TiendaInfoFragment();
        // Se le establece como argumento la tienda.
        Bundle argumentos = new Bundle();
        argumentos.putParcelable(TiendaInfoFragment.EXTRA_TIENDA, tienda);
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
        argumentos.putString(ComentariosFragment.EXTRA_PARENT,
                tienda.getObjectId());
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
        return super.onCreateOptionsMenu(menu);
    }

    // Activa el ítem de overflow en dispositivos con botón físico de menú.
    private void overflowEnDispositivoConTeclaMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignorar
        }
    }

}
