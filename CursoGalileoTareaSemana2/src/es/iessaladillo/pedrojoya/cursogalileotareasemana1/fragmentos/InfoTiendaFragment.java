package es.iessaladillo.pedrojoya.cursogalileotareasemana1.fragmentos;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.R;

public class InfoTiendaFragment extends Fragment {

    // Vistas.
    private ImageView btnLlamar;
    private TextView lblTelefono;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla el layout correspondiente.
        View v = inflater.inflate(R.layout.fragment_tienda_info, container,
                true);
        // Se obtienen e inicializan las vistas.
        getVistas(v);
        // Se retorna la vista que debe mostrar el fragmento.
        return v;
    }

    // Obtiene e inicializa las vistas.
    private void getVistas(View v) {
        // Se obtienen las vistas.
        btnLlamar = (ImageView) v.findViewById(R.id.btnLlamar);
        lblTelefono = (TextView) v.findViewById(R.id.lblTelefono);
        btnLlamar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnLlamarOnClick();
            }
        });

    }

    // Al hacer click sobre btnLlamar.
    private void btnLlamarOnClick() {
        // Se crea un intent implícito para mostrar el dial, que recibe como
        // data la URI con el número.
        String telefono = lblTelefono.getText().toString();
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + telefono));
        // Se envía el intent.
        startActivity(i);
    }
}
