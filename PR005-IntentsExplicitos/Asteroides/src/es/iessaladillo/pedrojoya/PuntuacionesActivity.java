package es.iessaladillo.pedrojoya;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class PuntuacionesActivity extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.puntuaciones_activity);
		setListAdapter(new MiAdaptador(this,
				MainActivity.almacen.listaPuntuaciones(10)));
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
		Object o = getListAdapter().getItem(position);
		Toast.makeText(
				this,
				"Selección: " + Integer.toString(position) + " - "
						+ o.toString(), Toast.LENGTH_LONG).show();
	}
}