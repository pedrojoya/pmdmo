package es.iessaladillo.pedrojoya.cursogalileotareasemana1.actividades;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.R;

public class DetalleFotoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_foto);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detalle_foto, menu);
        return true;
    }

}