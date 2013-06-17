package es.iessaladillo.pedrojoya.pr027.actividades;

import es.iessaladillo.pedrojoya.pr027.R;
import es.iessaladillo.pedrojoya.pr027.R.layout;
import es.iessaladillo.pedrojoya.pr027.R.string;
import es.iessaladillo.pedrojoya.pr027.bd.DAO;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
 
	// Variables a nivel de clase.
	Context contexto;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		// LLamo al onCreate del padre.
		super.onCreate(savedInstanceState);
		// Establezco el layout que debe mostrar la actividad.
        setContentView(R.layout.main);
        // Guardo el contexto.
        contexto = this;
    }
    
    // Método callback que se ejecuta cuando se hace click en btnAgregar
	public void btnAgregarOnClick(View v) {
		// Creo un intent explícito con la acción NEW_STUDENT.
        Intent i = new Intent(this, AlumnoActivity.class);
        i.setAction("es.uma.SQL.NEW_STUDENT");
        // Llamo a la actividad.
        startActivity(i);
	}

    // Método callback que se ejecuta cuando se hace click en btnConsultar
	public void btnConsultarOnClick(View v) {
		// Creo un intent explícito para la actividad Consulta.
//        Intent i = new Intent(this, Consulta.class);
        Intent i = new Intent(this, ListadoActivity.class);
        // Llamo a la actividad.
        startActivity(i);
	}

    // Método callback que se ejecuta cuando se hace click en btnConsultar
	public void btnBorrarBDOnClick(View v) {
		// Solicito confirmación mediante un AlertDialog.
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setMessage(R.string.confirmar_eliminar_bd);
		b.setCancelable(false);
		b.setNegativeButton(R.string.no, null);
		b.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Borro la BD.
				ContextWrapper c = new ContextWrapper(contexto);
				c.deleteDatabase(DAO.DATABASE_NAME);
			}
		});
		b.create().show();
	}

}