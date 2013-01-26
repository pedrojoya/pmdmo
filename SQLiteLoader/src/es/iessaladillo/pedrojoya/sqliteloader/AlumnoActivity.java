package es.iessaladillo.pedrojoya.sqliteloader;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AlumnoActivity extends Activity {

	public static final int MODO_AGREGAR = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alumno);
	}

}
