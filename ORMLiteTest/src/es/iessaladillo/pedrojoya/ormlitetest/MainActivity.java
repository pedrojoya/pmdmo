package es.iessaladillo.pedrojoya.ormlitetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
		// LLamo al onCreate del padre.
		super.onCreate(savedInstanceState);
		// Establezco el layout que debe mostrar la actividad.
        setContentView(R.layout.main);
    }
    
    // Método callback que se ejecuta cuando se hace click en btnAgregar
	public void btnAgregarOnClick(View v) {
		// Creo un intent explícito con la acción NEW_STUDENT.
        Intent i = new Intent(this, AddAlumnos.class);
        i.setAction("es.uma.SQL.NEW_STUDENT");
        // Llamo a la actividad.
        startActivity(i);
	}

    // Método callback que se ejecuta cuando se hace click en btnConsultar
	public void btnConsultarOnClick(View v) {
		// Creo un intent explícito para la actividad Consulta.
//        Intent i = new Intent(this, Consulta.class);
        Intent i = new Intent(this, ListaAlumnosActivity.class);
        // Llamo a la actividad.
        startActivity(i);
	}

    // Método callback que se ejecuta cuando se hace click en btnConsultar
	public void btnBorrarBDOnClick(View v) {
		// TO-DO.
	}

}