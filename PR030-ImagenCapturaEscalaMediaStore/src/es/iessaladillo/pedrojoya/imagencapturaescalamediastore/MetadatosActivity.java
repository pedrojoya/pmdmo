package es.iessaladillo.pedrojoya.imagencapturaescalamediastore;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MetadatosActivity extends Activity {

	// Variables a nivel de clase.
	private EditText txtTitulo;
	private EditText txtDescripcion;
	private Uri uriFoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.metadatos);
		initVistas();
	}

	private void initVistas() {
		txtTitulo = (EditText) this.findViewById(R.id.txtTitulo);
		txtDescripcion = (EditText) this.findViewById(R.id.txtDescripcion);
		uriFoto = this.getIntent().getData();
		Cursor cursor = getContentResolver().query(uriFoto,
				new String[] { Media.DISPLAY_NAME, Media.DESCRIPTION }, null,
				null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			txtTitulo.setText(cursor.getString(0));
			txtDescripcion.setText(cursor.getString(1));
		}
	}

	public void btnGuardarOnClick(View v) {
		// Creo un ContentValues y lo relleno con los valores de título y
		// descripción.
		ContentValues metadatos = new ContentValues(3);
		metadatos.put(Media.DISPLAY_NAME, txtTitulo.getText().toString());
		metadatos.put(Media.DESCRIPTION, txtDescripcion.getText().toString());
		// Actualizo en la MediaStore los metadatos de la foto e informo al
		// usuario.
		getContentResolver().update(uriFoto, metadatos, null, null);
		Toast.makeText(this, R.string.metadatos_guardados, Toast.LENGTH_SHORT)
				.show();
		// Termino la actividad.
		finish();
	}

}
