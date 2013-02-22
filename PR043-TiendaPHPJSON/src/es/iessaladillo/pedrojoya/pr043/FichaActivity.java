package es.iessaladillo.pedrojoya.pr043;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FichaActivity extends Activity {
	private String vproductoNombre;
	private String vproductoFoto;
	private String vproductoDescripcion;
	private String vpkProducto;
	private String vunidadesSel;
	private TextView cproductoNombre;
	private TextView cproductoDescripcion;
	private EditText eNumUnidades;
	private Button bAnadirCarrito;
	private Button bResumenCompra;
	private Button bSeguirComprando;
	private int unidadesSeleccionadas;
	private Bitmap loadedImage;
	private GestorBD usdbh = new GestorBD(this);

	// Al mostrar el menú de opciones.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_ficha_activity, menu);
		return true;
	}
	
	// Obtiene la referencia a las vistas.
	private void getVistas() {
		
	}
	
	// Al crear la actividad.
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ficha_activity);
		// Obtengo los extras con los que me han llamado.
		Bundle extras = getIntent().getExtras();
		long idProducto = extras.getLong("idProducto");
		// Obtengo la referencia a las vistas.
		getVistas();
		// Cargo los datos del producto.
		

		// ProductosSQLiteHelper usdbh = new ProductosSQLiteHelper(this,
		// "DBTienda", null, 1);
		SQLiteDatabase db = usdbh.getWritableDatabase();

		String sql = "SELECT pro_nombre, pro_descripcion, pro_imagen, pk_producto, pro_unidades_sel FROM t_producto WHERE pk_producto='"
				+ idProducto + "'";
		Cursor c = db.rawQuery(sql, null);

		int numRegistros = c.getCount();

		// Toast.makeText(getApplicationContext(), sql,
		// Toast.LENGTH_SHORT).show();
		// Toast.makeText(getApplicationContext(), "Registros: "+numRegistros,
		// Toast.LENGTH_SHORT).show();

		if (numRegistros == 0) {
			cproductoNombre = (TextView) findViewById(R.id.productoNombre);
			cproductoNombre.setVisibility(0);
			cproductoDescripcion = (TextView) findViewById(R.id.productoDescripcion);
			cproductoDescripcion.setVisibility(0);
			ImageView imageView = (ImageView) findViewById(R.id.productoImagen);
			imageView.setVisibility(0);
			bAnadirCarrito = (Button) findViewById(R.id.btnAcceder);
			bAnadirCarrito.setVisibility(0);
			Toast.makeText(getApplicationContext(),
					this.getString(R.string.ErrorSeleccionaProducto),
					Toast.LENGTH_SHORT).show();
		}
		else {
			if (c.moveToFirst()) {
				vproductoNombre = c.getString(0);
				vproductoDescripcion = c.getString(1);
				vproductoFoto = c.getString(2);
				vpkProducto = c.getString(3);
				vunidadesSel = c.getString(4);

				cproductoNombre = (TextView) findViewById(R.id.productoNombre);
				cproductoNombre.setText(vproductoNombre);

				// Toast.makeText(getApplicationContext(), "Foto " +
				// vproductoFoto, Toast.LENGTH_SHORT).show();

				/* String uri = "drawable/"+vproductoFoto; int imageResource =
				 * getResources().getIdentifier(uri, null, getPackageName());
				 * ImageView imageView = (ImageView)
				 * findViewById(R.id.productoImagen); Drawable image =
				 * getResources().getDrawable(imageResource);
				 * imageView.setImageDrawable(image); */

				downloadFile("http://www.jrcj.com/android/imagenes/"
						+ vproductoFoto + "");

				cproductoDescripcion = (TextView) findViewById(R.id.productoDescripcion);
				cproductoDescripcion.setText(vproductoDescripcion);

				eNumUnidades = (EditText) findViewById(R.id.productoUnidades);
				eNumUnidades.setText(vunidadesSel);

				bAnadirCarrito = (Button) findViewById(R.id.anadirCarro);
				bAnadirCarrito.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						eNumUnidades = (EditText) findViewById(R.id.productoUnidades);
						// Toast.makeText(getApplicationContext(),
						// eNumUnidades.getText(), Toast.LENGTH_SHORT).show();
						if (eNumUnidades.getText().toString() == "") {
							Toast.makeText(getApplicationContext(),
									getString(R.string.MinimoUnidad),
									Toast.LENGTH_SHORT).show();
						}
						else {
							unidadesSeleccionadas = Integer
									.parseInt(eNumUnidades.getText().toString());
							if (unidadesSeleccionadas == 0) {
								Toast.makeText(getApplicationContext(),
										getString(R.string.MinimoUnidad),
										Toast.LENGTH_SHORT).show();
							}
							else {
								// Toast.makeText(getApplicationContext(),
								// "Unidades " + unidadesSeleccionadas,
								// Toast.LENGTH_SHORT).show();
								ActualizaUnidades(vpkProducto,
										unidadesSeleccionadas);
							}
						}
					}
				});
			}
			else {
				Toast.makeText(getApplicationContext(),
						getString(R.string.ErrorSeleccionaProducto),
						Toast.LENGTH_SHORT).show();
			}
		}
		bResumenCompra = (Button) findViewById(R.id.resumenCompra);
		bResumenCompra.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarResumenCompra();
			}
		});
		bSeguirComprando = (Button) findViewById(R.id.listadoProductos);
		bSeguirComprando.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarCatalogoProd();
			}
		});
	}
	
	// Carga los datos del producto provenientes de la BD.
	private void cargarProducto(long id) {
		// Consulto en el content provider los datos del producto.
		Uri uri = Uri.parse("content://es.iessaladillo.tienda/producto/" + id);
		CursorLoader cLoader = new CursorLoader(this, uri, GestorBD.PRO_TODOS,
				null, null, null);
		Cursor cursor = cLoader.loadInBackground();
		if (cursor.getCount() == 1) {
			// Lo muestro en las vistas correspondientes.
			cursor.moveToFirst();
			
			
			
			alumno = GestorBD.cursorToAlumno(cursor);
		} else {
			// Si no se ha encontrado el alumno en la BD, informo y paso al modo
			// Agregar.
			Toast.makeText(this, R.string.producto_no_encontrado,
					Toast.LENGTH_LONG).show();
		}
		// Cierro el cursor.
		cursor.close();
	}
	
	public void lanzarResumenCompra() {
		Intent i = new Intent(this, CarritoActivity.class);
		startActivityForResult(i, 1234);
	}

	public void lanzarCatalogoProd() {
		Intent i = new Intent(this, CatalogoActivity.class);
		startActivityForResult(i, 1234);
	}

	public void ActualizaUnidades(String pkProducto, int unidades) {
		SQLiteDatabase db = usdbh.getWritableDatabase();

		String s = "UPDATE t_producto SET pro_unidades_sel = '" + unidades
				+ "' WHERE pk_producto = '" + pkProducto + "'";
		// Toast.makeText(getApplicationContext(), s,
		// Toast.LENGTH_SHORT).show();
		db.execSQL(s);
		Toast.makeText(getApplicationContext(),
				unidades + " " + this.getString(R.string.UnidadesAlCarrito),
				Toast.LENGTH_SHORT).show();
	}

	void downloadFile(String imageHttpAddress) {
		URL imageUrl = null;
		ImageView imageView = (ImageView) findViewById(R.id.productoImagen);
		try {
			imageUrl = new URL(imageHttpAddress);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.connect();
			loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
			imageView.setImageBitmap(loadedImage);
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(),
					"Error cargando la imagen: " + e.getMessage(),
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}
}
