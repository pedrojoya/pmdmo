package es.iessaladillo.pedrojoya.pr055.actividades;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import es.iessaladillo.pedrojoya.pr055.R;
import es.iessaladillo.pedrojoya.pr055.fragmentos.AlumnoFragment;
import es.iessaladillo.pedrojoya.pr055.fragmentos.NotasFragment;

public class MainActivity extends SherlockFragmentActivity implements
        ActionBar.OnNavigationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Se configura para action bar para que tenga un spinner de navegación,
        // NO muestre el título (para que haya más espacio), pero sí muestre el
        // icono de navegación junto al icono de la aplicación.
        ActionBar ab = getSupportActionBar();
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);
        // Se crea el adaptador para el Spinner a partir de un array de
        // constantes de cadena. Se usará como layout uno similar a
        // android.R.layout.simple_spinner_dropdown_item pero con el texto en
        // blanco.
        SpinnerAdapter adaptador = ArrayAdapter.createFromResource(this,
                R.array.opciones, R.layout.dark_actionbar_spinner);
        // Se establece el adaptador y el listener para el spinner (que será la
        // propia actividad).
        ab.setListNavigationCallbacks(adaptador, this);
        // Si venimos de un estado anterior.
        if (savedInstanceState != null) {
            // Se coloca en la opción en la que estaba.
            ab.setSelectedNavigationItem(savedInstanceState.getInt("opcion"));
        }
    }

    // Al seleccionar un elemento del spinner de la action bar.
    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        // Se crea el fragmento correspondiente al elemento seleccionado.
        Fragment frg = null;
        switch (itemPosition) {
            case 0:
                frg = new AlumnoFragment();
                break;
            case 1:
                frg = new NotasFragment();
                break;
        }
        // Se muestra el fragmento en el contenedor del layout de la actividad.
        FragmentTransaction transaccion = getSupportFragmentManager()
                .beginTransaction();
        transaccion.replace(android.R.id.content, frg);
        transaccion.commit();
        // Se retorna que ya ha sido procesada la selección.
        return true;
    }

    // Al crear la primera vez el menú.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Se infla el menú a partir del XML.
        getSupportMenuInflater().inflate(R.menu.activity_main, menu);
        // Se retorna lo que devuelva la actividad.
        return super.onCreateOptionsMenu(menu);
    }

    // Cuando se pulsa un elemento del menú.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Dependiendo del item pulsado se realiza la acción deseada.
        switch (item.getItemId()) {
            case android.R.id.home:
                mostrarTostada(getString(R.string.ir_a_la_actividad_superior));
                break;
            case R.id.mnuAgregar:
                mostrarTostada(getString(R.string.agregar));
                break;
            case R.id.mnuCargar:
                mostrarTostada(getString(R.string.cargar));
                break;
            case R.id.mnuEditar:
                mostrarTostada(item.getTitle().toString());
                break;
            case R.id.mnuEliminar:
                mostrarTostada(getString(R.string.eliminar));
                break;
            case R.id.mnuBuscar:
                mostrarTostada(getString(R.string.buscar));
                break;
            case R.id.mnuCompartir:
                mostrarTostada(getString(R.string.compartir));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        // Retorna que ya ha sido gestionado.
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Almacena qué pestaña tenemos seleccionada.
        outState.putInt("opcion", getSupportActionBar()
                .getSelectedNavigationIndex());
        super.onSaveInstanceState(outState);
    }

    // Muestra una tostada.
    private void mostrarTostada(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT)
                .show();
    }

}
