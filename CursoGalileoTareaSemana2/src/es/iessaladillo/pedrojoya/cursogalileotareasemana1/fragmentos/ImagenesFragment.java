package es.iessaladillo.pedrojoya.cursogalileotareasemana1.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.R;

public class ImagenesFragment extends Fragment implements OnPageChangeListener {

    private PaginasAdapter adaptador;
    private ViewPager paginador;
    private FragmentManager gestor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla el layout.
        View v = inflater.inflate(R.layout.fragment_imagenes, container, false);
        paginador = (ViewPager) v.findViewById(R.id.vpPaginador);
        // Se crea el adaptador que mostrará el fragmento correspondiente y se
        // le asigna al paginador.
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        gestor = getChildFragmentManager(); // MUY IMPORTANTE, SINO SE MUESTRA
                                            // EN BLANCO LA PRIMERA PÁGINA.
        adaptador = new PaginasAdapter(gestor);
        paginador.setAdapter(adaptador);
        // Se establece que la actividad actúa como objeto //listener// cuando
        // se cambie la página en el paginador.
        paginador.setOnPageChangeListener(this);
        paginador.setCurrentItem(0);
        super.onActivityCreated(savedInstanceState);
    }

    // Clase adaptadora de páginas
    private class PaginasAdapter extends FragmentPagerAdapter {

        public static final int NUM_PAGES = 5;
        int[] resIdFotos = new int[] { R.drawable.retrato1,
                R.drawable.retrato2, R.drawable.retrato3, R.drawable.retrato4,
                R.drawable.retrato5 };

        // Constructor.
        public PaginasAdapter(FragmentManager fm) {
            super(fm);
        }

        // Retorna el fragmento que debe mostrarse.
        @Override
        public Fragment getItem(int position) {
            // Se crea el objeto Fragment del fragmento correspondiente,
            // pasándolo el resId de la foto a mostrar.
            Fragment frgPagina = new PaginaFragment();
            // Se le pasa como parámetro el número de página.
            Bundle parametros = new Bundle();
            parametros.putInt(PaginaFragment.PAR_RESID_FOTO,
                    resIdFotos[position]);
            frgPagina.setArguments(parametros);
            // Se retorna el fragmento que debe mostrar el paginador
            return frgPagina;
        }

        // Retorna el número de páginas.
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return NUM_PAGES;
        }

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub

    }

}
