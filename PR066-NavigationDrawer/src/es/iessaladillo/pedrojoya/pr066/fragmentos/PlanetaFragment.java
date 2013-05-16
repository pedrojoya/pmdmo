package es.iessaladillo.pedrojoya.pr066.fragmentos;

import java.util.Locale;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import es.iessaladillo.pedrojoya.pr066.R;

public class PlanetaFragment extends Fragment {
    public static final String ARG_PLANET_NUMBER = "numPlaneta";

    // Constructor vacío. Obligatorio.
    public PlanetaFragment() {
    }

    // Al crear la vista correspondiente al layout del fragmento.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla la especificación XML del layout en el contenedor.
        View vistaFragmento = inflater.inflate(R.layout.fragment_planet,
                container, false);
        // Se obtiene el parámetro correspondiente al índice de planeta y se
        // muestra la imagen adecuada en el ImageView
        int i = getArguments().getInt(ARG_PLANET_NUMBER);
        String nombrePlaneta = getResources().getStringArray(
                R.array.planets_array)[i];
        int resIdImagenPlaneta = getResources().getIdentifier(
                nombrePlaneta.toLowerCase(Locale.getDefault()), "drawable",
                getActivity().getPackageName());
        ((ImageView) vistaFragmento.findViewById(R.id.image))
                .setImageResource(resIdImagenPlaneta);
        // Se actualiza el título de la ActionBar con el nombre del planeta.
        getActivity().setTitle(nombrePlaneta);
        return vistaFragmento;
    }
}
