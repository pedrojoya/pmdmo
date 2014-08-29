package es.iessaladillo.pedrojoya.pr021;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,
        OnItemSelectedListener {

    // Vistas.
    private Spinner spnPais;
    private Button btnMostrar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        getVistas();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        btnMostrar = (Button) findViewById(R.id.btnMostrar);
        btnMostrar.setOnClickListener(this);
        spnPais = (Spinner) findViewById(R.id.spnPais);
        // Se crea el adaptador y se le asigna al spinner.
        PaisesAdapter adaptador = new PaisesAdapter(this, getPaises());
        spnPais.setAdapter(adaptador);

        // La actividad acutará como listener cuando se seleccione un elemento.
        spnPais.setOnItemSelectedListener(this);
    }

    // Obtiene el ArrayList de paises.
    private ArrayList<Pais> getPaises() {
        ArrayList<Pais> paises = new ArrayList<Pais>();
        paises.add(new Pais(R.drawable.no_flag,
                getString(R.string.elija_un_pais)));
        paises.add(new Pais(R.drawable.alemania, "Alemania"));
        paises.add(new Pais(R.drawable.dinamarca, "Dinamarca"));
        paises.add(new Pais(R.drawable.finlandia, "Finlandia"));
        paises.add(new Pais(R.drawable.francia, "Francia"));
        paises.add(new Pais(R.drawable.grecia, "Grecia"));
        paises.add(new Pais(R.drawable.holanda, "Holanda"));
        paises.add(new Pais(R.drawable.irlanda, "Irlanda"));
        paises.add(new Pais(R.drawable.islandia, "Islandia"));
        paises.add(new Pais(R.drawable.lituania, "Lituania"));
        paises.add(new Pais(R.drawable.noruega, "Noruega"));
        paises.add(new Pais(R.drawable.polonia, "Polonia"));
        paises.add(new Pais(R.drawable.portugal, "Portugal"));
        paises.add(new Pais(R.drawable.spain, "España"));
        return paises;
    }

    // Al pulsar btnMostrar.
    @Override
    public void onClick(View v) {
        // Se muestra el nombre del país seleccionado.
        Pais pais = (Pais) spnPais.getSelectedItem();
        if (!pais.getNombre().equals(getString(R.string.elija_un_pais))) {
            Toast.makeText(this, pais.getNombre(), Toast.LENGTH_SHORT).show();
        }
    }

    // Al seleccionar un elemento.
    @Override
    public void onItemSelected(AdapterView<?> spn, View v, int position, long id) {
        // Se activa o desactiva el botón dependiendo de la selección.
        btnMostrar.setEnabled(spnPais.getSelectedItemPosition() != 0);
    }

    // Obigatorio en interfaz OnItemSelectedListener.
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

}
