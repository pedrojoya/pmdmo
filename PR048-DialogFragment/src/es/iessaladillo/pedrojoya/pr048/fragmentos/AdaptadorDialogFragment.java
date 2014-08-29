package es.iessaladillo.pedrojoya.pr048.fragmentos;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import es.iessaladillo.pedrojoya.pr048.R;
import es.iessaladillo.pedrojoya.pr048.adaptadores.AdaptadorAlbumes;
import es.iessaladillo.pedrojoya.pr048.modelos.Album;

public class AdaptadorDialogFragment extends DialogFragment {

    // Variables.
    private AdaptadorDialogListener mListener = null;
    private ArrayList<Album> mAlbumes;

    // Interfaz pública para comunicación con la actividad.
    public interface AdaptadorDialogListener {
        public void onListItemClick(DialogFragment dialog, Album album);
    }

    // Al crear el diálogo. Retorna el diálogo configurado.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.album);
        b.setIcon(R.drawable.ic_launcher);
        // Se crea el array de datos.
        mAlbumes = getDatos();
        // Se crea y asigna el adaptador.
        AdaptadorAlbumes adaptador = new AdaptadorAlbumes(this.getActivity(),
                getDatos());
        b.setAdapter(adaptador, new OnClickListener() {
            // Cuando se hace click sobre un elemento de la lista.
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Se notifica el evento al listener pasándole el álbum pulsado.
                mListener.onListItemClick(AdaptadorDialogFragment.this,
                        mAlbumes.get(which));
            }
        });
        // Se retorna el diálogo.
        return b.create();
    }

    // Cuando se enlaza el fragmento con la actividad.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Establece la actividad como listener de los eventos del diálogo.
        try {
            mListener = (AdaptadorDialogListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz, se lanza excepción.
            throw new ClassCastException(activity.toString()
                    + " debe implementar SeleccionDirectaDialogListener");
        }
    }

    // Retorna la lista de datos.
    private ArrayList<Album> getDatos() {
        ArrayList<Album> albumes = new ArrayList<Album>();
        albumes.add(new Album(R.drawable.animal, "Veneno", "1977"));
        albumes.add(new Album(R.drawable.art, "Seré mecánico por ti", "1981"));
        albumes.add(new Album(R.drawable.bridge, "Echate un cantecito", "1992"));
        albumes.add(new Album(R.drawable.flag, "Está muy bien eso del cariño",
                "1995"));
        albumes.add(new Album(R.drawable.food, "Punta Paloma", "1997"));
        albumes.add(new Album(R.drawable.fruit, "Puro Veneno", "1998"));
        albumes.add(new Album(R.drawable.furniture, "La familia pollo", "2000"));
        albumes.add(new Album(R.drawable.glass, "Un ratito de gloria", "2001"));
        albumes.add(new Album(R.drawable.plant, "El hombre invisible", "2005"));
        return albumes;
    }

}
