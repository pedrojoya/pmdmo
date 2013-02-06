package es.iessaladillo.pedrojoya.ormlitetest;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddAlumnos extends OrmLiteBaseActivity<AdaptadorBD> {

	// Constantes.
	private enum Modo {AGREGAR, EDITAR, VER};
	
	// Variables a nivel de clase.
	private AdaptadorBD  bd;
	private EditText txtNombre;
	private EditText txtTelefono;
	private Spinner spnCurso;
	private Button btnAgregar;
	private Modo modo;
	private Alumno alumno;
	private ArrayAdapter<CharSequence> adaptadorCursos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Llamo al onCreate del padre.
		super.onCreate(savedInstanceState);
		// Establezco el layout que mostrará la actividad.
		setContentView(R.layout.addalumnos);
		// Obtengo el adaptador de la BD y la abro.
		bd = this.getHelper();
		// Obtengo las referencias a las vistas.
		getVistas();
		// Cargo el spinner de cursos.
		cargarCursos();
		// Establezco el modo en el que debe comportarse la actividad dependiendo de la acción.
		setModo();
	}
	
	// Carga los cursos en el spinner.
	private void cargarCursos() {
		/* Creo un adaptador para el spinner.
		 * Le paso el contexto, los datos como recurso de array de cadenas y el layout
		 * (en este caso uno por defecto) con el que se deben mostrar el dato sin desplegar. */
		adaptadorCursos = ArrayAdapter.createFromResource(
				this, R.array.cursos, android.R.layout.simple_spinner_item);
		// Establezco el layout que debe usarse cuando se despliegue el spinner (uno por defecto).
		adaptadorCursos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Lo establezco como adaptador del spinner.
		spnCurso.setAdapter(adaptadorCursos);
	}

	private void setModo() {
		// Dependiendo de la acción.
		String accion = getIntent().getAction();
		if ("es.uma.SQL.EDIT_STUDENT".equals(accion)) {
			setModoEditar();
		}
		else if ("es.uma.SQL.VIEW_STUDENT".equals(accion)) {
			setModoVer();
		}
		else {
			setModoAgregar();
		}
	}
	
	private void setModoEditar() {
		// Establezo el modo a Editar.
		modo = Modo.EDITAR;
		// Cargo los datos del alumno.
		cargarAlumno(getIntent().getExtras().getInt("id"));
		// Escribo los datos del alumno en las vistas.
		alumnoToVistas();
		// Actulizo el título de la actividad y el texto del botón.
		this.setTitle(R.string.editar_alumno);
		btnAgregar.setText(R.string.editar_alumno);
		// Habilito las vistas.
		enableVistas(true);
	}

	private void setModoVer() {
		// Establezo el modo a Editar.
		modo = Modo.VER;
		// Cargo los datos del alumno.
		cargarAlumno(getIntent().getExtras().getInt("id"));
		// Escribo los datos del alumno en las vistas.
		alumnoToVistas();
		// Actulizo el título de la actividad y el texto del botón.
		this.setTitle(R.string.ver_alumno);
		btnAgregar.setText(R.string.llamar_alumno);
		// Deshabilito las vistas.
		enableVistas(false);
	}
	
	private void setModoAgregar() {
		// Establezo el modo a Editar.
		modo = Modo.AGREGAR;
		// Creo un nuevo objeto Alumno vacío.
		alumno = new Alumno();
		// Actulizo el título de la actividad y el texto del botón.
		this.setTitle(R.string.agregar_alumno);
		btnAgregar.setText(R.string.agregar_alumno);
		// Habilito las vistas.
		enableVistas(true);
	}
	
	// Carga los datos del alumno provenientes de la BD en el objeto Alumno.
	private void cargarAlumno(int id) {
		// Consulto en la BD los datos del alumno.
		try {
			Dao<Alumno, Integer> dao = bd.getAlumnoDAO();
			alumno = dao.queryForId(new Integer(id));
			if (alumno == null) {
				// Si no se ha encontrado el alumno en la BD, informo y paso al modo Agregar.
				Toast.makeText(this, R.string.alumno_no_encontrado, Toast.LENGTH_LONG).show();
				setModoAgregar();
			}
		} catch (java.sql.SQLException e) {
			throw new RuntimeException(e);
		}
	}
	 	 
	// btnAgregarOnClick
	public void btnAgregarOnClick(View v) {
		// Lleno objeto Alumno con los datos de las vistas.
		vistasToAlumno(); 
		// Agrego el alumno (si se han introducido los datos obligatorios).
		if (alumno.getNombre().length() > 0 && alumno.getTelefono().length() > 0) {
			// Dependiendo de la acción.
			switch (modo) {
			case AGREGAR:
				agregarAlumno(alumno);
				break;
			case EDITAR:
				actualizarAlumno(alumno);
				break;
			case VER:
				llamarAlumno(alumno);
			}
		} 
		else {
			Toast.makeText(this, this.getString(R.string.datos_obligatorios), Toast.LENGTH_SHORT).show();
		}
	}

	// Agrega un alumno a la base de datos. Recibe un objeto Alumno.
	private void agregarAlumno(Alumno alumno) {
		// Consulto en la BD los datos del alumno.
		try {
			Dao<Alumno, Integer> dao = bd.getAlumnoDAO();
			if (dao.create(alumno) > 0) {
				Toast.makeText (this, getString(R.string.insercion_correcta), Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText (this, getString(R.string.insercion_incorrecta), Toast.LENGTH_SHORT).show();				
			}
			resetVistas();
		} catch (java.sql.SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	// Actualiza un alumno en la base de datos. Recibe un objeto Alumno.
	private void actualizarAlumno(Alumno alumno) {
		// Consulto en la BD los datos del alumno.
		try {
			Dao<Alumno, Integer> dao = bd.getAlumnoDAO();
			if (dao.update(alumno) > 0) {
				Toast.makeText (this, getString(R.string.actualizacion_correcta), Toast.LENGTH_SHORT).show();
				finish();
			}
			else {
				Toast.makeText (this, getString(R.string.actualizacion_incorrecta), Toast.LENGTH_SHORT).show();				
			}
		} catch (java.sql.SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	// Envía un intent implícito para llamar por teléfono al alumno.
	private void llamarAlumno(Alumno alumno) {
		/* Creo un intent implícito para llamar al teléfono del alumno 
		 * (convetido a URI) y llamo a la actividad. */
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + alumno.getTelefono())));				
	}
		
	// Obtiene la referencia a las vistas del layout.
	private void getVistas() {
		spnCurso = (Spinner) findViewById(R.id.spnCurso);
		txtNombre = (EditText) findViewById(R.id.txtNombre);
		txtTelefono = (EditText) findViewById(R.id.txtTelefono);		
		btnAgregar = (Button) findViewById(R.id.btnAgregar);
	}

	// Hace reset sobre las vistas.
	private void resetVistas() {
		txtNombre.setText("");
		txtTelefono.setText("");		
	}
	
	private void enableVistas(boolean estado) {
		txtNombre.setEnabled(estado);
		txtTelefono.setEnabled(estado);
		spnCurso.setEnabled(estado);
	}

	// Muestro los datos de un objeto Alumno en las vistas.
	private void alumnoToVistas() {
		txtNombre.setText(alumno.getNombre());
		txtTelefono.setText(alumno.getTelefono());
		spnCurso.setSelection(adaptadorCursos.getPosition(alumno.getCurso()), true);
	}
	
	// Llena el objeto Alumno con los datos de las vistas.
	private void vistasToAlumno() {
		alumno.setNombre(txtNombre.getText().toString());
		alumno.setTelefono(txtTelefono.getText().toString());
		alumno.setCurso((String) spnCurso.getSelectedItem());
	}

}
