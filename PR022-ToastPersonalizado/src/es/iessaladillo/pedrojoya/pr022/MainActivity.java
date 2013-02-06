package es.iessaladillo.pedrojoya.pr022;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
