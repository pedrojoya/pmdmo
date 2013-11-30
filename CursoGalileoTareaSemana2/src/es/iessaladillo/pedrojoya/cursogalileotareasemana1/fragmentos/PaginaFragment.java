package es.iessaladillo.pedrojoya.cursogalileotareasemana1.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.R;

public class PaginaFragment extends Fragment {

    // Constantes.
    public static final String PAR_RESID_FOTO = "resIdFoto";

    // Vistas.
    private ImageView imgFoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla el layout correspondiente.
        View v = inflater.inflate(R.layout.fragment_pagina, container, false);
        // Se obtienen e inicializan las referencias a las vistas.
        getVistas(v);
        // Se muestra la foto correspondiente
        mostrarFoto();
        // Se retorna la vista que debe mostrar el fragmento.
        return v;
    }

    // Obtiene e inicializa las vistas.
    private void getVistas(View v) {
        imgFoto = (ImageView) v.findViewById(R.id.imgFoto);
    }

    // Establece como imagen del ImageView la foto correspondiente.
    private void mostrarFoto() {
        // Se obtiene el parámetro que me han pasado con el resId de la foto a
        // mostrar y se le asigna al ImageView.
        int resIdFoto = getArguments().getInt(PAR_RESID_FOTO,
                R.drawable.ic_launcher);
        imgFoto.setImageResource(resIdFoto);
    }

}
