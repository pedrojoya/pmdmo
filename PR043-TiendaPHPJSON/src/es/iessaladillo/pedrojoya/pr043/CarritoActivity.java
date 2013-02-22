package es.iessaladillo.pedrojoya.pr043;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CarritoActivity extends Activity {
	private TextView cresumenDetalle;
	private String resumen;
	private Button bAnadirMasProductos;
	private Button bAnadirObservaciones;
	private GestorBD usdbh = new GestorBD(this);

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.carrito_activity);

		// ProductosSQLiteHelper usdbh = new ProductosSQLiteHelper(this,
		// "DBTienda", null, 1);
		SQLiteDatabase db = usdbh.getWritableDatabase();

		String sql = "SELECT pro_nombre, pro_unidades_sel FROM t_producto WHERE pro_unidades_sel>0";
		Cursor c = db.rawQuery(sql, null);

		int numRegistros = c.getCount();

		resumen = "";
		if (numRegistros > 0) {
			if (c.moveToFirst()) {
				// Recorremos el cursor hasta que no haya más registros
				do {
					resumen += c.getString(0) + " - " + c.getString(1)
							+ " unidades\n";
				} while (c.moveToNext());
			}
		}
		if (resumen == "") {
			cresumenDetalle = (TextView) findViewById(R.id.resumenDetalle);
			cresumenDetalle.setText(this.getString(R.string.MinimoUnidad));
		}
		else {
			cresumenDetalle = (TextView) findViewById(R.id.resumenDetalle);
			cresumenDetalle.setText(resumen);
		}

		bAnadirMasProductos = (Button) findViewById(R.id.anadirMasProductos);
		bAnadirMasProductos.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarListadoProductos();
			}
		});

		bAnadirObservaciones = (Button) findViewById(R.id.anadirObservaciones);
		bAnadirObservaciones.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarObservaciones();
			}
		});
	}

	public void lanzarListadoProductos() {
		Intent i = new Intent(this, CatalogoActivity.class);
		startActivityForResult(i, 1234);
	}

	public void lanzarObservaciones() {
		Intent i = new Intent(this, PedidoActivity.class);
		startActivityForResult(i, 1234);
	}
}
