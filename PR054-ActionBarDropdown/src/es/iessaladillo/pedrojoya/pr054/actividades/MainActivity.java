package es.iessaladillo.pedrojoya.pr054.actividades;

import java.lang.reflect.Field;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import es.iessaladillo.pedrojoya.pr054.R;
import es.iessaladillo.pedrojoya.pr054.fragmentos.FotoFragment;
import es.iessaladillo.pedrojoya.pr054.fragmentos.InfoFragment;

public class MainActivity extends Activity implements
        ActionBar.OnNavigationListener {

    // Constantes.
    private static final int POS_OPCION_FOTO = 0;
    private static final int POS_OPCION_INFO = 1;
    private static final String TAG_FRG_FOTO = "fotoFragment";
    private static final String TAG_FRG_INFO = "infoFragment";
    private static final String STATE_OPCION = "opcion";

    // Variables.
    private FotoFragment frgFoto;
    private InfoFragment frgInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Se activa el ítem de overflow en dispositivos con botón físico de
        // menú.
        overflowEnDispositivoConTeclaMenu();
        // Se configura para action bar para que tenga un spinner de navegación,
        // y NO muestre el título (para que haya más espacio).
        ActionBar ab = getActionBar();
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
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
            ab.setSelectedNavigationItem(savedInstanceState
                    .getInt(STATE_OPCION));
        }
    }

    // Cuando se produce un cambio de configuración.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Almacena qué pestaña tenemos seleccionada.
        outState.putInt(STATE_OPCION, getActionBar()
                .getSelectedNavigationIndex());
        super.onSaveInstanceState(outState);
    }

    // Al seleccionar un elemento del spinner de la action bar.
    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        // Se crea el fragmento correspondiente al elemento seleccionado.
        switch (itemPosition) {
        case POS_OPCION_FOTO:
            cargarFragmentoFoto();
            break;
        case POS_OPCION_INFO:
            cargarFragmentoInfo();
            break;
        }
        // Se retorna que ya ha sido procesada la selección.
        return true;
    }

    // Carga el fragmento de la foto
    private void cargarFragmentoFoto() {
        // Se busca el fragmento
        frgFoto = (FotoFragment) getFragmentManager().findFragmentByTag(
                TAG_FRG_FOTO);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        // Si no se encuentra, se crea y se añade al contenedor.
        if (frgFoto == null) {
            frgFoto = FotoFragment.newInstance(R.drawable.bench);
            ft.add(android.R.id.content, frgFoto, TAG_FRG_FOTO);
        } else {
            // Si se encuentra se enlaza con el contenedor.
            ft.attach(frgFoto);
        }
        // Se desvincula el otro fragmento del contenedor.
        if (frgInfo != null) {
            ft.detach(frgInfo);
        }
        ft.commit();
    }

    // Carga el fragmento de la info.
    private void cargarFragmentoInfo() {
        // Se busca el fragmento.
        frgInfo = (InfoFragment) getFragmentManager().findFragmentByTag(
                TAG_FRG_INFO);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        // Si no se encuentra, se crea y se añade al contenedor.
        if (frgInfo == null) {
            frgInfo = new InfoFragment();
            ft.add(android.R.id.content, frgInfo, TAG_FRG_INFO);
        } else {
            // Si se encuentra se enlaza con el contenedor.
            ft.attach(frgInfo);
        }
        // Se desvincula el otro fragmento del contenedor.
        if (frgFoto != null) {
            ft.detach(frgFoto);
        }
        ft.commit();
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
