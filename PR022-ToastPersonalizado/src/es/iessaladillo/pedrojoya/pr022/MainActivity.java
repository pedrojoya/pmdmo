package es.iessaladillo.pedrojoya.pr022;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        getVistas();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        ((Button) findViewById(R.id.btnToastDinamico))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        // Se muestra un toast creado dinámicamente.
                        mostrarToastDinamico(
                                R.string.toast_creado_dinamicamente,
                                R.drawable.ic_launcher);
                    }
                });
        ((Button) findViewById(R.id.btnToastLayout))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        // Se muestra un toast creado dinámicamente.
                        mostrarToastLayout(R.string.toast_con_layout_propio,
                                R.layout.toast, R.id.lblMensaje);
                    }
                });
    }

    // Muestra un toast creado de forma dinámica que recrea el layout
    // res/layout/transient_notification.xml de la plataforma.
    private void mostrarToastDinamico(int stringResId, int drawableResId) {
        // Se crea el objeto Toast.
        Toast toast = new Toast(this);
        // Se crea el LinearLayout que actuará como raíz del layout del toast y
        // se establece su fondo.
        LinearLayout raiz = new LinearLayout(this);
        raiz.setBackgroundResource(R.drawable.toast_frame);
        // Se crea y configura el TextView que mostrará el mensaje.
        TextView texto = new TextView(this);
        texto.setText(stringResId);
        texto.setTextAppearance(getApplicationContext(),
                android.R.style.TextAppearance_Small);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, 0);
        texto.setLayoutParams(params);
        texto.setGravity(Gravity.CENTER);
        texto.setShadowLayer(2.75f, 0, 0, Color.parseColor("#BB000000"));
        // Se establece el icono que se mostrará a la izquierda del TextView.
        texto.setCompoundDrawablesWithIntrinsicBounds(drawableResId, 0, 0, 0);
        texto.setCompoundDrawablePadding(10);
        // Se agrega el TextView como hijo del LinearLayout.
        raiz.addView(texto);
        // Se establece el LinearLayout como vista que debe mostrar el toast.
        toast.setView(raiz);
        // Se establece la duración del toast.
        toast.setDuration(Toast.LENGTH_SHORT);
        // Se muestra el toast.
        toast.show();
    }

    // Muestra un toast con layout específico.
    private void mostrarToastLayout(int stringResId, int layoutId,
            int textViewId) {
        try {
            // Se infla el layout obteniendo la vista que omostará.
            View raiz = LayoutInflater.from(this).inflate(layoutId, null);
            // Se obtiene el TextView donde debe aparecer el mensaje.
            TextView lblMensaje = (TextView) raiz.findViewById(textViewId);
            if (lblMensaje != null) {
                // Se escribe el texto en el TextView.
                lblMensaje.setText(stringResId);
                // Se crea el objeto toast.
                Toast tostada = new Toast(this);
                // Se establece la vista que debe mostrar el toast.
                tostada.setView(raiz);
                // Se establece la duración de la tostada.
                tostada.setDuration(Toast.LENGTH_SHORT);
                // Se muestra el toast.
                tostada.show();
            } else {
                // Si hay algún problema se muestra un Toast estándar.
                Toast.makeText(this, stringResId, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            // Si hay algún problema se muestra un Toast estándar.
            Toast.makeText(this, stringResId, Toast.LENGTH_LONG).show();
        }
    }

}
