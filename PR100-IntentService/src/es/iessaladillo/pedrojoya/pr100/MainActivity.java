package es.iessaladillo.pedrojoya.pr100;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // Vistas.
    private BroadcastReceiver mExportarReceiver;
    private IntentFilter mExportarFilter;

    // Al crear la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        getVistas();
        // Se crea el receptor de mensajes desde el servicio.
        mExportarReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(getApplicationContext(),
                        intent.getStringExtra(ExportarService.EXTRA_FILENAME),
                        Toast.LENGTH_LONG).show();
                Intent mostrarIntent = new Intent(Intent.ACTION_VIEW);
                mostrarIntent.setDataAndType(Uri.parse(intent
                        .getStringExtra(ExportarService.EXTRA_FILENAME)),
                        "text/plain");
                startActivity(mostrarIntent);
                // Se aborta el broadcast para que no lo reciba nadie más.
                abortBroadcast();
            }
        };
        // Se crea el filtro para al receptor.
        mExportarFilter = new IntentFilter(ExportarService.ACTION_COMPLETADA);
        mExportarFilter.setPriority(2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se registra el receptor para dicha acción.
        registerReceiver(mExportarReceiver, mExportarFilter);
    }

    @Override
    protected void onPause() {
        // Se desregistra el receptor dicha acción.
        unregisterReceiver(mExportarReceiver);
        super.onPause();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        // Se carga la lista a partir de las constantes de cadena.
        ((ListView) findViewById(R.id.lstAlumnos)).setAdapter(ArrayAdapter
                .createFromResource(this, R.array.alumnos,
                        android.R.layout.simple_list_item_1));
    }

    // Al crear el menú de opciones.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Al pulsar un ítem de menú.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.mnuExportar:
            exportar();
            return true;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Exporta la lista de alumnos.
    private void exportar() {
        // Se inicia el servicio enviando como extra el array de datos.
        String[] alumnos = getResources().getStringArray(R.array.alumnos);
        Intent i = new Intent(this, ExportarService.class);
        i.putExtra(ExportarService.EXTRA_DATOS, alumnos);
        startService(i);
    }
}
