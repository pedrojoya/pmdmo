package es.iessaladillo.pedrojoya.pr060;

import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;

public class MainActivity extends SherlockActivity implements
        OnQueryTextListener {

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
        getSupportMenuInflater().inflate(R.menu.activity_main, menu);
        // Se obtiene la referencia a la SearchView.
        svBuscar = (SearchView) menu.findItem(R.id.mnuBuscar).getActionView();
        // La propia actividad será notificada cuando de realice la búsqueda.
        svBuscar.setOnQueryTextListener(this);
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

    // Cuando se cambia el texto en el SearchView.
    @Override
    public boolean onQueryTextChange(String arg0) {
        // No se hace nada.
        return false;
    }

    // Cuando se envía el término a la búsqueda.
    @Override
    public boolean onQueryTextSubmit(String query) {
        mostrarTostada(getString(R.string.buscar) + query);
        return true;
    }

    // Muestra una tostada.
    private void mostrarTostada(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT)
                .show();
    }

}
