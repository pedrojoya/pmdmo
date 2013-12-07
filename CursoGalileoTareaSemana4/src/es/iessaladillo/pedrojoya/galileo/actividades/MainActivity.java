package es.iessaladillo.pedrojoya.galileo.actividades;

import java.lang.reflect.Field;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.ParseAnalytics;

import es.iessaladillo.pedrojoya.galileo.R;
import es.iessaladillo.pedrojoya.galileo.dialogos.FotoDialogFragment;
import es.iessaladillo.pedrojoya.galileo.fragmentos.FotosListaFragment;
import es.iessaladillo.pedrojoya.galileo.fragmentos.ImagenesListaFragment;
import es.iessaladillo.pedrojoya.galileo.fragmentos.PortadaFragment;
import es.iessaladillo.pedrojoya.galileo.fragmentos.TiendasFragment;

public class MainActivity extends ActionBarActivity implements
        OnItemClickListener, FotoDialogFragment.Callback {

    private static final int ACCION_HACER_FOTO = 0;
    private static final int ACCION_DESDE_GALERIA = 1;
    private ListView lstPanelNavegacion;
    private ArrayAdapter<CharSequence> adaptador;
    private String tituloContenido;
    private DrawerLayout panelNavegacion;
    private FragmentManager gestorFragmentos;
    private String tituloPanelNavegacion;
    private ActionBarDrawerToggle conmutadorPanelNavegacion;
    private Fragment frg = null;
    private ActionBar barra;
    private Menu mnuActividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Seguimos la estadísticas de la aplicación en Parse.
        ParseAnalytics.trackAppOpened(getIntent());
        // Se obtiene el gestor de fragmentos
        gestorFragmentos = getSupportFragmentManager();
        // Obtengo la action bar.
        barra = getSupportActionBar();
        // Inicialmente el título del panel de navegación, como el del contenido
        // principal coincide con el de la actividad.
        tituloContenido = tituloPanelNavegacion = getTitle().toString();
        // Se establece el drawable que actúa como sombra del contenido
        // principal cuando se abre el panel de navegación.
        panelNavegacion = (DrawerLayout) findViewById(R.id.drlPanelNavegacion);
        // panelNavegacion.setDrawerShadow(R.drawable.drawer_shadow,
        // GravityCompat.START);
        // Se carga la lista del panel de navegación y se indica que sea la
        // propia la que responda a la pulsación sobre los elementos de la
        // lista.
        lstPanelNavegacion = (ListView) findViewById(R.id.lstNavegacion);
        adaptador = ArrayAdapter
                .createFromResource(this, R.array.opcionesNavegacion,
                        android.R.layout.simple_list_item_1);
        lstPanelNavegacion.setAdapter(adaptador);
        lstPanelNavegacion.setOnItemClickListener(this);
        // Se crea el objeto de vínculo entre la ActionBar y el panel de
        // navegación. El constructor recibe la actividad, el panel de
        // navegación, el icono de activación en la ActionBar, el texto de
        // apertura y el de cierre. El objeto debe implementar ciertos métodos
        // de la interfaz DrawerLayout.DrawerListener.
        conmutadorPanelNavegacion = new ActionBarDrawerToggle(this,
                panelNavegacion, R.drawable.ic_navigation_drawer,
                R.string.abrir_panel_de_navegacion,
                R.string.cerrar_panel_de_navegacion) {
            // Al terminar de cerrarse el panel de navegación.
            public void onDrawerClosed(View view) {
                // Se reestablece el título de la ActionBar al valor que tuviera
                // antes de abrir el panel de navegación.
                getSupportActionBar().setTitle(tituloContenido);
                // Se recarga el menú de la ActionBar.
                invalidateOptionsMenu();
            }

            // Al terminar de abrirse el panel de navegación.
            public void onDrawerOpened(View drawerView) {
                // Se establece como título de la ActionBar el nombre de la
                // actividad.
                getSupportActionBar().setTitle(tituloPanelNavegacion);
                // Se recarga el menú de la ActionBar.
                invalidateOptionsMenu();
            }
        };
        // Se establece que la ActionBar muestre un icono para el conmutador.
        barra.setDisplayHomeAsUpEnabled(true);
        barra.setHomeButtonEnabled(true);
        // Se vincula el conmutador con el panel de navegación.
        panelNavegacion.setDrawerListener(conmutadorPanelNavegacion);
        // Se selecciona el primer elemento de la lista del panel de navegación
        // para que el contenedor de la actividad principal no se muestre vacío
        // inicialmente.
        if (savedInstanceState == null) {
            panelNavegacionItemSelected(0);
        }
        // Se establece que se muestre el menú de overflow incluso en
        // dispositivos que tengan tecla física de menú.
        overflowEnDispositivoConTeclaMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        mnuActividad = menu;
        return true;
    }

    // Cuando se selecciona un ítem de menú en la ActionBar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Si se ha pulsado sobre icono de activación del panel de navegación no
        // se debe hacer nada más, ya que el activador ya se encarga por
        // nosotros de
        // abrir o cerrar el panel de navegación.
        if (conmutadorPanelNavegacion.onOptionsItemSelected(item)) {
            return true;
        }
        // En cualquier otro caso se procesa la selección.
        switch (item.getItemId()) {
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    // Al hacer click sobre un elemento de la lista del panel de navegación.
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        // Se selecciona el elemento correspondiente.
        panelNavegacionItemSelected(position);
    }

    // Respuesta a la selección de un elemento en el panel de navegación.
    private void panelNavegacionItemSelected(int position) {
        // Se determina qué opción de navegación se ha seleccionado.
        String[] opciones = getResources().getStringArray(
                R.array.opcionesNavegacion);
        // Dependiendo de la opción seleccionada.
        switch (position) {
        case 0:
            frg = new PortadaFragment();
            break;
        case 1:
            frg = new TiendasFragment();
            break;
        case 2:
            frg = new FotosListaFragment();
            break;
        case 3:
            frg = new ImagenesListaFragment();
            break;
        default:
            frg = new PortadaFragment();
            break;
        }
        // Se carga en la actividad principal el fragmento correspondiente.
        gestorFragmentos.beginTransaction().replace(R.id.frmContenido, frg)
                .commit();
        // Se marca como seleccionado dicho elemento en la lista.
        lstPanelNavegacion.setItemChecked(position, true);
        // Se actualiza el título que debe mostrar la ActionBar, acorde al
        // fragmento que se ha cargado.
        tituloContenido = opciones[position];
        getActionBar().setTitle(tituloContenido);
        // Se cierra el panel de navegación.
        panelNavegacion.closeDrawer(lstPanelNavegacion);
    }

    // Si se usa un conmutador del panel de navegación, se debe
    // sincronizar con la ActionBar una vez creada la actividad.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        conmutadorPanelNavegacion.syncState();
    }

    // Si se usa un conmutador del panel de navegación, se le debe
    // informar de la nueva configuración.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        conmutadorPanelNavegacion.onConfigurationChanged(newConfig);
    }

    @Override
    public void onAccionClick(int which) {
        switch (which) {
        case ACCION_HACER_FOTO:
            ((FotosListaFragment) frg).hacerFoto();
            break;
        case ACCION_DESDE_GALERIA:
            ((FotosListaFragment) frg).desdeGaleria();
        }
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
