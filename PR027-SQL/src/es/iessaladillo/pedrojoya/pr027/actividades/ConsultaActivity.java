package es.iessaladillo.pedrojoya.pr027.actividades;

import es.iessaladillo.pedrojoya.pr027.R;
import es.iessaladillo.pedrojoya.pr027.R.id;
import es.iessaladillo.pedrojoya.pr027.R.layout;
import es.iessaladillo.pedrojoya.pr027.R.string;
import es.iessaladillo.pedrojoya.pr027.bd.AdaptadorBD;
import es.iessaladillo.pedrojoya.pr027.modelos.Alumno;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ConsultaActivity extends Activity {

	// Variables a nivel de clase.
	EditText txtNombre;
	EditText txtTelefono;
    Spinner spnAlumno;
    Button btnConsultar;
    Button btnLlamar;
    Alumno alumno;	// Alumno con el que trabajará la actividad.
    AdaptadorBD  bd;
    
	public void onCreate(Bundle savedInstanceState) {
		// Llamo al onCreate del padre.
	    super.onCreate(savedInstanceState);
	    // Establezco el layout que mostrará la actividad.
	    setContentView(R.layout.consulta);
	    // Obtengo la referencia a las vistas del layout.
	    getVistas();
	    // Cargo el spinner de alumnos.
	    cargarAlumnos();
	}
	 
	// btnConsultarOnClick
	public void btnConsultarOnClick(View v) {
		// Obtengo del spinner el registro del alumno seleccionado (cursor)
		Cursor cursor = (Cursor) spnAlumno.getSelectedItem();
		// Lo paso a objeto Alumno.
		alumno = bd.cursorToAlumno(cursor);
		// Relleno las vistas con los datos del alumno.
		rellenarVistas(alumno);		
		// Habilito las vistas.
		enableVistas(true);
	}
	
	// btnLlamarOnClick
	public void btnLlamarOnClick(View v) {
		/* Creo un nuevo intent implícito con la acción CALL (llamar) y la uri con el
		 * teléfono del alumno. */
        Intent i =new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + alumno.getTelefono()));
        // Envío el intent.
        startActivity(i);	
	}
	
	// Obtiene las referencias a las vistas del layout
	private void getVistas() {
        spnAlumno = (Spinner) findViewById(R.id.spnAlumno);
	    txtNombre = (EditText)  findViewById(R.id.txtNombre);
	    txtTelefono = (EditText)  findViewById(R.id.txtTelefono);
	    btnConsultar = (Button) findViewById(R.id.btnConsultar);
	    btnLlamar = (Button) findViewById(R.id.btnLlamar);
	}
	
	// Carga el spinner de Alumnos con los datos obtenidos de la BD.
	private void cargarAlumnos() {
		// Creo un objeto adaptador de la BD.
        bd = new AdaptadorBD (this);
        try {
        	// Abro la base de datos.
			bd.open();
			// Consulto todos los alumnos y los almaceno en un cursor.   
			Cursor cursor = bd.queryAllAlumnos();
			/* Creo un adaptador SimpleCursorAdapter que gestione el spinner. Indico:
			 * Contexto, layout que se utilizará cuando no esté desplegado (layout estándar),
			 * el cursor que posee los datos, la columna del cursor que se mostrará y
			 * el nombre de la vista del layout donde se debe mostrar el dato 
			 * (debe ser text1 a tratarse del layout estándar simple_spinner_item). */
			SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this,
					android.R.layout.simple_spinner_item,
					cursor,
				    new String[] {AdaptadorBD.FLD_ALU_NOM}, 
				    new int[] {android.R.id.text1});
			// Establezco el layout que debe utilizarse cuando se despliegue el spinner (uno estándar).
			adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			// Hago que el SimpleCursorAdapter que hemos personalizado sea el adaptador del spinner.
			spnAlumno.setAdapter(adaptador);
			// Si no hay alumnos en la BD deshabilito los controles y botones.
			if (cursor.getCount() == 0) {
				enableVistas(false);
			 	Toast.makeText(this, getString(R.string.sin_alumnos), Toast.LENGTH_LONG).show();
			}
			// Cierro la BD.
			bd.close();
		} catch (SQLException e) {
			enableVistas(false);
		 	Toast.makeText(this, getString(R.string.error_apertura_bd), Toast.LENGTH_LONG).show();
		}
	}
	
	// Deshabilita la vistas del layout.
	private void enableVistas(boolean estado) {
		btnConsultar.setEnabled(estado);
 		spnAlumno.setEnabled(estado);
 		txtNombre.setEnabled(estado);
 		txtTelefono.setEnabled(estado);
 		btnLlamar.setEnabled(estado);
	}
	
	// Muestra los datos de un alumno en las vistas del layout.
	private void rellenarVistas(Alumno alu) {
		txtNombre.setText(alu.getNombre());
		txtTelefono.setText(alu.getTelefono());
	}
	
}
