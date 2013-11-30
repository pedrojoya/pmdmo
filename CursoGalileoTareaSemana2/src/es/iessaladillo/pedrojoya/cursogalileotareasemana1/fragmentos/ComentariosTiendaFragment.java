package es.iessaladillo.pedrojoya.cursogalileotareasemana1.fragmentos;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.R;

public class ComentariosTiendaFragment extends Fragment {

    // Vistas.
    private TextView lblComentarios;
    private EditText txtComentario;
    private ImageView btnEnviar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla el layout correspondiente.
        View v = inflater.inflate(R.layout.fragment_comentarios, container,
                true);
        // Se obtienen e inicializan las vistas.
        getVistas(v);
        return v;
    }

    // Al pulsar sobre el botón btnEnviar.
    public void btnEnviarOnClick() {
        // Se añade al TextView la cadena de cometario (si no está vacía)
        String comentario = txtComentario.getText().toString();
        if (!TextUtils.isEmpty(comentario)) {
            lblComentarios.append(comentario + "\n\n");
            txtComentario.setText("");
            // Se informa.
            Toast.makeText(this.getActivity(), "Comentario añadido",
                    Toast.LENGTH_LONG).show();
        }
    }

    // Obtiene e inicializa las vistas.
    private void getVistas(View v) {
        // Se obtienen las vistas.
        lblComentarios = (TextView) v.findViewById(R.id.lblComentarios);
        txtComentario = (EditText) v.findViewById(R.id.txtComentario);
        btnEnviar = (ImageView) v.findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnEnviarOnClick();
            }
        });
    }

}
