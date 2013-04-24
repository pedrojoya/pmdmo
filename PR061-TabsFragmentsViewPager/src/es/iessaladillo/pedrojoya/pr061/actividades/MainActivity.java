package es.iessaladillo.pedrojoya.pr061.actividades;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import es.iessaladillo.pedrojoya.pr061.R;
import es.iessaladillo.pedrojoya.pr061.fragmentos.PaginaFragment;

public class MainActivity extends FragmentActivity implements
        ActionBar.TabListener, OnPageChangeListener {

    ActionBar actionBar;
    PaginasAdapter adaptador;
    ViewPager paginador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se configura la action bar para que muestre pestañas.
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // Se crea el adaptador que mostrará el fragmento correspondiente y se
        // le asigna al paginador.
        adaptador = new PaginasAdapter(getSupportFragmentManager());
        paginador = (ViewPager) findViewById(R.id.vpPaginador);
        paginador.setAdapter(adaptador);
        // Se establece que la actividad actúa como objeto //listener// cuando
        // se cambie la página en el paginador.
        paginador.setOnPageChangeListener(this);
        // Se crea una pestaña para cada página, cuyo título corresponde al de
        // la página y se establece la propia actividad como objeto listener que
        // será notificado cuando se seleccione una pestaña.
        for (int i = 0; i < adaptador.getCount(); i++) {
            ActionBar.Tab tab = actionBar.newTab();
            tab.setText(adaptador.getPageTitle(i));
            tab.setTabListener(this);
            actionBar.addTab(tab);
        }
        // Si se viene de un estado anterior se coloca en el número de pestaña
        // salvado.
        if (savedInstanceState != null) {
            actionBar.setSelectedNavigationItem(savedInstanceState.getInt(
                    "tab", 0));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se almacena el número de pestaña actual.
        outState.putInt("tab", actionBar.getSelectedNavigationIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Al seleccionar una pestaña
    @Override
    public void onTabSelected(ActionBar.Tab tab,
            FragmentTransaction fragmentTransaction) {
        // Se coloca al paginador en dicha página.
        paginador.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab,
            FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab,
            FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    // Al cambiar la página en el paginador. Recibe el número de página.
    @Override
    public void onPageSelected(int position) {
        // Se establece como pestaña activa la correspondiente a dicho número de
        // página.
        actionBar.setSelectedNavigationItem(position);
    }

    // Adaptador para el paginador de fragmentos.
    public class PaginasAdapter extends FragmentPagerAdapter {

        // Constantes.
        private int NUM_PAGINAS = 5;

        // Constructor. Recibe el gestor de fragmentos.
        public PaginasAdapter(FragmentManager fm) {
            super(fm);
        }

        // Retorna el fragmento correspondiente a una página. Recibe el número
        // de página.
        @Override
        public Fragment getItem(int position) {
            // Se crea el fragmento y se le pasa como argumento el número de
            // pestaña para que lo escriba en su TextView.
            Fragment frgPagina = new PaginaFragment();
            Bundle parametros = new Bundle();
            parametros.putInt(PaginaFragment.PAR_NUM_PAGINA, position + 1);
            frgPagina.setArguments(parametros);
            return frgPagina;
        }

        // Retorna el número de páginas.
        @Override
        public int getCount() {
            return NUM_PAGINAS;
        }

        // Retorna el título de una página. Recibe el número de página.
        @Override
        public CharSequence getPageTitle(int position) {
            return getString(R.string.pestana) + (position + 1);
        }
    }

}