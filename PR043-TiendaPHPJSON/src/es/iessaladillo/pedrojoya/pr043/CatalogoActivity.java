package es.iessaladillo.pedrojoya.pr043;

import java.io.File;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CatalogoActivity extends FragmentActivity implements
		OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

	long idProducto;
	private ListView lstProductos;
	ProductosCursorAdapter adaptador;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Llamo al onCreate del padre.
		super.onCreate(savedInstanceState);
		// Establezco el layout que utilizará la actividad.
		setContentView(R.layout.catalogo_activity);
		// Activo el botón de Home en la Action Bar (si la versión lo permite).
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		// Obtengo las vistas.
		lstProductos = (ListView) this.findViewById(R.id.lstProductos);
		// Establezco la vista a mostrar cuando la lista esté vacía.
		RelativeLayout rlCatalogoVacio = (RelativeLayout) this
				.findViewById(R.id.rlCatalogoVacio);
		lstProductos.setEmptyView(rlCatalogoVacio);
		// Cargo la lista.
		cargarLista();
		// La propia actividad responderá a los click en la lista.
		lstProductos.setOnItemClickListener(this);
	}

	// Obtiene los datos de la BD y los carga en la lista.
	private void cargarLista() {
		// Inicializo el cargador.
		getSupportLoaderManager().initLoader(0, null, this);
		// Creo un adaptador para la lista (con el cursor nulo inicialmente).
		adaptador = new ProductosCursorAdapter(this, null);
		// Asigno el adaptador a la ListActivity.
		lstProductos.setAdapter(adaptador);
	}

	// Al seleccionar una opción del menú de opciones.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Dependiendo de la opción de menú seleccionada.
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		// Obtengo el registro correspondiente al alumno seleccionado en forma
		// de cursor.
		Cursor cursor = (Cursor) lstProductos.getItemAtPosition(position);
		// Obtengo el id del producto seleccionado.
		idProducto = cursor.getLong(cursor.getColumnIndex(DAO.FLD_PRO_ID));
		// Envío el intent correspondiente.
		mostrarFichaProducto(idProducto);
	}

	// Lanza la actividad Ficha.
	private void mostrarFichaProducto(long idProducto) {
		// Creo un intent explícito para mostrar la actividad Ficha y le paso
		// como extra el id del producto que debe mostrar.
		Intent i = new Intent(this, FichaActivity.class);
		i.putExtra("idProducto", idProducto);
		startActivity(i);
	}

	public void btnCatalogoVacioOnClick(View v) {
		// Si hay conexión a Internet.
		if (ConexionServidor.isOnline(this)) {
			// Creo la tarea asíncrona para importar los datos.
			ImportarCatalogo tarea = new ImportarCatalogo(this);
			tarea.execute();
		}
		else {
			Toast.makeText(this, R.string.sin_conexion_a_internet,
					Toast.LENGTH_LONG).show();
		}
	}

	// Crea el cursor.
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// Consulto todos los alumnos a través del content provider.
		Uri uri = Uri.parse("content://es.iessaladillo.tienda/productos");
		CursorLoader cLoader = new CursorLoader(this, uri, DAO.PRO_TODOS,
				null, null, null);
		return cLoader;
	}

	// Cuando el cursor ha terminado de cargar los datos.
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// Cambio el cursor del adaptador por el que tiene datos.
		adaptador.changeCursor(data);
	}

	// Cuando se resetea el cargador.
	public void onLoaderReset(Loader<Cursor> loader) {
		// Cambio el cursor del adaptador por el que tiene datos.
		adaptador.changeCursor(null);
	}

	@Override
	protected void onResume() {
		getSupportLoaderManager().restartLoader(0, null, this);
		super.onResume();
	}

	private class ProductosCursorAdapter extends CursorAdapter {

		// Variables miembro.
		LayoutInflater inflador;

		// Contenedor de vistas de la fila.
		private class ContenedorVistas {
			ImageView imgFoto;
			TextView lblNombre;
			TextView lblDescripcion;
		}

		// Constructor.
		public ProductosCursorAdapter(Context contexto, Cursor c) {
			// Llamo al constructor del padre.
			super(contexto, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
			// Obtengo un inflador.
			inflador = LayoutInflater.from(contexto);
		}

		@Override
		public void bindView(View vistaFila, Context contexto, Cursor c) {
			// Recupero el contenedor de vistas de la fila desde la propiedad
			// Tag de la vista-fila.
			ContenedorVistas contenedor = (ContenedorVistas) vistaFila.getTag();
			// Obtengo los datos desde el cursor y los escribo en las vistas
			// correspondientes.
			contenedor.lblNombre.setText(c.getString(c
					.getColumnIndex(DAO.FLD_PRO_NOM)));
			contenedor.lblDescripcion.setText(c.getString(c
					.getColumnIndex(DAO.FLD_PRO_DES)));
			// Obtengo el nombre del archivo de la foto.
			String sNombreImagen = c.getString(c
					.getColumnIndex(DAO.FLD_PRO_IMA));
			// Si el producto tiene foto la pongo, y si no pongo una por
			// defecto.
			if (sNombreImagen != null && !sNombreImagen.equals("")) {
				String sPath = Environment.getExternalStorageDirectory()
						+ "/tienda/" + sNombreImagen;
				File archivo = new File(sPath);
				if (archivo.exists()) {
					contenedor.imgFoto.setImageURI(Uri.fromFile(archivo));
				}
				else {
					contenedor.imgFoto.setImageResource(R.drawable.ic_iconopp);
				}
			}
		}

		@Override
		public View newView(Context contexto, Cursor c, ViewGroup parent) {
			// Inflo el layout y obtengo la vista-fila correspondiente.
			View vistaFila = inflador.inflate(R.layout.fila_catalogo_activity,
					null);
			// Creo el contenedor de vista, lo relleno y lo almaceno en la
			// propiedad Tag de la vista-fila.
			ContenedorVistas contenedor = new ContenedorVistas();
			contenedor.imgFoto = (ImageView) vistaFila
					.findViewById(R.id.imgFoto);
			contenedor.lblNombre = (TextView) vistaFila
					.findViewById(R.id.lblNombre);
			contenedor.lblDescripcion = (TextView) vistaFila
					.findViewById(R.id.lblDescripcion);
			vistaFila.setTag(contenedor);
			// Retorno la vista-fila.
			return vistaFila;
		}

	}

}