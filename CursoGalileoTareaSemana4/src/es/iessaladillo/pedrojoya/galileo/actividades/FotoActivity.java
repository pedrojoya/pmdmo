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
import es.iessaladillo.pedrojoya.galileo.datos.Foto;
import es.iessaladillo.pedrojoya.galileo.fragmentos.ComentariosFragment;
import es.iessaladillo.pedrojoya.galileo.fragmentos.FotoInfoFragment;

public class FotoActivity extends ActionBarActivity {

    // Constantes.
    public static final String EXTRA_FOTO = "foto";

    // Propieades.
    private ActionBar barra;
    private FragmentManager gestorFragmentos;
    private Foto foto;

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
        foto = getIntent().getParcelableExtra(EXTRA_FOTO);
        // Se obtiene el gestor de fragmentos.
        gestorFragmentos = getSupportFragmentManager();
        // Se carga el fragmento de info de la foto.
        cargarInfoFotoFragment();
        // Se carga el framento de comentarios de la foto.
        cargarComentariosFragment();
        // Se establece que se muestre el menú de overflow incluso en
        // dispositivos que tengan tecla física de menú.
        overflowEnDispositivoConTeclaMenu();
    }

    // Carga el fragmento de info de la foto.
    private void cargarInfoFotoFragment() {
        // Se crea el fragmento.
        FotoInfoFragment frg = new FotoInfoFragment();
        // Se le establece como argumento la tienda.
        Bundle argumentos = new Bundle();
        argumentos.putParcelable(FotoInfoFragment.EXTRA_FOTO, foto);
        frg.setArguments(argumentos);
        // Se carga el fragmento en el contenedor correspodiente.
        gestorFragmentos.beginTransaction().replace(R.id.frmInfoFoto, frg)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Se infla el menú.
        getMenuInflater().inflate(R.menu.activity_foto, menu);
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

    // Carga el fragmento de comentarios sobre la foto.
    private void cargarComentariosFragment() {
        // Se crea el fragmento.
        ComentariosFragment frg = new ComentariosFragment();
        // Se le establece como argumento la tienda.
        Bundle argumentos = new Bundle();
        argumentos.putString(ComentariosFragment.EXTRA_PARENT,
                foto.getObjectId());
        frg.setArguments(argumentos);
        // Se carga el fragmento en el contenedor correspodiente.
        gestorFragmentos.beginTransaction()
                .replace(R.id.frmComentariosFoto, frg).commit();
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
