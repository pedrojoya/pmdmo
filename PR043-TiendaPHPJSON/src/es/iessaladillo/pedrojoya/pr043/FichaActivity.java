package es.iessaladillo.pedrojoya.pr043;

import java.io.File;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.CursorLoader;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FichaActivity extends Activity {

	// Variables miembro.
	long idProducto;
	private TextView lblNombre;
	private TextView lblDescripcion;
	private ImageView imgFoto;
	private long unidadesPedidas;

	// Al crear la actividad.
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ficha_activity);
		// Obtengo los extras con los que me han llamado.
		Bundle extras = getIntent().getExtras();
		idProducto = extras.getLong("idProducto");
		// Activo el botón de Home en la Action Bar (si la versión lo permite).
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		// Obtengo la referencia a las vistas.
		getVistas();
		// Cargo los datos del producto.
		cargarProducto(idProducto);
	}

	// Al mostrar el menú de opciones.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_ficha_activity, menu);
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
			case R.id.mnuAgregarAlCarrito:
				agregarAlCarrito();
				break;
			case R.id.mnuVerCarrito:
				verCarrito();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	// Obtiene la referencia a las vistas.
	private void getVistas() {
		lblNombre = (TextView) this.findViewById(R.id.lblNombre);
		lblDescripcion = (TextView) this.findViewById(R.id.lblDescripcion);
		imgFoto = (ImageView) this.findViewById(R.id.imgFoto);
	}

	// Carga los datos del producto provenientes de la BD.
	private void cargarProducto(long id) {
		// Consulto en el content provider los datos del producto.
		Uri uri = Uri.parse("content://es.iessaladillo.tienda/productos/" + id);
		CursorLoader cLoader = new CursorLoader(this, uri, GestorBD.PRO_TODOS,
				null, null, null);
		Cursor cursor = cLoader.loadInBackground();
		if (cursor.getCount() == 1) {
			// Lo muestro en las vistas correspondientes.
			cursor.moveToFirst();
			lblNombre.setText(cursor.getString(cursor
					.getColumnIndex(GestorBD.FLD_PRO_NOM)));
			lblDescripcion.setText(cursor.getString(cursor
					.getColumnIndex(GestorBD.FLD_PRO_DES)));
			unidadesPedidas = cursor.getLong(cursor
					.getColumnIndex(GestorBD.FLD_PRO_VEN));
			// Obtengo el nombre del archivo de la foto.
			String sNombreImagen = cursor.getString(cursor
					.getColumnIndex(GestorBD.FLD_PRO_IMA));
			// Si el producto tiene foto la pongo, y si no pongo una por
			// defecto.
			if (sNombreImagen != null && !sNombreImagen.equals("")) {
				String sPath = Environment.getExternalStorageDirectory()
						+ "/tienda/" + sNombreImagen;
				File archivo = new File(sPath);
				if (archivo.exists()) {
					imgFoto.setImageURI(Uri.fromFile(archivo));
				}
				else {
					imgFoto.setImageResource(R.drawable.ic_iconopp);
				}
			}

		}
		else {
			// Si no se ha encontrado el alumno en la BD, informo y paso al modo
			// Agregar.
			Toast.makeText(this, R.string.producto_no_encontrado,
					Toast.LENGTH_LONG).show();
		}
		// Cierro el cursor.
		cursor.close();
	}

	private void agregarAlCarrito() {
		// Realizo el update en la base de datos a través del content provider.
		Uri uri = Uri.parse("content://es.iessaladillo.tienda/productos/"
				+ idProducto);
		ContentValues valores = new ContentValues();
		valores.put(GestorBD.FLD_PRO_VEN, unidadesPedidas + 1);
		if (getContentResolver().update(uri, valores, null, null) > 0) {
			// Incremento el número de unidades pedidas del artículo.
			unidadesPedidas += 1;
			Toast.makeText(this,
					R.string.agregado_al_carrito_una_unidad_del_producto,
					Toast.LENGTH_SHORT).show();
		}
	}

	private void verCarrito() {
		// Creo el intent explícito e inicio la actividad CarritoActivity.
		Intent i = new Intent(this, CarritoActivity.class);
		startActivity(i);
	}

}
