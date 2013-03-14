package es.iessaladillo.pedrojoya.pr047.actividades;

import es.iessaladillo.pedrojoya.pr047.R;
import es.iessaladillo.pedrojoya.pr047.R.layout;
import es.iessaladillo.pedrojoya.pr047.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
