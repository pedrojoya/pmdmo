package es.iessaladillo.pedrojoya.pr022;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // Constantes.
    private static final int NUM_TARJETAS = 4;

    // Variables a nivel de clase.
    Ejercicio ejercicio;

    // Vistas.
    private RelativeLayout[] tarjetas;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        getVistas();
        // Se obtienen los datos del ejercicio.
        ejercicio = getEjercicio();
        // Se muestran los datos del ejercicio.
        showEjercicio();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        tarjetas = new RelativeLayout[Ejercicio.NUM_RESPUESTAS];
        tarjetas[0] = (RelativeLayout) findViewById(R.id.rlOpcion1);
        tarjetas[1] = (RelativeLayout) findViewById(R.id.rlOpcion2);
        tarjetas[2] = (RelativeLayout) findViewById(R.id.rlOpcion3);
        tarjetas[3] = (RelativeLayout) findViewById(R.id.rlOpcion4);
    }

    // Obtiene los datos del ejercicio.
    private Ejercicio getEjercicio() {
        Respuesta[] respuestas = new Respuesta[] {
                new Respuesta("apple", R.drawable.manzana),
                new Respuesta("stawberry", R.drawable.manzana),
                new Respuesta("banana", R.drawable.manzana),
                new Respuesta("pear", R.drawable.manzana) };
        return new Ejercicio("manzana", respuestas, 0);
    }

    // Muestra los datos del ejercicio en las correspondientes vistas.
    private void showEjercicio() {
        ((TextView) findViewById(R.id.lblConcepto)).setText(ejercicio
                .getPregunta());
        Respuesta respuesta;
        for (int i = 0; i < Ejercicio.NUM_RESPUESTAS; i++) {
            respuesta = ejercicio.getRespuesta(i);
            ((TextView) tarjetas[i].findViewById(R.id.lblOpcion))
                    .setText(respuesta.getTexto());
            ((ImageView) tarjetas[i].findViewById(R.id.imgOpcion))
                    .setImageResource(respuesta.getResIdImagen());
        }
    }

    // Al hacer click sobre btnToastDinamico.
    public void btnToastDinamicoOnClick(View v) {
        // Muestro un toast creado dinámicamente.
        mostrarTostada(R.string.toast_creado_dinamicamente,
                R.drawable.ic_launcher);
    }

    // Al hacer click sobre btnToastDinamico.
    public void btnToastLayoutOnClick(View v) {
        // Muestro un toast con layout personalizado.
        mostrarTostadaLayout(R.string.toast_con_layout_propio, R.layout.toast,
                R.id.lblMensaje);
    }

    // Recrea el layout res/layout/transient_notification.xml de la
    // plataforma.
    private void mostrarTostada(int stringResId, int drawableResId) {
        // Obtengo el contexto.
        Context contexto = getApplicationContext();
        // Creo un objeto tostada.
        Toast tostada = new Toast(contexto);
        // Creo un LinearLayout como padre del layout para la tostada.
        LinearLayout padre = new LinearLayout(contexto);
        padre.setBackgroundResource(R.drawable.toast_frame);
        // Creo un TextView, le asigno el texto del mensaje y
        // el icono y se lo añado al LinearLayout.
        TextView texto = new TextView(contexto);
        texto.setText(stringResId);
        texto.setTextAppearance(contexto, android.R.style.TextAppearance_Small);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, 0);
        texto.setLayoutParams(params);
        texto.setGravity(Gravity.CENTER);
        texto.setShadowLayer(2.75f, 0, 0, Color.parseColor("#BB000000"));
        texto.setCompoundDrawablesWithIntrinsicBounds(drawableResId, 0, 0, 0);
        texto.setCompoundDrawablePadding(10);
        padre.addView(texto);
        // Establezco el LinarLayout como la vista
        // que debe mostrar la tostada.
        tostada.setView(padre);
        // Establezco la duración de la tostada.
        tostada.setDuration(Toast.LENGTH_SHORT);
        // Muestro la tostada.
        tostada.show();
    }

    private void mostrarTostadaLayout(int stringResId, int layoutId,
            int textViewId) {
        // Obtengo el contexto.
        Context contexto = getApplicationContext();
        try {
            // Inflo el layout.
            View padre = LayoutInflater.from(contexto).inflate(layoutId, null);
            // Escribo el mensaje en el TextView.
            TextView lblMensaje = (TextView) padre.findViewById(textViewId);
            if (lblMensaje != null) {
                lblMensaje.setText(stringResId);
                // Creo un objeto tostada.
                Toast tostada = new Toast(contexto);
                // Establezco el LinarLayout como la vista
                // que debe mostrar la tostada.
                tostada.setView(padre);
                // Establezco la duración de la tostada.
                tostada.setDuration(Toast.LENGTH_SHORT);
                // Muestro la tostada.
                tostada.show();
            } else {
                // Si hay problema muestro un Toast estándar
                Toast.makeText(contexto, stringResId, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            // Si hay problema muestro un Toast estándar.
            Toast.makeText(contexto, stringResId, Toast.LENGTH_LONG).show();
        }
    }

}
