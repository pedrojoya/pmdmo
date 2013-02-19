package es.iessaladillo.pedrojoya.pr041;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

// OJO NO OLVIDAR AÑADIR AL MANIFIESTO EL PERMISO DE INTERNET.
public class MainActivity extends Activity {

	// Constantes.
	public static final String TAG_TITULO = "title";
	public static final String TAG_AUTOR = "creator";
	public static final String TAG_EDITORIAL = "publisher";
	public static final String TAG_ANIO = "date";

	// Variables miembro.
	private EditText txtTermino;
	private ListView lstLibros;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getVistas();
	}

	// Obtiene las referencias a las vistas y las inicializa.
	private void getVistas() {
		txtTermino = (EditText) findViewById(R.id.txtTermino);
		lstLibros = (ListView) findViewById(R.id.lstLibros);
	}

	public void btnBuscarOnClick(View view) {
		String termino = txtTermino.getText().toString();
		if (!termino.equals("")) {
			// Creo una tarea de búsqueda.
			BusquedaLibros buscar = new BusquedaLibros(this);
			buscar.execute(termino);
		}
	}

	private class BusquedaLibros extends
			AsyncTask<String, String, ArrayList<HashMap<String, String>>> {

		Context contexto;
		ProgressDialog pd;
		Exception error = null;

		public BusquedaLibros(Context contexto) {
			super();
			this.contexto = contexto;
		}

		@Override
		protected void onPreExecute() {
			// Creo un diálogo de progreso.
			pd = ProgressDialog.show(contexto, "Carga de datos",
					"Iniciando...", true, false);
			super.onPreExecute();
		}

		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(
				String... arg0) {

			// Variables.
			ArrayList<HashMap<String, String>> listaLibros = new ArrayList<HashMap<String, String>>();
			String termino = arg0[0];

			// Realizo la petición GET por HTTP obteniendo como resultado un
			// flujo de datos con la respuesta.
			try {
				publishProgress("Consultando libros...");
				// Construyo la URL de consulta.
				String sURL = "http://books.google.com/books/feeds/volumes?q=\""
						+ URLEncoder.encode(termino, "UTF-8") + "\"";
				URL url = new URL(sURL);
				// Creo un objeto lector de XML mediante SAX.
				XMLReader lectorXML = SAXParserFactory.newInstance()
						.newSAXParser().getXMLReader();
				// Creo un objeto que gestionará las etiquetas XML para obtener
				// los datos y se lo asigno al lector XML.
				ExtractorDatos extractorDatos = new ExtractorDatos(listaLibros);
				lectorXML.setContentHandler(extractorDatos);
				InputStream flujo = url.openStream();
				lectorXML.parse(new InputSource(flujo));
			} catch (Exception e) {
				error = e;
				return null;
			}
			// Retorno la lista de libros.
			return listaLibros;
		}

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
			// Cierro el diálogo de progreso.
			pd.dismiss();
			if (error == null) {
				// Creo el adaptador para la lista.
				ListAdapter adaptador = new SimpleAdapter(contexto, result,
						R.layout.fila, new String[] { MainActivity.TAG_TITULO,
								MainActivity.TAG_AUTOR,
								MainActivity.TAG_EDITORIAL,
								MainActivity.TAG_ANIO }, new int[] {
								R.id.lblTitulo, R.id.lblAutor,
								R.id.lblEditorial, R.id.lblAnio });
				lstLibros.setAdapter(adaptador);
			} else {
				Toast.makeText(contexto, error.getMessage(), Toast.LENGTH_LONG)
						.show();
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// Escribo el mensaje de progreso en el diálogo.
			pd.setMessage(values[0]);
			super.onProgressUpdate(values);
		}
	}

	public class ExtractorDatos extends DefaultHandler {

		private ArrayList<HashMap<String, String>> listaLibros;
		private StringBuilder cadena = new StringBuilder();
		HashMap<String, String> libro;

		public ExtractorDatos(ArrayList<HashMap<String, String>> listaLibros) {
			super();
			this.listaLibros = listaLibros;
		}

		@Override
		public void startElement(String uri, String nombreLocal,
				String nombreCualif, Attributes atributos) throws SAXException {
			// Reinicializo la cadena.
			cadena.setLength(0);
			// Si la etiqueta corresponde a un nuevo libro.
			if (nombreLocal.equals("entry")) {
				libro = new HashMap<String, String>();
			}
		}

		@Override
		public void characters(char ch[], int comienzo, int lon) {
			cadena.append(ch, comienzo, lon);
		}

		@Override
		public void endElement(String uri, String nombreLocal,
				String nombreCualif) throws SAXException {
			if (nombreCualif.equals("dc:title")) {
				libro.put(TAG_TITULO, cadena.toString());
			} else if (nombreCualif.equals("dc:creator")) {
				libro.put(TAG_AUTOR, cadena.toString());
			} else if (nombreCualif.equals("dc:publisher")) {
				libro.put(TAG_EDITORIAL, cadena.toString());
			} else if (nombreCualif.equals("dc:date")) {
				libro.put(TAG_ANIO, cadena.toString());
			} else if (nombreLocal.equals("entry")) {
				listaLibros.add(libro);
			}
		}
	}
}
