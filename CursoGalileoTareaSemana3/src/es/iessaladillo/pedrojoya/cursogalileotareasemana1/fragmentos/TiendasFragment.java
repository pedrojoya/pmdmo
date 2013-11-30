package es.iessaladillo.pedrojoya.cursogalileotareasemana1.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.R;

public class TiendasFragment extends Fragment {

    // Vistas.
    private ActionBar barra;
    private ActionBarActivity actividad;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Se establece que el fragmento aportará menús.
        setHasOptionsMenu(true);
        // Se configura la ActionBar para navegación por pestañas.
        actividad = (ActionBarActivity) getActivity();
        barra = actividad.getSupportActionBar();
        barra.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // Se crean las pestañas.
        ActionBar.Tab tabListado = barra.newTab();
        tabListado.setText(R.string.listado);
        ActionBar.Tab tabMapa = barra.newTab();
        tabMapa.setText(R.string.mapa);
        // Se crean los fragmentos de cada pestaña.
        Fragment frgListado = new TiendasListaFragment();
        Fragment frgNotas = new TiendasMapaFragment();
        // Se crea y asocia el listener a cada pestaña.
        tabListado.setTabListener(new GestorTabListener(frgListado));
        tabMapa.setTabListener(new GestorTabListener(frgNotas));
        // Se añaden las pestañas a la action bar.
        barra.addTab(tabListado);
        barra.addTab(tabMapa);
        // Si venimos de un estado anterior.
        if (savedInstanceState != null) {
            // Se coloca en la pestaña en la que estaba.
            barra.setSelectedNavigationItem(savedInstanceState.getInt("tab"));
        } else {
            barra.setSelectedNavigationItem(0);
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        // Se eliminan las tabs (para que no se vean en la actividad cuando en
        // el navigation drawer se selecciona la opción correpondiente a otro
        // fragmento) y se establece el modo de navegación normal.
        barra.removeAllTabs();
        barra.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla el layout correspondiente.
        View v = inflater.inflate(R.layout.fragment_tiendas, container, false);
        // Se retorna la vista que debe mostrar el fragmento.
        return v;
    }

    // Cuando se va a recrear el fragmento.
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se almacena el número de pestaña actual.
        outState.putInt("tab", barra.getSelectedNavigationIndex());
    }

    // Clase Gestor de pestañas.
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
            // Inserto el fragmento correspondiente en el contenedor,
            // reemplazando el que tuviera.
            ft.replace(R.id.frlTiendas, fragment);
        }

        // Al dejar de estar activa una pestaña.
        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            // Quito el fragmento del contenedor.
            ft.remove(fragment);
        }
    }

}
