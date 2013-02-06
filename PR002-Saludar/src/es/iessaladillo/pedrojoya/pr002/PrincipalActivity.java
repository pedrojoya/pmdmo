package es.iessaladillo.pedrojoya.pr002;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

// Actividad Principal
public class PrincipalActivity extends Activity {

	// Variables a nivel de clase.
	Context contexto;
	CheckBox chkEducado;

	// Cuando se crea la actividad.
	@Override
	public void onCreate(Bundle savedInstanceState) {

		// Se llama al método onCreate de la actividad padre.
		super.onCreate(savedInstanceState);

		// Establece el layout que se usará para la actividad.
		this.setContentView(R.layout.main);

		// Almaceno el contexto.
		contexto = this;

		// Obtengo la referencia al checkbox.
		chkEducado = (CheckBox) this.findViewById(R.id.chkEducado);

		// Establezco el valor inicial del checkbox para que esté chequeado.
		chkEducado.setChecked(true);

		// Obtengo la referencia al botón.
		Button btnSaludar = (Button) this.findViewById(R.id.btnSaludar);

		// Click sobre btnSaludar.
		btnSaludar.setOnClickListener(new OnClickListener() {

			public void onClick(View b) {

				// Obtengo la referencia a txtNombre.
				EditText txtNombre = (EditText) findViewById(R.id.txtNombre);

				// Creo el mensaje a mostrar.
				String mensaje = contexto.getString(R.string.buenos_dias);
				if (chkEducado.isChecked()) {
					mensaje = mensaje + " "
							+ contexto.getString(R.string.tenga_usted);
				}
				mensaje += " " + txtNombre.getText();

				// Muestro el saludo nominativo en un Toast.
				Toast.makeText(contexto, mensaje, Toast.LENGTH_LONG).show();
			}

		});

		// Click sobre chkEducado.
		chkEducado.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				// Muestro un mensaje informando de si estamos en modo educado o
				// no.
				String mensaje = contexto.getString(R.string.modo_educado)
						+ " ";
				if (isChecked) {
					mensaje += contexto.getString(R.string.on);
				} else {
					mensaje += contexto.getString(R.string.off);
				}
				Toast.makeText(contexto, mensaje, Toast.LENGTH_LONG).show();

			}

		});
	}
}