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
		mostrarTostada(R.string.esta_tostada_esta_personalizada, R.drawable.ic_launcher);	
	}

	// Al hacer click sobre btnToastDinamico.
	public void btnToastLayoutOnClick(View v) {
		// Muestro un toast con layout personalizado.
		mostrarTostadaLayout(R.string.esta_tostada_esta_personalizada, R.layout.toast);	
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
        padre.setOrientation(LinearLayout.HORIZONTAL);
        padre.setBackgroundResource(R.drawable.toast_frame);
        // Creo un ImageView, la asigno la imagen pasada como parámetro
        // y se la añado al LinearLayout.
        ImageView imagen = new ImageView(contexto);
        LayoutParams params = new LayoutParams(40, 40, 0);
        params.gravity = Gravity.CENTER_VERTICAL;
        imagen.setLayoutParams(params);
        imagen.setImageResource(drawableResId);
        padre.addView(imagen);
        // Creo un TextView, le asigno el texto del mensaje y
        // se lo añado al LinearLayout.
        TextView texto = new TextView(contexto);
        texto.setText(stringResId);
        texto.setTextAppearance(contexto, android.R.style.TextAppearance_Small);
        params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
        params.gravity = Gravity.CENTER;
        params.leftMargin = 20;
        texto.setLayoutParams(params);
        texto.setShadowLayer(2.75f, 0, 0, Color.parseColor("#BB000000"));
        padre.addView(texto);
        // Establezco el LinarLayout como la vista 
        // que debe mostrar la tostada.
        tostada.setView(padre);
        // Establezco la duración de la tostada.
        tostada.setDuration(Toast.LENGTH_SHORT);
        // Muestro la tostada.
        tostada.show();
	}
	
	private void mostrarTostadaLayout(int stringResId, int layoutId) {
		// Obtengo el contexto.
		Context contexto = getApplicationContext();
		// Creo un objeto tostada.
		Toast tostada = new Toast(contexto);
		// Inflo el layout.
        View padre = LayoutInflater.from(contexto).inflate(layoutId, null);
        // Escribo el mensaje en el TextView.
        TextView lblMensaje = (TextView) padre.findViewById(R.id.lblMensaje);
        lblMensaje.setText(stringResId);
        // Establezco el LinarLayout como la vista 
        // que debe mostrar la tostada.
        tostada.setView(padre);
        // Establezco la duración de la tostada.
        tostada.setDuration(Toast.LENGTH_SHORT);
        // Muestro la tostada.
        tostada.show();
	}
	

}
