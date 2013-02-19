package test.Droidlogin;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import test.Droidlogin.library.PeticionWeb;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {

	// Constantes.
	public final String URL_BASE = "http://www.informaticasaladillo.es/android/droidlogin/";
	public final String PHP_ACCESO = "acces.php";
	public final String PHP_REGISTRO = "adduser.php";
	public final String HTML_REGISTRO = "adduser.html";
	// String URL_connect="http://www.scandroidtest.site90.com/acces.php";

	// Vistas.
	EditText txtUsuario;
	EditText txtPassword;
	Button btnLogin;
	TextView lblRegistrarse;

	// Miembros de clase.
	PeticionWeb peticion;						// Objeto HttpPost que realizará la petición Web.
	private ProgressDialog pdConsultando;	// Diálogo de proceso para indicar que se está consultando.
			
	boolean result_back;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Llamo al onCreate del padre.
		super.onCreate(savedInstanceState);
		// Establezo el layout que debe mostrar la actividad.
		setContentView(R.layout.main);
		// Obtengo la referencia a las vistas del layout.
		getVistas();
		// Creamos el objeto HttpPost.
		peticion = new PeticionWeb();
	}

	// Obtiene la referencia e inicializa las vistas del layout.
	private void getVistas() {
		txtUsuario = (EditText) findViewById(R.id.txtUsuario);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		lblRegistrarse = (TextView) findViewById(R.id.lblRegistrarse);
	}

	// Cuando se hace click sobre el botón btnLogin.
	public void btnLoginOnClick(View v) {
		// Obtenemos los datos de usuario y password.
		String usuario = txtUsuario.getText().toString();
		String passw = txtPassword.getText().toString();
		// Si no están vacíos.
		if (!(usuario.equals("")) && !(passw.equals(""))) {
			// Comprobamos que los datos de usuario son correctos en la BD MySQL online
			// mediante una tarea asíncrona a la que pasamos los datos de usuario y la url
			// del PHP que realizará la comprobación.
			new MySQLLoginAsyncTask().execute(usuario, passw);        		               
		}
		else {
			// Informo al usuario.
			Toast.makeText(this, R.string.error_vacios, Toast.LENGTH_SHORT).show();
		}
	}

	// Cuando se hace click sobre el botón lblRegistrarse.
	public void lblRegistrarseOnClick(View v) {
		// Creo un intent implícito que muestre en el navegador
		// la página adduser.html
		String url = URL_BASE + HTML_REGISTRO;
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);        		    
	}

	
	// Clase interna que implementa la tarea asíncrona de comprobar contra la BD MySQL online
	// si el usuario cuyos datos han sido introducidos existe en la tabla usuarios de la BD.
	class MySQLLoginAsyncTask extends AsyncTask<String, String, Integer> {
	
		// Constantes
		final int LOGIN_ERROR = -1;		// Error en la conexión o en procesamiento JSON.
		final int LOGIN_INVALIDO = 0;	// El login no es válido.
		final int LOGIN_VALIDO = 1;		// El login es válido.
		
		// Miembros de clase.
		String usuario;
		String password;
		
		// Se ejecuta en el hilo principal.
		protected void onPreExecute() {
			// Creo el ProgressDialog indicándole como contexto la actividad y lo muestro. 
			pdConsultando = new ProgressDialog(Login.this);
			pdConsultando.setMessage("Autenticando....");
			pdConsultando.setIndeterminate(false);
			pdConsultando.setCancelable(false);
			pdConsultando.show();
		}
	
		// Realiza la tarea en otro hilo. Recibe los argumentos indicados
		// en la llamada al método execute de la tarea en el hilo principal.
		// Retorna el estado del login (ver constantes)
		protected Integer doInBackground(String... params) {
			// Obtenemos los datos pasados desde el hilo principal.
			usuario = params[0];
			password = params[1];
			// Retornamos el resultado de la comprobación.
			return comprobarLogin(usuario, password);    		    			
		}
	
		// Valida que el usuario existe en la BD. Recidbe el nombre de usuario
		// y el password. Retorna el estado del login (ver constantes).
		private Integer comprobarLogin(String usuario, String password) {
			// Creamos un ArrayList del tipo nombre-valor con los datos suministrados,
			// para enviarlo mediante POST al PHP de validación. 
			ArrayList<NameValuePair> parametros = new ArrayList<NameValuePair>();
			parametros.add(new BasicNameValuePair("usuario", usuario));
			parametros.add(new BasicNameValuePair("password", password));
			// Obtenemos los datos de respuesta desde el servidor
			// en forma de array JSON.
			JSONArray respuesta = peticion.getDatosServidor(parametros, URL_BASE + PHP_ACCESO);
			// Si hay respuesta (no es nula y no es un array JSON vacío).
			if (respuesta!=null && respuesta.length() > 0) {
				// Extraemos el primero objeto JSON del array,
				// que en nuestro caso debe ser el único.
				try {
					JSONObject datos = respuesta.getJSONObject(0);
					// Obtenemos el dato entero llamado logstatus.
					int estado = datos.getInt("logstatus");
					// Retornamos si se ha validado o no el login.
					return estado==1?LOGIN_VALIDO:LOGIN_INVALIDO;
				} catch (JSONException e) {
					return LOGIN_ERROR;
				}		            
			}
			else {
				return LOGIN_ERROR;
			}
		}

		// Se ejecuta en el hilo principal. Recibe lo que retorna doInBackgroud
		protected void onPostExecute(Integer estado) {
			// Ocultamos el ProgressDialog.
			pdConsultando.dismiss();
			// Dependiendo del estado.
			switch (estado) {
			// Login válido.
			case LOGIN_VALIDO:
				// Envío un intent para que se muestre la actividad de saludo.
				Intent i=new Intent(Login.this, HiScreen.class);
				i.putExtra("txtUsuario",usuario);
				startActivity(i); 
				break;
			// Login no válido.
			case LOGIN_INVALIDO:
				// Informo al usuario.
				Toast.makeText(Login.this, R.string.login_no_valido, Toast.LENGTH_LONG).show();
				break;
			// Error en la conexión o en el procesamiento JSON.
			case LOGIN_ERROR:
				Toast.makeText(Login.this, R.string.error_comprobacion, Toast.LENGTH_LONG).show();
				break;
			}
		}
	
	}

}




//-----------------------------------------------------------------------




