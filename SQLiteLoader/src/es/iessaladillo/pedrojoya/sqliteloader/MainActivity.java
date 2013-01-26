package es.iessaladillo.pedrojoya.sqliteloader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void btnNuevoOnClick(View v) {
		Intent i = new Intent(this, AlumnoActivity.class);
		i.putExtra("modo", AlumnoActivity.MODO_AGREGAR);
	}

}
