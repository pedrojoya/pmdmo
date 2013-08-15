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
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CarritoActivity extends FragmentActivity implements
		OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor>,
		MultiChoiceModeListener {

	private long idProducto;
	private ListView lstProductos;
	ProductosCursorAdapter adaptador;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Llamo al onCreate del padre.
		super.onCreate(savedInstanceState);
		// Establezco el layout que utilizará la actividad.
		setContentView(R.layout.carrito_activity);
		// Activo el botón de Home en la Action Bar (si la versión lo permite).
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		// Obtengo las vistas.
		lstProductos = (ListView) this.findViewById(R.id.lstProductos);
		// Establezco la vista a mostrar cuando la lista esté vacía.
		RelativeLayout rlCarritoVacio = (RelativeLayout) this
				.findViewById(R.id.rlCarritoVacio);
		lstProductos.setEmptyView(rlCarritoVacio);
		// Cargo la lista.
		cargarLista();
		// La propia actividad responderá a los click en la lista.
		lstProductos.setOnItemClickListener(this);
		// Hago que se puedan seleccionar varios elementos de la lista.
		lstProductos.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		lstProductos.setMultiChoiceModeListener(this);
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

	// Al mostrar el menú de opciones.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_carrito_activity, menu);
		return true;
	}

	// Al seleccionar una opción del menú de opciones.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Dependiendo de la opción de menú seleccionada.
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				break;
			case R.id.mnuHacerPedido:
				mostrarPedido();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void mostrarPedido() {
		// Creo un intent explícito para mostrar la actividad Pedido.
		Intent i = new Intent(this, PedidoActivity.class);
		startActivity(i);
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

	public void btnCarritoVacioOnClick(View v) {
		// Creo el intent explícito y muestro la actividad CatalogoActivity.
		Intent i = new Intent(this, CatalogoActivity.class);
		startActivity(i);
	}

	// Crea el cursor.
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// Consulto a través del content provider los registros con unidades a
		// pedir.
		Uri uri = Uri.parse("content://es.iessaladillo.tienda/productos");
		CursorLoader cLoader = new CursorLoader(this, uri, DAO.PRO_TODOS,
				DAO.FLD_PRO_VEN + " > 0", null, null);
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
		// Para que actualice los datos al volver.
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
			TextView lblUnidades;
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
			// Obtengo los datos desde el cursor y los escribo en las
			// vistas correspondientes.
			contenedor.lblNombre.setText(c.getString(c
					.getColumnIndex(DAO.FLD_PRO_NOM)));
			contenedor.lblUnidades.setText(getResources().getString(
					R.string.unidades_pedidas)
					+ c.getString(c.getColumnIndex(DAO.FLD_PRO_VEN)));
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
			View vistaFila = inflador.inflate(R.layout.fila_carrito_activity,
					null);
			// Creo el contenedor de vista, lo relleno y lo almaceno en la
			// propiedad Tag de la vista-fila.
			ContenedorVistas contenedor = new ContenedorVistas();
			// Rellenar el contenedor con las vistas obtenidas
			contenedor.imgFoto = (ImageView) vistaFila
					.findViewById(R.id.imgFoto);
			contenedor.lblNombre = (TextView) vistaFila
					.findViewById(R.id.lblNombre);
			contenedor.lblUnidades = (TextView) vistaFila
					.findViewById(R.id.lblUnidades);
			vistaFila.setTag(contenedor);
			// Retorno la vista-fila.
			return vistaFila;
		}

	}

	// Al pulsar un elemento del menú contextual.
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		// Obtengo un array que indica para cada elemento de la lista si está
		// seleccionado o no.
		@SuppressWarnings("unused")
		SparseBooleanArray elementos = lstProductos.getCheckedItemPositions();
		// Switch on the item’s ID to find the action the user selected
		switch (item.getItemId()) {
			case R.id.mnuQuitar:
				Toast.makeText(this, "Vas a eliminar", Toast.LENGTH_LONG)
						.show();
				break;
			default:
				return false;
		}
		return true;
	}

	// Al crear el menú contextual.
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		// Inflo la especificación del menú contextual sobre el menú.
		MenuInflater inflater = this.getMenuInflater();
		inflater.inflate(R.menu.contextual_carrito_activity, menu);
		return true;
	}

	public void onDestroyActionMode(ActionMode mode) {
	}

	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		// Comportamiento por defecto.
		return true;
	}

	// Al cambiar el número de elementos seleccionados.
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onItemCheckedStateChanged(ActionMode mode, int position,
			long id, boolean checked) {
		// Obtengo el número de elementos seleccionados en la lista.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			int seleccionados = lstProductos.getCheckedItemCount();
			mode.setTitle(seleccionados + getString(R.string.seleccionados));
		}
	}

}

// public class CarritoActivity extends Activity {
// private TextView cresumenDetalle;
// private String resumen;
// private Button bAnadirMasProductos;
// private Button bAnadirObservaciones;
// private GestorBD usdbh = new GestorBD(this);
//
// /** Called when the activity is first created. */
// @Override
// public void onCreate(Bundle savedInstanceState) {
// super.onCreate(savedInstanceState);
// setContentView(R.layout.carrito_activity);
//
// // ProductosSQLiteHelper usdbh = new ProductosSQLiteHelper(this,
// // "DBTienda", null, 1);
// SQLiteDatabase db = usdbh.getWritableDatabase();
//
// String sql =
// "SELECT pro_nombre, pro_unidades_sel FROM t_producto WHERE pro_unidades_sel>0";
// Cursor c = db.rawQuery(sql, null);
//
// int numRegistros = c.getCount();
//
// resumen = "";
// if (numRegistros > 0) {
// if (c.moveToFirst()) {
// // Recorremos el cursor hasta que no haya mÃ¡s registros
// do {
// resumen += c.getString(0) + " - " + c.getString(1)
// + " unidades\n";
// } while (c.moveToNext());
// }
// }
// if (resumen == "") {
// cresumenDetalle = (TextView) findViewById(R.id.resumenDetalle);
// cresumenDetalle.setText(this.getString(R.string.MinimoUnidad));
// }
// else {
// cresumenDetalle = (TextView) findViewById(R.id.resumenDetalle);
// cresumenDetalle.setText(resumen);
// }
//
// bAnadirMasProductos = (Button) findViewById(R.id.anadirMasProductos);
// bAnadirMasProductos.setOnClickListener(new OnClickListener() {
// public void onClick(View view) {
// lanzarListadoProductos();
// }
// });
//
// bAnadirObservaciones = (Button) findViewById(R.id.anadirObservaciones);
// bAnadirObservaciones.setOnClickListener(new OnClickListener() {
// public void onClick(View view) {
// lanzarObservaciones();
// }
// });
// }
//
// public void lanzarListadoProductos() {
// Intent i = new Intent(this, CatalogoActivity.class);
// startActivityForResult(i, 1234);
// }
//
// public void lanzarObservaciones() {
// Intent i = new Intent(this, PedidoActivity.class);
// startActivityForResult(i, 1234);
// }
// }
