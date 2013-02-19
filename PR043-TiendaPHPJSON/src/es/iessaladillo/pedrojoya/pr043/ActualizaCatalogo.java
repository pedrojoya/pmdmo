package es.iessaladillo.pedrojoya.pr043;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActualizaCatalogo extends Activity {
	private Button bActualizaCatalogo;
	private TextView cProcesoInforma;
	private String sProcesoInforma;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actualizacatalogo);

		bActualizaCatalogo = (Button) findViewById(R.id.actualizarCatalogo);
		bActualizaCatalogo.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarActualizacionCatalogo();
			}
		});
	}

	public void lanzarActualizacionCatalogo() {
		// Creamos la base de datos
		GestorBD usdbh = new GestorBD(this);
		SQLiteDatabase db = usdbh.getWritableDatabase();

		// Si hemos abierto correctamente la base de datos
		if (db != null) {
			// Borramos todos los registros
			db.execSQL("DELETE FROM t_producto");

			sProcesoInforma = "";

			cProcesoInforma = (TextView) findViewById(R.id.procesoInforma);

			sProcesoInforma = sProcesoInforma + "Borrada la base de datos\n";
			cProcesoInforma.setText(sProcesoInforma);

			// Toast.makeText(getApplicationContext(), "entro",
			// Toast.LENGTH_SHORT).show();

			// Importamos las imágenes

			ArrayList parametros = new ArrayList();
			parametros.add("clave_acceso");
			parametros.add("alri23aklLL");
			// Llamada a Servidor Web PHP
			try {
				PostJSon post = new PostJSon();
				JSONArray datos = post.getServerData(parametros,
						"http://www.jrcj.com/android/exporta.php");
				if (datos != null && datos.length() > 0) {
					for (int i = 0; i < datos.length(); i++) {
						JSONObject json_data = datos.getJSONObject(i);
						// String pro_nombre =
						// json_data.getString("pro_nombre");
						int pk_producto = json_data.getInt("pro_id");
						String pro_nombre = json_data.getString("pro_nombre");
						String pro_descripcion = json_data
								.getString("pro_descripcion");
						String pro_imagen = json_data.getString("pro_imagen");
						// Toast.makeText(getBaseContext(), pro_nombre,
						// Toast.LENGTH_SHORT).show();
						sProcesoInforma = sProcesoInforma
								+ "Insertado producto " + pro_nombre + "\n";
						cProcesoInforma.setText(sProcesoInforma);
						String s = "INSERT INTO t_producto (pk_producto, pro_nombre, pro_descripcion, pro_imagen) VALUES ("
								+ pk_producto
								+ ", '"
								+ pro_nombre
								+ "', '"
								+ pro_descripcion + "', '" + pro_imagen + "')";

						db.execSQL(s);
					}
				}
				else {
					Toast.makeText(getBaseContext(),
							"Error al obtener los datos", Toast.LENGTH_SHORT)
							.show();
				}
			} catch (Exception e) {
				Toast.makeText(getBaseContext(),
						"Error al conectar con el servidor", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
}
