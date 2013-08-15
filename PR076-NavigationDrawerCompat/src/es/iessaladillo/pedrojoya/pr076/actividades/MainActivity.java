package es.iessaladillo.pedrojoya.pr076.actividades;

import java.util.ArrayList;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import es.iessaladillo.pedrojoya.pr076.R;
import es.iessaladillo.pedrojoya.pr076.adaptadores.AlbumesAdapter;
import es.iessaladillo.pedrojoya.pr076.fragmentos.DetalleFragment;
import es.iessaladillo.pedrojoya.pr076.modelos.Album;

public class MainActivity extends ActionBarActivity implements
        OnItemClickListener {

    // Constantes.
    private static final String PANEL_YA_ABIERTO = "prefPanelYaAbierto";

    // Variables miembro.
    private DrawerLayout panelNavegacion;
    private ListView lstPanelNavegacion;
    private ActionBarDrawerToggle conmutadorPanelNavegacion;
    private CharSequence tituloPanelNavegacion;
    private CharSequence tituloContenido;
    private AlbumesAdapter adaptador;
    private FragmentManager gestorFragmentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtiene el gestor de fragmentos
        gestorFragmentos = getSupportFragmentManager();
        // Inicialmente el título del panel de navegación, como el del contenido
        // principal coincide con el de la actividad.
        tituloContenido = tituloPanelNavegacion = getTitle();
        // Se establecemos el drawable que actúa como sombra del contenido
        // principal cuando se abre el panel de navegación.
        panelNavegacion = (DrawerLayout) findViewById(R.id.drawer_layout);
        panelNavegacion.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);
        // Se carga la lista del panel de navegación y se indica que sea la
        // propia la que responda a la pulsación sobre los elementos de la
        // lista.
        lstPanelNavegacion = (ListView) findViewById(R.id.left_drawer);
        adaptador = new AlbumesAdapter(this, getDatos());
        lstPanelNavegacion.setAdapter(adaptador);
        lstPanelNavegacion.setOnItemClickListener(this);
        // Se crea el objeto de vínculo entre la ActionBar y el panel de
        // navegación. El constructor recibe la actividad, el panel de
        // navegación, el icono de activación en la ActionBar, el texto de
        // apertura y el de cierre. El objeto debe implementar ciertos métodos
        // de la interfaz DrawerLayout.DrawerListener.
        conmutadorPanelNavegacion = new ActionBarDrawerToggle(this,
                panelNavegacion, R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // Se vincula el conmutador con el panel de navegación.
        panelNavegacion.setDrawerListener(conmutadorPanelNavegacion);
        // Se selecciona el primer elemento de la lista del panel de navegación
        // para que el contenedor de la actividad principal no se muestre vacío
        // inicialmente.
        if (savedInstanceState == null) {
            panelNavegacionItemSelected(0);
        }
        // Se obtiene la preferencia PANEL_YA_ABIERTO (false por defecto)
        SharedPreferences preferencias = getPreferences(MODE_PRIVATE);
        if (!preferencias.getBoolean(PANEL_YA_ABIERTO, false)) {
            // Se abre el panel de navegación con su lista.
            panelNavegacion.openDrawer(lstPanelNavegacion);
            // Se actualiza la preferencia.
            SharedPreferences.Editor editor = preferencias.edit();
            editor.putBoolean(PANEL_YA_ABIERTO, true);
            editor.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Llamado automáticamente tras cada llamada a invalidateOptionsMenu()
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Si el panel de navegación está abierto, se ocultan los ítems de menú
        // de la
        // ActionBar relacionados con contenido (ya que éste está oculto tras el
        // panel de navegación).
        boolean abierto = panelNavegacion.isDrawerOpen(lstPanelNavegacion);
        menu.findItem(R.id.mnuBuscar).setVisible(!abierto);
        return super.onPrepareOptionsMenu(menu);
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
        case R.id.mnuBuscar:
            // Se crea un intent explícito para buscar en Internet el título
            // de la ActionBar.
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, getSupportActionBar()
                    .getTitle());
            // Se comprueba si hay alguna aplicación que pueda tratar el
            // intent.
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.sin_navegador, Toast.LENGTH_LONG)
                        .show();
            }
            return true;
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
        // Se obtienen los datos del álbum sobre el que se ha pulsado.
        Album album = (Album) lstPanelNavegacion.getItemAtPosition(position);
        // Se carga en la actividad principal el fragmento correspondiente.
        Fragment frgDetalle = new DetalleFragment();
        Bundle args = new Bundle();
        args.putParcelable(DetalleFragment.ARG_ALBUM, album);
        frgDetalle.setArguments(args);
        gestorFragmentos.beginTransaction()
                .replace(R.id.content_frame, frgDetalle).commit();
        // Se marca como seleccionado dicho elemento en la lista.
        lstPanelNavegacion.setItemChecked(position, true);
        // Se actualiza el título que debe mostrar la ActionBar, acorde al
        // fragmento que se ha cargado.
        tituloContenido = album.getNombre();
        getSupportActionBar().setTitle(tituloContenido);
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

    // Creo los datos para la lista.
    private ArrayList<Album> getDatos() {
        ArrayList<Album> albumes = new ArrayList<Album>();
        albumes.add(new Album(R.drawable.veneno, "Veneno", "1977"));
        albumes.add(new Album(R.drawable.mecanico, "Seré mecánico por ti",
                "1981"));
        albumes.add(new Album(R.drawable.cantecito, "Echate un cantecito",
                "1992"));
        albumes.add(new Album(R.drawable.carinio,
                "Está muy bien eso del cariño", "1995"));
        albumes.add(new Album(R.drawable.paloma, "Punta Paloma", "1997"));
        albumes.add(new Album(R.drawable.puro, "Puro Veneno", "1998"));
        albumes.add(new Album(R.drawable.pollo, "La familia pollo", "2000"));
        albumes.add(new Album(R.drawable.ratito, "Un ratito de gloria", "2001"));
        albumes.add(new Album(R.drawable.hombre, "El hombre invisible", "2005"));
        return albumes;
    }

}