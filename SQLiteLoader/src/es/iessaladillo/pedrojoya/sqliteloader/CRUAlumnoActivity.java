package es.iessaladillo.pedrojoya.sqliteloader;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CRUAlumnoActivity extends Activity {

	// Constantes.
	public static final int MODO_AGREGAR = 0;
	public static final int MODO_EDITAR = 1;
	public static final int MODO_VER = 2;

	// Variables a nivel de clase.
	private EditText txtNombre;
	private EditText txtTelefono;
	private EditText txtDireccion;
	private Spinner spnCurso;
	private Button btnAgregar;
	private int modo;
	private Alumno alumno;
	private ArrayAdapter<CharSequence> adaptadorCursos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Llamo al onCreate del padre.
		super.onCreate(savedInstanceState);
		// Establezco el layout que mostrará la actividad.
		setContentView(R.layout.activity_crualumno);
		// Obtengo las referencias a las vistas.
		getVistas();
		// Cargo el spinner de cursos.
		cargarCursos();
		// Establezco el modo en el que debe comportarse la actividad
		// dependiendo de la acción.
		setModo();
	}

	// Carga los cursos en el spinner.
	private void cargarCursos() {
		// Creo un adaptador para el spinner. Le paso el contexto, los datos
		// como recurso de array de cadenas y el layout (en este caso uno por
		// defecto) con el que se deben mostrar el dato sin desplegar.
		adaptadorCursos = ArrayAdapter.createFromResource(this, R.array.cursos,
				android.R.layout.simple_spinner_item);
		// Establezco el layout que debe usarse cuando se despliegue el spinner
		// (uno por defecto).
		adaptadorCursos
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Lo establezco como adaptador del spinner.
		spnCurso.setAdapter(adaptadorCursos);
	}

	private void setModo() {
		// Dependiendo de la acción.
		String accion = getIntent().getAction();
		if (accion.equals("es.iessaladillo.EDITAR_ALUMNO")) {
			setModoEditar();
		} else if ("es.iessaladillo.VER_ALUMNO".equals(accion)) {
			setModoVer();
		} else {
			setModoAgregar();
		}
	}

	private void setModoEditar() {
		// Establezo el modo a Editar.
		modo = MODO_EDITAR;
		// Cargo los datos del alumno.
		cargarAlumno(getIntent().getExtras().getLong("id"));
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
		modo = MODO_VER;
		// Cargo los datos del alumno.
		cargarAlumno(getIntent().getExtras().getLong("id"));
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
		modo = MODO_AGREGAR;
		// Creo un nuevo objeto Alumno vacío.
		alumno = new Alumno();
		// Actualizo el título de la actividad y el texto del botón.
		this.setTitle(R.string.agregar_alumno);
		btnAgregar.setText(R.string.agregar_alumno);
		// Habilito las vistas.
		enableVistas(true);
	}

	// Carga los datos del alumno provenientes de la BD en el objeto Alumno.
	private void cargarAlumno(long id) {
		// Consulto en el content provider los datos del alumno.
		Uri uri = Uri.parse("content://es.iessaladillo.alumnos/alumnos/" + id);
		CursorLoader cLoader = new CursorLoader(this, uri, GestorBD.ALU_TODOS,
				null, null, null);
		Cursor cursor = cLoader.loadInBackground();
		if (cursor.getCount() == 1) {
			// Los cargo en el objeto Alumno.
			cursor.moveToFirst();
			alumno = GestorBD.cursorToAlumno(cursor);
		} else {
			// Si no se ha encontrado el alumno en la BD, informo y paso al modo
			// Agregar.
			Toast.makeText(this, R.string.alumno_no_encontrado,
					Toast.LENGTH_LONG).show();
			setModoAgregar();
		}
		// Cierro el cursor.
		cursor.close();
	}

	// btnAgregarOnClick
	public void btnAgregarOnClick(View v) {
		// Lleno objeto Alumno con los datos de las vistas.
		vistasToAlumno();
		// Agrego el alumno (si se han introducido los datos obligatorios).
		if (alumno.getNombre().length() > 0
				&& alumno.getTelefono().length() > 0) {
			// Dependiendo de la acción.
			switch (modo) {
			case MODO_AGREGAR:
				agregarAlumno(alumno);
				break;
			case MODO_EDITAR:
				actualizarAlumno(alumno);
				break;
			case MODO_VER:
				llamarAlumno(alumno);
			}
		} else {
			Toast.makeText(this, this.getString(R.string.datos_obligatorios),
					Toast.LENGTH_SHORT).show();
		}
	}

	// Agrega un alumno a la base de datos. Recibe un objeto Alumno.
	private void agregarAlumno(Alumno alumno) {
		// Copio los datos de las vistas en el objeto Alumno.
		vistasToAlumno();
		// Realizo el insert a través del content provider.
		Uri uri = Uri.parse("content://es.iessaladillo.alumnos/alumnos");
		long id = Long.parseLong(getContentResolver().insert(uri,
				GestorBD.alumnoToContentValues(alumno)).getLastPathSegment());
		// Si se ha realizado correctamente guardo el id que se le asignado en
		// el objeto Alumno.
		if (id >= 0) {
			alumno.setId(id);
			Toast.makeText(this, getString(R.string.insercion_correcta),
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, getString(R.string.insercion_incorrecta),
					Toast.LENGTH_SHORT).show();
		}
		resetVistas();
	}

	// Actualiza un alumno en la base de datos. Recibe un objeto Alumno.
	private void actualizarAlumno(Alumno alumno) {
		// Copio los datos de las vistas en el objeto Alumno.
		vistasToAlumno();
		// Realizo el update a través del content provider.
		Uri uri = Uri.parse("content://es.iessaladillo.alumnos/alumnos/"
				+ alumno.getId());
		if (getContentResolver().update(uri,
				GestorBD.alumnoToContentValues(alumno), null, null) > 0) {
			Toast.makeText(this, getString(R.string.actualizacion_correcta),
					Toast.LENGTH_SHORT).show();
			finish();
		} else {
			Toast.makeText(this, getString(R.string.actualizacion_incorrecta),
					Toast.LENGTH_SHORT).show();
		}
	}

	// Envía un intent implícito para llamar por teléfono al alumno.
	private void llamarAlumno(Alumno alumno) {
		/*
		 * Creo un intent implícito para llamar al teléfono del alumno
		 * (convetido a URI) y llamo a la actividad.
		 */
		startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ alumno.getTelefono())));
	}

	// Obtiene la referencia a las vistas del layout.
	private void getVistas() {
		spnCurso = (Spinner) findViewById(R.id.spnCurso);
		txtNombre = (EditText) findViewById(R.id.txtNombre);
		txtTelefono = (EditText) findViewById(R.id.txtTelefono);
		txtDireccion = (EditText) findViewById(R.id.txtDireccion);
		btnAgregar = (Button) findViewById(R.id.btnAgregar);
	}

	// Hace reset sobre las vistas.
	private void resetVistas() {
		txtNombre.setText("");
		txtTelefono.setText("");
		txtDireccion.setText("");
	}

	private void enableVistas(boolean estado) {
		txtNombre.setEnabled(estado);
		txtTelefono.setEnabled(estado);
		txtDireccion.setEnabled(estado);
		spnCurso.setEnabled(estado);
	}

	// Muestro los datos de un objeto Alumno en las vistas.
	private void alumnoToVistas() {
		txtNombre.setText(alumno.getNombre());
		txtTelefono.setText(alumno.getTelefono());
		txtDireccion.setText(alumno.getDireccion());
		spnCurso.setSelection(adaptadorCursos.getPosition(alumno.getCurso()),
				true);
	}

	// Llena el objeto Alumno con los datos de las vistas.
	private void vistasToAlumno() {
		alumno.setNombre(txtNombre.getText().toString());
		alumno.setTelefono(txtTelefono.getText().toString());
		alumno.setDireccion(txtDireccion.getText().toString());
		alumno.setCurso((String) spnCurso.getSelectedItem());
	}

}
