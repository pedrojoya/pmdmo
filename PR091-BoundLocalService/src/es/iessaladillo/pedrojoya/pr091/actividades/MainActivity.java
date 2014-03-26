package es.iessaladillo.pedrojoya.pr091.actividades;

import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import es.iessaladillo.pedrojoya.pr091.R;
import es.iessaladillo.pedrojoya.pr091.modelos.Cancion;
import es.iessaladillo.pedrojoya.pr091.servicios.MusicaOnlineService;
import es.iessaladillo.pedrojoya.pr091.servicios.MusicaOnlineService.LocalBinder;

public class MainActivity extends Activity implements OnItemClickListener {

    private Intent intentServicio;
    private ListView lstCanciones;
    private ServiceConnection conexion;
    private MusicaOnlineService servicio;
    private ArrayList<Cancion> canciones;
    private LocalBroadcastManager gestor;
    private BroadcastReceiver receptor;
    private IntentFilter filtro;
    private boolean vinculado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getVistas();
        // Se obtiene el gestor de receptores locales.
        gestor = LocalBroadcastManager.getInstance(this);
        // Se crea el receptor de mensajes desde el servicio.
        receptor = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // Se extra del intent la posición de la canción que se está
                // reproduciendo.
                if (intent.hasExtra(MusicaOnlineService.EXTRA_POS_ACTUAL)) {
                    lstCanciones.setItemChecked(intent.getIntExtra(
                            MusicaOnlineService.EXTRA_POS_ACTUAL,
                            lstCanciones.getCheckedItemPosition()), true);
                }
            }
        };
        // Se crea el filtro para al receptor.
        filtro = new IntentFilter(MusicaOnlineService.ACTION_REPRODUCIENDO);
        // Se crea el objeto que representa la conexión con el servicio.
        conexion = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName arg0) {
                servicio = null;
                vinculado = false;
            }

            @Override
            public void onServiceConnected(ComponentName className,
                    IBinder binder) {
                vinculado = true;
                // Se obtiene la referencia al servicio.
                servicio = ((LocalBinder) binder).getService();
                // Se carga la lista de canciones en el servicio.
                servicio.setLista(canciones);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Se crea el intent de vinculación con el servicio.
        intentServicio = new Intent(getApplicationContext(),
                MusicaOnlineService.class);
        // Se vincula el servicio.
        bindService(intentServicio, conexion, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (vinculado) {
            unbindService(conexion);
            vinculado = false;
        }
    }

    private void getVistas() {
        lstCanciones = (ListView) findViewById(R.id.lstCanciones);
        canciones = getListaCanciones();
        ArrayAdapter<Cancion> adaptador = new ArrayAdapter<Cancion>(this,
                android.R.layout.simple_list_item_activated_1, canciones);
        lstCanciones.setAdapter(adaptador);
        lstCanciones.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lstCanciones.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.mnuParar:
            // Se para el servicio.
            unbindService(conexion);
            vinculado = false;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Seleccionamos la canción que se está reproduciendo.
        if (servicio != null) {
            lstCanciones.setItemChecked(servicio.getPosCancionActual(), true);
        }
        // Se registra el receptor en el gestor de receptores locales para dicha
        // acción.
        gestor.registerReceiver(receptor, filtro);
    }

    @Override
    protected void onPause() {
        // Se desregistra el receptor del gestor de receptores locales para
        // dicha acción.
        gestor.unregisterReceiver(receptor);
        super.onPause();
    }

    // Cuando se pulsa en una canción de la lista.
    @Override
    public void onItemClick(AdapterView<?> lst, View v, int position, long id) {
        // Se inicia el servicio para que reproduzca la canción.
        reproducirCancion(position);
    }

    // Inicia el servicio de reproducción de la canción.
    private void reproducirCancion(int position) {
        if (vinculado) {
            servicio.reproducirCancion(position);
        }
    }

    // Crea y retorna el ArrayList de canciones.
    private ArrayList<Cancion> getListaCanciones() {
        ArrayList<Cancion> canciones = new ArrayList<Cancion>();
        canciones
                .add(new Cancion("Morning Mood (by Grieg)", "3:43",
                        "https://www.youtube.com/audiolibrary_download?vid=036500ffbf472dcc"));
        canciones
                .add(new Cancion("Brahms Lullaby", "1:46",
                        "https://www.youtube.com/audiolibrary_download?vid=9894a50b486c6136"));
        canciones
                .add(new Cancion("From Russia With Love", "2:26",
                        "https://www.youtube.com/audiolibrary_download?vid=4e8d1a0fdb3bbe12"));
        canciones
                .add(new Cancion("Les Toreadors from Carmen (by Bizet)",
                        "2:21",
                        "https://www.youtube.com/audiolibrary_download?vid=fafb35a907cd6e73"));
        canciones
                .add(new Cancion("Funeral March (by Chopin)", "9:25",
                        "https://www.youtube.com/audiolibrary_download?vid=4a7d058f20d31cc4"));
        return canciones;
    }

}