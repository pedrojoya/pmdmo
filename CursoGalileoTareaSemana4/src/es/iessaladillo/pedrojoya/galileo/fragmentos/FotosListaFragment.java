package es.iessaladillo.pedrojoya.galileo.fragmentos;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import es.iessaladillo.pedrojoya.galileo.R;
import es.iessaladillo.pedrojoya.galileo.actividades.FotoActivity;
import es.iessaladillo.pedrojoya.galileo.adaptadores.FotosCursorAdapter;
import es.iessaladillo.pedrojoya.galileo.datos.BD;
import es.iessaladillo.pedrojoya.galileo.datos.Foto;
import es.iessaladillo.pedrojoya.galileo.dialogos.FotoDialogFragment;

// Fragmento que muestra la lista de fotos subidas a Parse.
public class FotosListaFragment extends Fragment implements OnClickListener,
		OnItemClickListener, OnRefreshListener, LoaderCallbacks<Cursor> {

	// Constantes.
	private static final int RC_HACER_FOTO = 0;
	private static final int RC_DESDE_GALERIA = 1;
	private static final int FOTOS_LOADER = 1;

	// Vistas.
	private ImageView btnHacerFoto;
	private ListView lstFotos;
	private PullToRefreshLayout ptrLayout;
	private RelativeLayout rlListaFotosVacia;

	// Variables.
	private String pathFoto;
	private String descripcionFoto;
	private ParseFile parseFileFoto;
	private ParseObject fotoParse;
	private FotosCursorAdapter adaptador;
	private LoaderManager gestor;
	private MenuItem mnuActualizar;
	private boolean cargando;

	// Retorna la vista que debe mostrar el fragmento.
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Se infla el layout correspondiente.
		View v = inflater.inflate(R.layout.fragment_fotos_lista, container,
				false);
		// Se obtienen las referencias a las vistas.
		getVistas(v);
		// Se retorna la vista.
		return v;
	}

	// Obtiene e inicializa las vistas.
	private void getVistas(View v) {
		ptrLayout = (PullToRefreshLayout) v.findViewById(R.id.ptr_layout);
		btnHacerFoto = (ImageView) v.findViewById(R.id.btnHacerFoto);
		lstFotos = (ListView) v.findViewById(R.id.lstFotos);
		rlListaFotosVacia = (RelativeLayout) v
				.findViewById(R.id.rlListaFotosVacia);
		// El propio fragmento responder� al hacer click sobre el bot�n o sobre
		// un elemento de la lista.
		btnHacerFoto.setOnClickListener(this);
		lstFotos.setOnItemClickListener(this);
	}

	// Cuando se ha creado la actividad completamente.
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// Se indica que el fragmento aportar� �tems a la ActionBar.
		setHasOptionsMenu(true);
		// Se configura el pulltorefresh.
		ActionBarPullToRefresh.from(getActivity()).allChildrenArePullable()
				.listener(this).setup(ptrLayout);
		// Se obtiene el gestor de cargadores.
		gestor = getActivity().getSupportLoaderManager();
		// Se carga de datos la lista.
		cargarLista();
		super.onActivityCreated(savedInstanceState);
	}

	// Al crear el men� de opciones.
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Se infla el men� correspondiente.
		inflater.inflate(R.menu.fragment_fotos_lista, menu);
		mnuActualizar = menu.findItem(R.id.mnuActualizar);
		super.onCreateOptionsMenu(menu, inflater);
	}

	// Al dibujar el men� de opciones.
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		// Si se est�n cargando datos, se muestra el c�rculo de
		// progreso y si no, simplemente el icono de actualizar.
		if (cargando) {
			MenuItemCompat.setActionView(mnuActualizar,
					R.layout.actionview_progreso);
		} else {
			MenuItemCompat.setActionView(mnuActualizar, null);
		}
		super.onPrepareOptionsMenu(menu);
	}

	// Al seleccionar un �tem de men�.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Dependiendo del �tem del men� pulsado.
		switch (item.getItemId()) {
		case R.id.mnuActualizar:
			// Se cargan los datos desde el backend.
			obtenerDatos();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	// Muestra el c�rculo de progreso en la ActionBar.
	private void mostrarProgreso(boolean mostrar) {
		// Se indica que se est�n cargando datos.
		cargando = mostrar;
		// Se invalida el men� de opciones para que se repinte.
		getActivity().invalidateOptionsMenu();
	}

	// Crea y asigna el adaptador para el ListView.
	private void cargarLista() {
		// Se inicializa el cargador.
		gestor.initLoader(FOTOS_LOADER, null, this);
		// Se crea un adaptador inicial con el cursor nulo.
		String[] from = new String[] { BD.Foto.DESCRIPCION, BD.Foto.URL };
		int[] to = new int[] { R.id.lblDescripcion, R.id.imgFoto };
		adaptador = new FotosCursorAdapter(this.getActivity(), null, from, to);
		// Se visualiza o oculta el relative layout de lista vac�a.
		rlListaFotosVacia
				.setVisibility((adaptador.getCount() > 0) ? View.INVISIBLE
						: View.VISIBLE);
		// Se asigna el adaptador al ListView.
		lstFotos.setAdapter(adaptador);
	}

	// Al pulsar sobre btnHacerFoto.
	@Override
	public void onClick(View arg0) {
		// Se muestra el fragmento de di�logo que permite seleccionar si
		// realizar la foto o tomar una de la galer�a.
		FotoDialogFragment frgDialogo = new FotoDialogFragment();
		frgDialogo.show(this.getActivity().getSupportFragmentManager(),
				"FotoDialogFragment");
	}

	// Opci�n hacer foto con la c�mara del dispositivo.
	public void hacerFoto() {
		// Se deshabilita el sensor de orientaci�n temporalmente para que no
		// haya problemas al retornar del intent porque la actividad se est�
		// recreando en ese momento.
		getActivity().setRequestedOrientation(
				ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		// Se crea el intent con la acci�n de captura de imagen.
		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Se crea el archivo donde se debe guardar la imagen capturada.
		File foto = crearArchivo();
		// Se almacena el camino absoluto al fichero para su posterior uso.
		pathFoto = foto.getAbsolutePath();
		// Se a�ade con extra del intent la uri del archivo donde se debe
		// guardar la imagen capturada.
		i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto));
		// Se env�a el intent impl�cito para que se muestre la c�mara, esperando
		// respuesta.
		startActivityForResult(i, RC_HACER_FOTO);
	}

	// Opci�n seleccionar una foto existente de la galer�a.
	public void desdeGaleria() {
		// Se env�a un intent impl�cito para seleccionar un imagen de la
		// galer�a, esperando respuesta.
		Intent i = new Intent(Intent.ACTION_PICK,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, RC_DESDE_GALERIA);
	}

	// Crea un archivo en almacenamiento externo en el que se guardar� la imagen
	// capturada. Retorna el archivo creado.
	public File crearArchivo() {
		// Se obtiene la carpeta de im�genes de almacenamiento externo.
		File carpeta = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		// Se obtiene la fecha y hora para la descripci�n y el timestamp para el
		// nombre del archivo.
		Date fecha = Calendar.getInstance().getTime();
		descripcionFoto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault()).format(fecha);
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(fecha);
		// Se obtiene el nombre del archivo en base al timestamp.
		String nombreArchivo = "IMG_" + timeStamp + ".jpg";
		// Se crea el archivo en la carpeta.
		File archivo = new File(carpeta, nombreArchivo);
		// Se retorna el archivo creado.
		return archivo;
	}

	// Cuando se recibe la respuesta de otra actividad.
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// Si la respuesta es correcta.
		if (resultCode == Activity.RESULT_OK) {
			// Dependiendo de la petici�n de la que se trate.
			switch (requestCode) {
			// Se ha hecho una foto.
			case RC_HACER_FOTO:
				// Se procesa la foto obtenida.
				procesarFoto();
				// Se habilita de nuevo el sensor de orientaci�n.
				getActivity().setRequestedOrientation(
						ActivityInfo.SCREEN_ORIENTATION_SENSOR);
				break;
			// Se ha seleccionado una foto de la galer�a.
			case RC_DESDE_GALERIA:
				if (data != null) {
					// La URI de la foto seleccionada es recibida en el data del
					// intent de respuesta.
					Uri uriImagen = data.getData();
					// Se obtiene del Content Provider para im�genes la columna
					// correspondiente al path de la foto.
					String[] columnas = { MediaStore.Images.Media.DATA };
					Cursor cursor = getActivity().getContentResolver().query(
							uriImagen, columnas, null, null, null);
					cursor.moveToFirst();
					int indiceColumnaPathFoto = cursor
							.getColumnIndex(columnas[0]);
					// Se guarda el path de la foto para su posterior uso.
					pathFoto = cursor.getString(indiceColumnaPathFoto);
					cursor.close();
					// Se crea la descripci�n de la foto a partir del timestamp.
					descripcionFoto = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss", Locale.getDefault())
							.format(Calendar.getInstance().getTime());
					procesarFoto();
				}
				break;
			}
		}
	}

	// Procesa una foto capturada o seleccionada.
	public void procesarFoto() {
		// Se redimensiona la foto.
		Bitmap fotoBitmap = resizeBitmap(R.dimen.ancho_foto, R.dimen.alto_foto);
		// Se env�a un broadcast para que se a�ada el archivo a la galer�a.
		Intent mediaScanIntent = new Intent(
				"android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		File f = new File(pathFoto);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		getActivity().sendBroadcast(mediaScanIntent);
		// Se obtienen los bytes de la foto escalada.
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		fotoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		byte[] bytesFoto = stream.toByteArray();
		// Se crea un nuevo archivo Parse para la foto, con los bytes obtenidos.
		parseFileFoto = new ParseFile("foto.jpg", bytesFoto);
		// Se guarda el archivo en Parse.
		parseFileFoto.saveInBackground(new SaveCallback() {

			// Cuando se ha guardado el archivo.
			@Override
			public void done(ParseException e) {
				// Se crea el objeto Foto con la descripci�n y el archivo Parse.
				fotoParse = new ParseObject(BD.Foto.TABLE_NAME);
				fotoParse.put(BD.Foto.DESCRIPCION, descripcionFoto);
				fotoParse.put(BD.Foto.ARCHIVO, parseFileFoto);
				// Se guarda el objeto Foto en Parse.
				fotoParse.saveInBackground(new SaveCallback() {

					// Cuando se ha guardado el objeto Foto.
					@Override
					public void done(ParseException e) {
						// Se inserta un nuevo registro Foto en la base de datos
						// local a trav�s del content provider.
						Foto foto = new Foto(fotoParse);
						getActivity().getContentResolver().insert(
								BD.Foto.CONTENT_URI, foto.toContentValues());
						// Se indica al cargador que debe recargar los datos.
						gestor.restartLoader(FOTOS_LOADER, null,
								FotosListaFragment.this);
					}
				});

			}
		});
	}

	// Retorna el bitmap esclada correspondiente al redimensionado de la foto a
	// la anchura y altura indicadas.
	public Bitmap resizeBitmap(int anchuraDestino, int alturaDestino) {
		// Se obtiene el ancho y alto de la imagen real.
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathFoto, bmOptions);
		int anchuraFoto = bmOptions.outWidth;
		int alturaFoto = bmOptions.outHeight;
		// Se calcula el factor de escala adecuado atendiendo a la altura y
		// anchura de destino.
		int factorEscala = 1;
		if ((anchuraDestino > 0) || (alturaDestino > 0)) {
			factorEscala = Math.min(anchuraFoto / anchuraDestino, alturaFoto
					/ alturaDestino);
		}
		// Se realiza el escala de la imagen con dicho factor de escala y se
		// retorna.
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = factorEscala;
		bmOptions.inPurgeable = true;
		// Se retorna el bitmap resultante del escalado.
		return BitmapFactory.decodeFile(pathFoto, bmOptions);
	}

	// Obtiene de Parse los objetos Foto y los almacena en la base de datos
	// local.
	private void obtenerDatos() {
		// Se muestra el c�rculo de progreso.
		mostrarProgreso(true);
		// Se obtienen las fotos.
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
				BD.Foto.TABLE_NAME);
		query.orderByDescending(BD.Foto.DESCRIPCION);
		query.findInBackground(new FindCallback<ParseObject>() {

			// Una obtenida la respuesta.
			@Override
			public void done(List<ParseObject> lista, ParseException e) {
				if (lista != null) {
					if (getActivity() != null) {
						// Se eliminan de la base de datos local los registros
						// existentes de fotos.
						getActivity().getContentResolver().delete(
								BD.Foto.CONTENT_URI, null, null);
						// Se inserta en la base de datos un registro por cada
						// objeto Foto obtenido desde Parse.
						for (ParseObject elemento : lista) {
							Foto foto = new Foto(elemento);
							getActivity().getContentResolver()
									.insert(BD.Foto.CONTENT_URI,
											foto.toContentValues());
						}
						// Se oculta el c�rculo de progreso.
						mostrarProgreso(false);
						// Se reinicia el cargador para que actualice los datos
						// mostrados.
						gestor.restartLoader(FOTOS_LOADER, null,
								FotosListaFragment.this);
						// Se indica que el PullToRefresh ha concluido.
						ptrLayout.setRefreshComplete();
					}
				}
			}
		});

	}

	// Al hacer click sobre un elemento de la lista.
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// Se obtiene del adaptador de la lista los datos del elemento
		// pulsado.
		Cursor cursor = (Cursor) lstFotos.getItemAtPosition(position);
		Foto foto = new Foto(cursor);
		// Se muestra la actividad de detalle de la foto.
		mostrarDetalleFoto(foto);

	}

	// Muestra la actividad de detalle de la foto.
	private void mostrarDetalleFoto(Foto foto) {
		// Se crea el intent para llamar a la actividad de detalle de foto, y
		// se le pasa la foto como dato extra.
		Intent i = new Intent(getActivity(), FotoActivity.class);
		i.putExtra(FotoActivity.EXTRA_FOTO, foto);
		// Se env�a el intent.
		startActivity(i);
	}

	// Cuando se crea el cargador. Retorna el cargador del cursor.
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// Se retorna el cargador del cursor. Se le pasa el contexto, la uri en
		// la que consultar los datos y las columnas a obtener.
		return new CursorLoader(getActivity(), BD.Foto.CONTENT_URI,
				BD.Foto.ALL, null, null, null);
	}

	// Cuando terminan de cargarse los datos en el cargador.
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// Se cambia el cursor del adaptador por el que tiene datos.
		if (adaptador != null) {
			adaptador.changeCursor(data);
			// Se visualiza o oculta el relative layout de lista vac�a.
			rlListaFotosVacia
					.setVisibility((adaptador.getCount() > 0) ? View.INVISIBLE
							: View.VISIBLE);
		}
	}

	// Cuando se resetea el cargador.
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Se vac�a de datos el adaptador.
		if (adaptador != null) {
			adaptador.changeCursor(null);
			// Se visualiza o oculta el relative layout de lista vac�a.
			rlListaFotosVacia
					.setVisibility((adaptador.getCount() > 0) ? View.INVISIBLE
							: View.VISIBLE);
		}
	}

	@Override
	public void onResume() {
		gestor.restartLoader(FOTOS_LOADER, null, this);
		super.onResume();
	}

	@Override
	public void onRefreshStarted(View view) {
		// Se obtienen los datos desde Parse.
		obtenerDatos();
	}

	@Override
	public void onDestroyView() {
		// Se "quitan" los menus del fragmento. OJO: si no se
		// hace se muestran repetidos al cambiar orientaci�n.
		setHasOptionsMenu(false);
		super.onDestroyView();
	}

}
