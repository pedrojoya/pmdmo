package es.iessaladillo.pedrojoya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button btnAcercaDe;
	public static AlmacenPuntuaciones almacen = new AlmacenPuntuacionesArray();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		btnAcercaDe = (Button) this.findViewById(R.id.btnAcercaDe);
		btnAcercaDe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				lanzarAcercaDe(null);
			}
		});
	}

	public void btnSalirOnClick(View v) {
		finish();
	}

	public void btnPuntuacionesOnClick(View v) {
		Intent i = new Intent(this, PuntuacionesActivity.class);
		startActivity(i);
	}

	public void lanzarAcercaDe(View v) {
		Intent i = new Intent(this, AcercaDeActivity.class);
		startActivity(i);
	}

	public void btnConfigurarOnClick(View v) {
		Intent i = new Intent(this, PreferenciasActivity.class);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.mnuAcercaDe:
				lanzarAcercaDe(null);
				break;
			case R.id.mnuConfig:
				btnConfigurarOnClick(null);
				break;
		}
		return true;
	}

}
