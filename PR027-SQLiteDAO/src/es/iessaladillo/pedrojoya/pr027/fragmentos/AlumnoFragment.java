package es.iessaladillo.pedrojoya.pr027.fragmentos;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import es.iessaladillo.pedrojoya.pr027.R;
import es.iessaladillo.pedrojoya.pr027.bd.DAO;
import es.iessaladillo.pedrojoya.pr027.modelos.Alumno;

public class AlumnoFragment extends Fragment {

	// Constantes.
	public static final String EXTRA_MODO = "modo";
	public static final String EXTRA_ID = "id";
	public static final String MODO_AGREGAR = "AGREGAR";
	public static final String MODO_EDITAR = "EDITAR";
	public static final String MODO_VER = "VER";

	// Variables a nivel de clase.
	private DAO dao;
	private EditText txtNombre;
	private EditText txtTelefono;
	private EditText txtDireccion;
	private Spinner spnCurso;
	private String modo;
	private Alumno alumno;
	private ArrayAdapter<CharSequence> adaptadorCursos;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_alumno, container, false);
		// Obtengo las referencias a las vistas.
		getVistas(v);
		// Cargo el spinner de cursos.
		cargarCursos();
		// Establezco el modo en el que debe comportarse la actividad
		// dependiendo de la acción.
		setModo();
		return v;
	}

	// Carga los cursos en el spinner.
	private void cargarCursos() {
		/*
		 * Creo un adaptador para el spinner. Le paso el contexto, los datos
		 * como recurso de array de cadenas y el layout (en este caso uno por
		 * defecto) con el que se deben mostrar el dato sin desplegar.
		 */
		adaptadorCursos = ArrayAdapter.createFromResource(this.getActivity(),
				R.array.cursos, android.R.layout.simple_spinner_item);
		// Establezco el layout que debe usarse cuando se despliegue el spinner
		// (uno por defecto).
		adaptadorCursos
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Lo establezco como adaptador del spinner.
		spnCurso.setAdapter(adaptadorCursos);
	}

	private void setModo() {
		// Dependiendo de la acción.
		String modo = this.getArguments().getString(EXTRA_MODO);
		if (modo.equals(MODO_EDITAR)) {
			setModoEditar();
		} else if (modo.equals(MODO_VER)) {
			setModoVer();
		} else {
			setModoAgregar();
		}
	}

	private void setModoEditar() {
		// Establezo el modo a Editar.
		modo = MODO_EDITAR;
		// Cargo los datos del alumno.
		cargarAlumno(this.getArguments().getLong(EXTRA_ID));
		// Escribo los datos del alumno en las vistas.
		alumnoToVistas();
		// Actulizo el título de la actividad y el texto del botón.
		this.getActivity().setTitle(R.string.editar_alumno);
		// Habilito las vistas.
		enableVistas(true);
	}

	private void setModoVer() {
		// Establezo el modo a Editar.
		modo = MODO_VER;
		// Cargo los datos del alumno.
		cargarAlumno(this.getArguments().getLong(EXTRA_ID));
		// Escribo los datos del alumno en las vistas.
		alumnoToVistas();
		// Actulizo el título de la actividad y el texto del botón.
		this.getActivity().setTitle(R.string.ver_alumno);
		// Deshabilito las vistas.
		enableVistas(false);
	}

	private void setModoAgregar() {
		// Establezo el modo a Editar.
		modo = MODO_AGREGAR;
		// Creo un nuevo objeto Alumno vacío.
		alumno = new Alumno();
		// Actulizo el título de la actividad y el texto del botón.
		this.getActivity().setTitle(R.string.agregar_alumno);
		// Habilito las vistas.
		enableVistas(true);
	}

	// Carga los datos del alumno provenientes de la BD en el objeto Alumno.
	private void cargarAlumno(long id) {
		// Consulto en la BD los datos del alumno.
		dao = new DAO(this.getActivity()).open();
		alumno = dao.queryAlumno(id);
		if (alumno == null) {
			// Si no se ha encontrado el alumno en la BD, se informa y se pasa
			// al modo
			// Agregar.
			Toast.makeText(this.getActivity(), R.string.alumno_no_encontrado,
					Toast.LENGTH_LONG).show();
			setModoAgregar();
		}
		dao.close();
	}

	public void guardarAlumno() {
		// Lleno objeto Alumno con los datos de las vistas.
		vistasToAlumno();
		// Agrego el alumno (si se han introducido los datos obligatorios).
		if (alumno.getNombre().length() > 0
				&& alumno.getTelefono().length() > 0) {
			// Dependiendo de la acción.
			if (modo.equals(MODO_AGREGAR)) {
				agregarAlumno();
			} else if (modo.equals(MODO_EDITAR)) {
				actualizarAlumno();
			} else if (modo.equals(MODO_VER)) {
				llamarAlumno();
			}
		} else {
			Toast.makeText(this.getActivity(),
					this.getString(R.string.datos_obligatorios),
					Toast.LENGTH_SHORT).show();
		}
	}

	// Agrega un alumno a la base de datos. Recibe un objeto Alumno.
	private void agregarAlumno() {
		// Realizo el insert.
		dao = new DAO(this.getActivity()).open();
		long id = dao.createAlumno(alumno);
		dao.close();
		// Si se ha realizado correctamente guardo el id que se le asignado en
		// el objeto Alumno.
		if (id >= 0) {
			alumno.setId(id);
			Toast.makeText(this.getActivity(),
					getString(R.string.insercion_correcta), Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(this.getActivity(),
					getString(R.string.insercion_incorrecta),
					Toast.LENGTH_SHORT).show();
		}
		resetVistas();
	}

	// Actualiza un alumno en la base de datos. Recibe un objeto Alumno.
	private void actualizarAlumno() {
		// Realizo el update.
		dao = new DAO(this.getActivity()).open();
		if (dao.updateAlumno(alumno)) {
			Toast.makeText(this.getActivity(),
					getString(R.string.actualizacion_correcta),
					Toast.LENGTH_SHORT).show();
			setModoEditar();
		} else {
			Toast.makeText(this.getActivity(),
					getString(R.string.actualizacion_incorrecta),
					Toast.LENGTH_SHORT).show();
		}
		dao.close();
	}

	// Envía un intent implícito para llamar por teléfono al alumno.
	private void llamarAlumno() {
		/*
		 * Creo un intent implícito para llamar al teléfono del alumno
		 * (convetido a URI) y llamo a la actividad.
		 */
		startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ alumno.getTelefono())));
	}

	// Obtiene la referencia a las vistas del layout.
	private void getVistas(View v) {
		spnCurso = (Spinner) v.findViewById(R.id.spnCurso);
		txtNombre = (EditText) v.findViewById(R.id.txtNombre);
		txtTelefono = (EditText) v.findViewById(R.id.txtTelefono);
		txtDireccion = (EditText) v.findViewById(R.id.txtDireccion);
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
