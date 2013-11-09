package es.iessaladillo.pedrojoya.cursogalileotareasemana1.actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.R;

public class ListaTiendasActivity extends Activity implements
        OnItemClickListener {

    private RelativeLayout rlListaTiendasVacia;
    private ListView lstTiendas;
    private ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tiendas);
        // Se obtienen e inicializan las vistas.
        getVistas();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        // Se obtienen las vistas.
        lstTiendas = (ListView) findViewById(R.id.lstTiendas);
        rlListaTiendasVacia = (RelativeLayout) findViewById(R.id.rlListaTiendasVacia);
        // Se establece la vista a mostrar cuando la lista esté vacía.
        lstTiendas.setEmptyView(rlListaTiendasVacia);
        // Se crea el adaptador para la lista, que usará para mostrar cada
        // elemento uno de los layouts predefinidos de Android y como fuente de
        // datos un array de cadenas de caracteres con los nombres de las
        // tiendas.
        String[] tiendas = getResources().getStringArray(R.array.tiendas);
        adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, tiendas);
        lstTiendas.setAdapter(adaptador);
        // Se establece que será la propia actividad quien responda a que se
        // haga click en un elemento de la lista.
        lstTiendas.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Al pulsar sobre el icono o texto de lista vacía.
    public void btnListaTiendasVaciaOnClick(View v) {
        // TODO ¿Mostrar actividad de Agregar?
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        // Se obtiene del adaptador de la lista los datos del elemento pulsado.
        String tienda = (String) lstTiendas.getItemAtPosition(position);
        // Se crea el intent para llamar a la actividad de detalle de tienda, y
        // se le pasa la tienda como dato extra.
        Intent i = new Intent(getApplicationContext(),
                DetalleTiendaActivity.class);
        i.putExtra(DetalleTiendaActivity.EXTRA_TIENDA, tienda);
        // Se inicia la actividad de detalle de tienda.
        startActivity(i);
    }

}
