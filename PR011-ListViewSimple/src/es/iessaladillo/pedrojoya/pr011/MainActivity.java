package es.iessaladillo.pedrojoya.pr011;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // Vistas.
    private ListView lstAlumnos;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        getVistas();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        lstAlumnos = (ListView) findViewById(R.id.lstAlumnos);
        // Se obtienen los datos para el adaptador de la lista.
        String[] alumnos = getResources().getStringArray(R.array.alumnos);
        // Se crea el adaptador ArrayAdapter con layout estándar.
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, alumnos);
        lstAlumnos.setAdapter(adaptador);
        // Se establece el listener para cuando se hace click en un item de la
        // lista.
        lstAlumnos.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> lst, View v, int position,
                    long id) {
                // Se informa al usuario sobre qué alumno ha pulsado.
                Toast.makeText(
                        getApplicationContext(),
                        getResources().getString(R.string.ha_pulsado_sobre)
                                + lst.getItemAtPosition(position),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
