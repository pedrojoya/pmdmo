package es.iessaladillo.pedrojoya.pr074;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    private SearchView svBuscar;

    // Cuando se crea la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Se llama al constructor del padre.
        super.onCreate(savedInstanceState);
        // Se establece el layout que mostrará la actividad.
        setContentView(R.layout.activity_main);
        // Se muestra el icono de navegación junto al icono de la aplicación.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // Al crear la primera vez el menú.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Se infla el menú a partir del XML.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        // Se obtiene la referencia a la SearchView.
        MenuItem item = menu.findItem(R.id.mnuBuscar);
        svBuscar = (SearchView) MenuItemCompat.getActionView(item);
        // Cuando de realice la búsqueda.
        svBuscar.setOnQueryTextListener(new OnQueryTextListener() {

            // Cuando se envía el término a la búsqueda.
            @Override
            public boolean onQueryTextSubmit(String query) {
                mostrarTostada(getString(R.string.buscar) + query);
                return true;
            }

            // Cuando cambia el texto.
            @Override
            public boolean onQueryTextChange(String query) {
                // No se hace nada.
                return false;
            }
        });
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
        default:
            return super.onOptionsItemSelected(item);
        }
        // Retorna que ya ha sido gestionado.
        return true;
    }

    // Muestra una tostada.
    private void mostrarTostada(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT)
                .show();
    }

}
