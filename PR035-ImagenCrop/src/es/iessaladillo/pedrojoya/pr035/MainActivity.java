package es.iessaladillo.pedrojoya.pr035;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

// Captura una foto y la recorta.
public class MainActivity extends Activity {

    private final static int RC_CAPTURAR_FOTO = 0;
    private final static int RC_RECORTAR_FOTO = 1;
    private final static String KEY_PATH = "pathFoto";
    private final static String KEY_FOTO = "bitmapFoto";

    // Variables a nivel de clase.
    private String pathFoto;
    private ImageView imgFoto;
    private Bitmap foto;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgFoto = (ImageView) findViewById(R.id.imgFoto);
        // Si existe estado previo, se inicializa el path de la foto
        // y se muestra la foto escalada.
        if (savedInstanceState != null) {
            pathFoto = savedInstanceState.getString(KEY_PATH);
            foto = savedInstanceState.getParcelable(KEY_FOTO);
            if (foto != null) {
                // Muestro la imagen recortada en el visor.
                imgFoto.setImageBitmap(foto);
            }
        }
    }

    // Al salvar el estado.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Se almacena el path de la foto.
        if (!TextUtils.isEmpty(pathFoto)) {
            outState.putString(KEY_PATH, pathFoto.toString());
        }
        outState.putParcelable(KEY_FOTO, foto);
        super.onSaveInstanceState(outState);
    }

    // Al crear el menú de opciones.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Se infla la especificación de menú correspondiente.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Cuando se pulsa sobre un ítem del menú de opciones.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Dependiendo del ítem pulsado.
        switch (item.getItemId()) {
        case R.id.mnuCapturarFoto:
            solicitarCapturaFoto();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Envía un intent implícito para la captura de una foto.
    private void solicitarCapturaFoto() {
        // Se crea el intent implícito para realizar la acción.
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Si hay alguna actividad que sepa realizar la acción.
        if (i.resolveActivity(getPackageManager()) != null) {
            // Se crea el archivo para la foto.
            File foto = null;
            try {
                foto = crearArchivoFoto();
            } catch (IOException ex) {
                // Se ha producido un error el crear el archivo.
            }
            // Si todo ha ido bien.
            if (foto != null) {
                // Se añade como extra del intent la uri donde debe guardarse.
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto));
                // Se envía el intent esperando respuesta.
                startActivityForResult(i, RC_CAPTURAR_FOTO);
            }
        }
    }

    // Crea un archivo para una foto y lo retorna.
    private File crearArchivoFoto() throws IOException {
        // Se obtiene la fecha y hora actual.
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        // Se establece el nombre del archivo.
        String nombre = "JPEG_" + timestamp + "_";
        // Se obtiene el directorio en el que almacenarlo.
        File directorio = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        // Se crea un archivo con ese nombre y la extensión jpg en ese
        // directorio.
        File archivo = File.createTempFile(nombre, ".jpg", directorio);
        // Se almacena el path de la foto para su posterior uso.
        pathFoto = archivo.getAbsolutePath();
        // Se retorna el archivo creado.
        return archivo;
    }

    // Cuando se recibe la foto capturada.
    protected void onActivityResult(int requestCode, int resultCode,
            Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
            case RC_CAPTURAR_FOTO:
                // Se agrega la foto a la Galería
                agregarFotoAGaleria(pathFoto);
                // Se recorta la foto.
                recortarImagen(pathFoto);
                break;
            case RC_RECORTAR_FOTO:
                // Obtengo el bitmap resultante.
                foto = intent.getExtras().getParcelable("data");
                // Muestro la imagen recortada en el visor.
                imgFoto.setImageBitmap(foto);
            }
        }
    }

    // Agrega a la Galería la foto indicada.
    private void agregarFotoAGaleria(String pathFoto) {
        // Se un intent implícito con la acción de
        // escaneo de un fichero multimedia.
        Intent i = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        // Se obtiene la uri del archivo a partir de su path.
        File archivo = new File(pathFoto);
        Uri uri = Uri.fromFile(archivo);
        // Se establece la uri con datos del intent.
        i.setData(uri);
        // Se envía un broadcast con el intent.
        this.sendBroadcast(i);
    }

    // Llama a un intent para recortar la imagen
    private void recortarImagen(String pathFoto) {
        // Se intenta recortar y si no se puede se muestra la imagen escalada.
        try {
            // Creo un intent estándar para el recortado de imágenes.
            Intent i = new Intent("com.android.camera.action.CROP");
            // Indico la uri de la imagen y el tipo.
            i.setDataAndType(Uri.fromFile(new File(pathFoto)), "image/*");
            // Indico que se debe recortar.
            i.putExtra("crop", "true");
            // Indico el ratio ancho/alto del recuadro de recorte.
            i.putExtra("aspectX",
                    getResources().getDimensionPixelSize(R.dimen.ancho_visor));
            i.putExtra("aspectY",
                    getResources().getDimensionPixelSize(R.dimen.alto_visor));
            // Indico los píxeles de salida de la imagen recortada.
            i.putExtra("outputX",
                    getResources().getDimensionPixelSize(R.dimen.ancho_visor));
            i.putExtra("outputY",
                    getResources().getDimensionPixelSize(R.dimen.alto_visor));
            // Indico que se devuelva la imagen recortada.
            i.putExtra("return-data", true);
            // Inicio la actividad esperando el resultado.
            startActivityForResult(i, RC_RECORTAR_FOTO);
        } catch (NotFoundException e) {
            // Se escala la imagen y se muestra.
            cargarImagenEscalada(pathFoto);
        }
    }

    // Escala y muestra la imagen en el visor.
    private void cargarImagenEscalada(String pathFoto) {
        // Se utiliza una tarea asíncrona, para escalar y mostrar la foto en el
        // ImageView, que recibe el path de la foto.
        MostrarFotoAsyncTask tarea = new MostrarFotoAsyncTask(this);
        tarea.execute(pathFoto);
        // Una vez realizada la tarea, se elimina el objeto.
        tarea = null;
    }

    // Tarea asíncrona que obtiene una foto a partir de su path y la muestra en
    // un visor.
    private class MostrarFotoAsyncTask extends AsyncTask<String, Void, Bitmap> {

        // Variables.
        private Context contexto;

        // Constructor.
        public MostrarFotoAsyncTask(Context contexto) {
            this.contexto = contexto;
        }

        // Se ejecuta en un hilo trabajador.
        @Override
        protected Bitmap doInBackground(String... params) {
            // Se escala la foto, cuyo path corresponde al primer parámetro,
            // retornado el Bitmap correspondiente.
            return escalarFoto(
                    params[0],
                    getResources().getDimensionPixelSize(R.dimen.ancho_visor),
                    contexto.getResources().getDimensionPixelSize(
                            R.dimen.alto_visor));
        }

        // Escala la foto indicada, para ser mostarda en un visor determinado.
        // Retorna el bitmap correspondiente a la imagen escalada o null si
        // se ha producido un error.
        private Bitmap escalarFoto(String pathFoto, int anchoVisor,
                int altoVisor) {
            try {
                // Se obtiene el tamaño de la imagen.
                BitmapFactory.Options opciones = new BitmapFactory.Options();
                opciones.inJustDecodeBounds = true; // Solo para cálculo.
                BitmapFactory.decodeFile(pathFoto, opciones);
                int anchoFoto = opciones.outWidth;
                int altoFoto = opciones.outHeight;
                // Se obtiene el factor de escalado para la imagen.
                int factorEscalado = Math.min(anchoFoto / anchoVisor, altoFoto
                        / altoVisor);
                // Se escala la imagen con dicho factor de escalado.
                opciones.inJustDecodeBounds = false; // Se escalará.
                opciones.inSampleSize = factorEscalado;
                opciones.inPurgeable = true; // Eliminable con poca memoria.
                return BitmapFactory.decodeFile(pathFoto, opciones);
            } catch (Exception e) {
                return null;
            }
        }

        // Una vez finalizado el hilo de trabajo. Se ejecuta en el hilo
        // principal. Recibe el Bitmap de la foto escalada (o null si error).
        @Override
        protected void onPostExecute(Bitmap result) {
            // Muestro la foto en el ImageView.
            if (result != null) {
                imgFoto.setImageBitmap(result);
            }
        }

    }

}