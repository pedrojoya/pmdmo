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
import android.view.LayoutInflater;
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
import es.iessaladillo.pedrojoya.galileo.actividades.MainActivity;
import es.iessaladillo.pedrojoya.galileo.adaptadores.FotosCursorAdapter;
import es.iessaladillo.pedrojoya.galileo.datos.BD;
import es.iessaladillo.pedrojoya.galileo.datos.Foto;
import es.iessaladillo.pedrojoya.galileo.dialogos.FotoDialogFragment;

public class FotosListaFragment extends Fragment implements OnClickListener,
        OnItemClickListener, OnRefreshListener, LoaderCallbacks<Cursor> {

    // Constantes.
    private static final int RC_HACER_FOTO = 0;
    private static final int RC_DESDE_GALERIA = 1;
    private static final int FOTOS_LOADER = 1;

    // Vistas.
    private ImageView btnHacerFoto;
    private ListView lstFotos;

    // Propiedades.
    private String pathFoto;
    private String descripcionFoto;
    private ParseFile parseFileFoto;
    private ParseObject fotoParse;
    // private ParseQueryAdapter<ParseObject> adaptador;
    private FotosCursorAdapter adaptador;
    private View vRaiz;
    private PullToRefreshLayout ptrLayout;
    private LoaderManager gestor;
    private RelativeLayout rlListaFotosVacia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla el layout correspondiente.
        View v = inflater.inflate(R.layout.fragment_fotos_lista, container,
                false);
        // Se obtienen las referencias a las vistas.
        getVistas(v);
        return v;
    }

    private void getVistas(View v) {
        vRaiz = v;
        btnHacerFoto = (ImageView) v.findViewById(R.id.btnHacerFoto);
        lstFotos = (ListView) v.findViewById(R.id.lstFotos);
        rlListaFotosVacia = (RelativeLayout) v
                .findViewById(R.id.rlListaFotosVacia);
        // El propio fragmento responderá al hacer click sobre el botón o sobre
        // un elemento de la lista.
        btnHacerFoto.setOnClickListener(this);
        lstFotos.setOnItemClickListener(this);
        ptrLayout = (PullToRefreshLayout) v.findViewById(R.id.ptr_layout);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Se configura el pulltorefresh.
        ActionBarPullToRefresh.from(getActivity()).allChildrenArePullable()
                .listener(this).setup(ptrLayout);
        // Se obtiene el gestor de cargadores.
        gestor = getActivity().getSupportLoaderManager();
        // Se carga de datos la lista.
        cargarListaDesdeBD();
        // cargarLista();
        super.onActivityCreated(savedInstanceState);
    }

    private void cargarListaDesdeBD() {
        // Se inicializa el cargador.
        gestor.initLoader(FOTOS_LOADER, null, this);
        // Se crea un adaptador inicial con el cursor nulo.
        String[] from = new String[] { BD.Foto.DESCRIPCION, BD.Foto.URL };
        int[] to = new int[] { R.id.lblDescripcion, R.id.imgFoto };
        adaptador = new FotosCursorAdapter(this.getActivity(), null, from, to);
        // Se visualiza o oculta el relative layout de lista vacía.
        rlListaFotosVacia
                .setVisibility((adaptador.getCount() > 0) ? View.INVISIBLE
                        : View.VISIBLE);
        lstFotos.setAdapter(adaptador);
    }

    @Override
    public void onClick(View arg0) {

        FotoDialogFragment frgDialogo = new FotoDialogFragment();
        frgDialogo.show(this.getActivity().getSupportFragmentManager(),
                "FotoDialogFragment");
    }

    public void hacerFoto() {
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File foto = crearArchivo();
        pathFoto = foto.getAbsolutePath();
        i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto));
        startActivityForResult(i, RC_HACER_FOTO);
    }

    public void desdeGaleria() {
        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RC_DESDE_GALERIA);
    }

    public File crearArchivo() {
        // Se obtiene la carpeta de imágenes de almacenamiento externo.
        File carpeta = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        // Se obtiene la fecha y hora para la descripción y el timestamp para el
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Si la respuesta es correcta.
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
            case RC_HACER_FOTO:
                fotoRecibida();
                getActivity().setRequestedOrientation(
                        ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                break;
            case RC_DESDE_GALERIA:
                if (data != null) {
                    // La URI de la foto es recibido en el data del intent de
                    // respuesta.
                    Uri uriImagen = data.getData();
                    // Se obtiene del Content Provider para imágenes la columna
                    // correspondiente al path de la foto.
                    String[] columnas = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getActivity().getContentResolver().query(
                            uriImagen, columnas, null, null, null);
                    cursor.moveToFirst();
                    int indiceColumnaPathFoto = cursor
                            .getColumnIndex(columnas[0]);
                    pathFoto = cursor.getString(indiceColumnaPathFoto);
                    cursor.close();
                    descripcionFoto = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                            .format(Calendar.getInstance().getTime());
                    fotoRecibida();
                }
                break;
            }
        }
    }

    public void fotoRecibida() {
        // Se redimensiona la foto.
        Bitmap fotoEscalada = resizeBitmap(640, 480);
        // Se envía un broadcast para que se añada el archivo a la galería.
        Intent mediaScanIntent = new Intent(
                "android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(pathFoto);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
        // Se obtienen los bytes de la foto escalada.
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        fotoEscalada.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytesFoto = stream.toByteArray();
        // Se crea un nuevo archivo Parse para la foto, con los bytes obtenidos.
        parseFileFoto = new ParseFile("foto.jpg", bytesFoto);
        parseFileFoto.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                // Se crea el objeto Foto en Parse con el fichero enviado.
                fotoParse = new ParseObject(BD.Foto.TABLE_NAME);
                fotoParse.put(BD.Foto.DESCRIPCION, descripcionFoto);
                fotoParse.put(BD.Foto.ARCHIVO, parseFileFoto);
                fotoParse.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        // Se añade al adaptador la nueva Foto.
                        Foto foto = new Foto(fotoParse);
                        getActivity().getContentResolver().insert(
                                BD.Foto.CONTENT_URI, foto.toContentValues());
                        gestor.restartLoader(FOTOS_LOADER, null,
                                FotosListaFragment.this);
                    }
                });

            }
        });
    }

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
        return BitmapFactory.decodeFile(pathFoto, bmOptions);
    }

    private void obtenerDatos() {
        // Se muestra el círculo de progreso.
        if (getActivity() != null) {
            ((MainActivity) getActivity()).mostrarProgreso(true);
        }
        // Se obtienen las fotos.
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                BD.Foto.TABLE_NAME);
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.orderByDescending(BD.Foto.DESCRIPCION);
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> lista, ParseException e) {
                if (lista != null) {
                    getActivity().getContentResolver().delete(
                            BD.Foto.CONTENT_URI, null, null);
                    for (ParseObject elemento : lista) {
                        Foto foto = new Foto(elemento);
                        getActivity().getContentResolver().insert(
                                BD.Foto.CONTENT_URI, foto.toContentValues());
                    }
                    // Se hacen visibles las vistas.
                    if (vRaiz != null) {
                        vRaiz.setVisibility(View.VISIBLE);
                    }
                    if (getActivity() != null) {
                        ((MainActivity) getActivity()).mostrarProgreso(false);
                    }
                    // Se reinicia el cargador.
                    gestor.restartLoader(FOTOS_LOADER, null,
                            FotosListaFragment.this);
                    // Se indica que el PullToRefresh ha concluido.
                    ptrLayout.setRefreshComplete();
                }
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        // Se obtiene del adaptador de la lista los datos del elemento
        // pulsado.
        Cursor cursor = (Cursor) lstFotos.getItemAtPosition(position);
        String objectIdFoto = cursor.getString(cursor
                .getColumnIndex(BD.Foto.OBJECTID));
        // Se muestra la actividad de detalle de la foto.
        mostrarDetalleFoto(objectIdFoto);

    }

    // Muestra la actividad de detalle de la foto.
    private void mostrarDetalleFoto(String objectIdFoto) {
        // Se crea el intent para llamar a la actividad de detalle de foto, y
        // se le pasa la foto como dato extra.
        Intent i = new Intent(getActivity(), FotoActivity.class);
        i.putExtra(FotoActivity.EXTRA_FOTO, objectIdFoto);
        // Se envía el intent.
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
            // Se visualiza o oculta el relative layout de lista vacía.
            rlListaFotosVacia
                    .setVisibility((adaptador.getCount() > 0) ? View.INVISIBLE
                            : View.VISIBLE);
        }
    }

    // Cuando se resetea el cargador.
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Se vacía de datos el adaptador.
        if (adaptador != null) {
            adaptador.changeCursor(null);
            // Se visualiza o oculta el relative layout de lista vacía.
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

}
