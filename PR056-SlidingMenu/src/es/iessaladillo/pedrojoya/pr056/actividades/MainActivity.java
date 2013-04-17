package es.iessaladillo.pedrojoya.pr056.actividades;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import es.iessaladillo.pedrojoya.pr056.R;
import es.iessaladillo.pedrojoya.pr056.fragmentos.DetalleFragment;
import es.iessaladillo.pedrojoya.pr056.fragmentos.MenuFragment;
import es.iessaladillo.pedrojoya.pr056.fragmentos.MenuFragment.OnAlbumSelectedListener;
import es.iessaladillo.pedrojoya.pr056.modelos.Album;

public class MainActivity extends SlidingFragmentActivity implements
        OnAlbumSelectedListener {

    // Constantes.
    public static final String EXTRA_ALBUM = "es.iessaladillo.pr056.ALBUM";

    // Variables miembro.
    protected ListFragment mFrag;
    private CanvasTransformer mTransformer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Se establece el layout que mostrará la actividad.
        setContentView(R.layout.activity_main);
        // La action bar mostrará el icono de up.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Se establece el layout que mostrará el sliding menu.
        setBehindContentView(R.layout.menu);
        // Si no procede de un estado anterior.
        if (savedInstanceState == null) {
            // Se carga el fragmento para el sliding menú.
            FragmentTransaction t = this.getSupportFragmentManager()
                    .beginTransaction();
            Fragment frgMenu = new MenuFragment();
            t.replace(R.id.menu_frame, frgMenu);
            t.commit();
        }
        // Se personaliza el sliding menu.
        SlidingMenu sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width); // Anchura sombra.
        sm.setShadowDrawable(R.drawable.shadow); // Sombra.
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset); // Margen.
        sm.setFadeDegree(0.35f); // Grado de fade in/out.
        // Toda la pantalla es zona de desplazamiento.
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // La action bar también se desplaza al desplazar el menú.
        setSlidingActionBarEnabled(true);
        // Se realiza una animación de zoom al mostrar el sliding menú.
        mTransformer = new CanvasTransformer() {
            @Override
            public void transformCanvas(Canvas canvas, float percentOpen) {
                float scale = (float) (percentOpen * 0.25 + 0.75);
                canvas.scale(scale, scale, canvas.getWidth() / 2,
                        canvas.getHeight() / 2);
            }
        };
        sm.setBehindScrollScale(0.0f);
        sm.setBehindCanvasTransformer(mTransformer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toggle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Cuando se selecciona un álbum en el sliding menu.
    @Override
    public void onAlbumSelected(Album album) {
        // Cierro el sliding menú.
        toggle();
        // Muestro el detalle del álbum.
        mostrarFragmentoDetalle(album);
    }

    // Muestra el fragmento de detalle.
    public void mostrarFragmentoDetalle(Album album) {
        // Inicio la transacción.
        FragmentTransaction transaccion = getSupportFragmentManager()
                .beginTransaction();
        // Creo una nueva instancia del fragmento de detalle.
        Fragment frgDetalle = new DetalleFragment();
        // Le paso el album como argumento al fragmento.
        Bundle argumentos = new Bundle();
        argumentos.putParcelable(MainActivity.EXTRA_ALBUM, album);
        frgDetalle.setArguments(argumentos);
        // Añado a la transacción el colocar el fragmento en el FrameLayout.
        transaccion.replace(R.id.flDetalle, frgDetalle);
        transaccion.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaccion.addToBackStack("prueba");
        // Finalizo la transacción.
        transaccion.commit();
    }

}