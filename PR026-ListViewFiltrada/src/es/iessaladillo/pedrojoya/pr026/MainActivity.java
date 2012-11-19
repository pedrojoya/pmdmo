package es.iessaladillo.pedrojoya.pr026;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	// Variables a nivel de clase.	
    private ListView lstAlumnos;
	private EditText txtAlumno;
	private ArrayAdapter<String> adaptador;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	// Llamo al onCreate del padre.
        super.onCreate(savedInstanceState);
        // Establezco el layout que mostrará la actividad.
        setContentView(R.layout.main);
        // Obtengo e inicializo las vistas.
        getVistas();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void getVistas() {
    	txtAlumno = (EditText) findViewById(R.id.txtAlumno);
    	lstAlumnos = (ListView) findViewById(R.id.lstAlumnos);
    	// Obtengo los datos para el adaptador de la lista.
    	String[] alumnos = getResources().getStringArray(R.array.alumnos);
    	// Creo el adaptador que usará dichos datos y un layout estándar.
    	adaptador = new ArrayAdapter<String>(this, 
    			            android.R.layout.simple_list_item_1, alumnos);
    	lstAlumnos.setAdapter(adaptador);
    	// Creo el listener para cuando se hace click en un item de la lista.
    	lstAlumnos.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> lst, View vistafila, 
					                int posicion, long id) {
				// Informo al usuario sobre que alumno ha pulsado.
				Toast.makeText(lst.getContext(), 
						       getResources().getString(R.string.ha_pulsado_sobre) + 
						       lst.getItemAtPosition(posicion), 
						       Toast.LENGTH_LONG).show();
			}
    	});
    	// Creo un "vigilante" para cuando se escribe texto en el EditText
    	txtAlumno.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s) {
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			// Al cambiar el texto.
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// Filtro por el texto introducido.
				// En este caso el adaptador es un ArrayAdapter,
				// que implementa la interfaz Filterable.
				// Para un adaptador más complejo se recomienda ver el proyecto
				// PR017.
				adaptador.getFilter().filter(s);
			}
    	});
    }
     
}
