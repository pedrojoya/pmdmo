package es.iessaladillo.pedrojoya.PR004;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PrincipalActivity extends Activity {

	// Constantes.
	private final int RC_HACER_FOTO = 1; // Result Code intent Hacer Foto
	
	// Cuando se crea la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    /** Manejador de los clicks de los botones. Indicado en la prop. onClick de cada botón.
	 * @param v Botón sobre el que se ha hecho click.
	 */
	public void manejarClick(View v) {
		// Intent implícito que será enviado.
		Intent intencion;
		// Dependiendo del botón pulsado, construyo el intent adecuado y llamo al componente.
		switch(v.getId()) {
		case R.id.btnNavegar:
			/* Construye el intent pasándole la acción y la URI de los datos.
			 * Para obtener URI utiliza el método estático parse de la clase Ui para 
			 * obtener el objeto Uri a partir de una cadena de caracteres que 
			 * especifica una uri.
			 */
			// Acción--> VER. Uri--> URL.
			intencion = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.iessaladillo.es"));
			// Envío el mensaje de intención para que lo reciba un componente Activity.
			startActivity(intencion);
			break;
		case R.id.btnBuscar:
			// Acción--> BUSCAR EN INTERNET. Extra -> Término de consulta.
			intencion = new Intent(Intent.ACTION_WEB_SEARCH);
			intencion.putExtra(SearchManager.QUERY, "IES Saladillo");
			startActivity(intencion);
			break;
		case R.id.btnLlamar:
			// Acción--> LLAMAR. Uri--> tel:num.
			intencion = new Intent(Intent.ACTION_CALL, Uri.parse("tel:(+34)123456789"));
			startActivity(intencion);
			break;
		case R.id.btnMarcar:
			// Acción--> MARCAR. Uri--> tel:num.
			intencion = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:(+34)12345789"));
			startActivity(intencion);
			break;
		case R.id.btnMostrarMapa:
			// Acción--> VER. Uri--> geo:latitud,longitud?z=zoom.
			intencion = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:50.123,7.1434?z=19"));
			startActivity(intencion);
			break;
		case R.id.btnBuscarMapa:
			// Acción--> VER. Uri--> geo:latitud,longitud?q=consulta.
			intencion = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=duque de rivas, Algeciras"));
			startActivity(intencion);
			break;
		case R.id.btnHacerFoto:
			/* Al crear el intent se indica sólo lo acción (captura de imagen), 
			 * que está definida en el paquete android.media.action
			 */
			intencion = new Intent("android.media.action.IMAGE_CAPTURE");
			/* Se realiza el envío el intent, pero indicando que deberemos recibir
			 * una respuesta (asíncrona).
			 */
			this.startActivityForResult(intencion, RC_HACER_FOTO);
			break;
		case R.id.btnMostrarContactos:
			// Acción--> VER. Uri--> Accederá al proveedor de contenidos de la aplicación de contactos.
			intencion = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/"));
			startActivity(intencion);
			break;
		}
	}

	/* Cuando está disponible el resultado de una actividad llamada.
	 * Se recibe el código con el que se llamo a la actividad,
	 * el código de respuesta (si ha ido bien o mal),
	 * y un intent con los datos retornados por la actividad llamada.
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Si retorna un resultado correcto.
		if (resultCode == Activity.RESULT_OK) {
			// Dependiendo de la actividad llamada que ha retornado el resultado.
			switch(requestCode) {
			case RC_HACER_FOTO:
				/* Obtengo una cadena con la URI de la foto realizada
				 * y la muestro en un Toast.
				 */
				String uriFoto = data.toURI();
				Toast.makeText(this, this.getString(R.string.uri_foto) + ": " + uriFoto, Toast.LENGTH_LONG).show();
			}
		}
	}
}