package es.iessaladillo.pedrojoya.pr071.actividades;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import es.iessaladillo.pedrojoya.pr071.R;
import es.iessaladillo.pedrojoya.pr071.fragmentos.AlumnoFragment;
import es.iessaladillo.pedrojoya.pr071.fragmentos.NotasFragment;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        // Se configura para action bar para que tenga pestañas de navegación,
        // NO muestre el título (para que haya más espacio), pero sí muestre el
        // icono de navegación junto al icono de la aplicación.
        ActionBar ab = getSupportActionBar();
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);
        // Se crean las pestañas.
        ActionBar.Tab tabAlumno = ab.newTab();
        tabAlumno.setText(R.string.alumno);
        ActionBar.Tab tabNotas = ab.newTab();
        tabNotas.setText(R.string.notas);
        // Se crean los fragmentos de cada pestaña.
        Fragment frgAlumno = new AlumnoFragment();
        Fragment frgNotas = new NotasFragment();
        // Se crea y asocia el listener a cada pestaña.
        tabAlumno.setTabListener(new GestorTabListener(frgAlumno));
        tabNotas.setTabListener(new GestorTabListener(frgNotas));
        // Se añaden las pestañas a la action bar
        ab.addTab(tabAlumno);
        ab.addTab(tabNotas);
        // Si venimos de un estado anterior.
        if (savedInstanceState != null) {
            // Se coloca en la pestaña en la que estaba.
            ab.setSelectedNavigationItem(savedInstanceState.getInt("tab"));
        }
    }

    // Al crear el menú.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Se infla el menú a partir del XML.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Cuando se pulsa un elemento del menú.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Dependiendo del item pulsado se realiza la acción deseada.
        switch (item.getItemId()) {
        case android.R.id.home:
            Toast.makeText(getApplicationContext(),
                    R.string.ir_a_la_actividad_superior, Toast.LENGTH_SHORT)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Almacena qué pestaña tenemos seleccionada.
        outState.putInt("tab", getSupportActionBar()
                .getSelectedNavigationIndex());
        super.onSaveInstanceState(outState);
    }

    // Gestor de pestañas.
    public class GestorTabListener implements ActionBar.TabListener {

        // Fragmento correspondiente de la pestaña.
        private Fragment fragment;

        // Constructor.
        public GestorTabListener(Fragment fg) {
            this.fragment = fg;
        }

        // Al volver a seleccionar una pestaña.
        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            // Normalmente no se hace nada.
        }

        // Al convertir en activa una pestaña.
        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            // Inserto el fragmento correspondiente en el contenedor (en este
            // caso el raíz del layout de la actividad), reemplazando el que
            // tuviera.
            ft.replace(R.id.frlPrincipal, fragment);
        }

        // Al dejar de estar activa una pestaña.
        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            // Quito el fragmento del contenedor.
            ft.remove(fragment);
        }

    }

}
