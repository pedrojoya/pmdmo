package es.iessaladillo.pedrojoya.pr035;

import java.io.File;
import java.io.FileOutputStream;
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

// Captura una foto y la recorta.
public class MainActivity extends Activity {

    // Constantes.
    private final static int RC_CAPTURAR_FOTO = 0;
    private final static int RC_RECORTAR_FOTO = 1;
    private final static String KEY_PATH = "pathFoto";
    private static final String NOMBRE_FOTO = "lafoto";

    // Variables a nivel de clase.
    private String pathFotoSinRecortar;
    private ImageView imgFoto;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgFoto = (ImageView) findViewById(R.id.imgFoto);
        // Si existe estado previo se reinicializa la variable.
        if (savedInstanceState != null) {
            pathFotoSinRecortar = savedInstanceState.getString(KEY_PATH);
        }
        // Si ya existe el archivo con la foto recortada, se muestra.
        File archivoFotoRecortada = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                NOMBRE_FOTO + ".jpg");
        if (archivoFotoRecortada.exists()) {
            imgFoto.setImageURI(Uri.fromFile(archivoFotoRecortada));
        }
    }

    // Al salvar el estado.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Se almacena el path de la foto.
        outState.putString(KEY_PATH, pathFotoSinRecortar);
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
            // Se solicita la captura de una foto.
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
            File archivoFotoSinRecortar = null;
            try {
                archivoFotoSinRecortar = crearArchivoFoto();
            } catch (IOException ex) {
                // Se ha producido un error el crear el archivo.
            }
            // Si todo ha ido bien.
            if (archivoFotoSinRecortar != null) {
                // Se almacena el path de la foto para su posterior uso.
                pathFotoSinRecortar = archivoFotoSinRecortar.getAbsolutePath();
                // Se añade como extra del intent la uri donde debe guardarse.
                i.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(archivoFotoSinRecortar));
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
        // Se crea el archivo y se retorna.
        return crearArchivoFoto(nombre);
    }

    // Crea un archivo para una foto y lo retorna.Recibe el nombre de la foto.
    private File crearArchivoFoto(String nombre) throws IOException {
        // Se obtiene el directorio en el que almacenarlo.
        File directorio = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        // Se crea un archivo con ese nombre y la extensión jpg en ese
        // directorio.
        File archivo = new File(directorio, nombre + ".jpg");
        // Se retorna el archivo creado.
        return archivo;
    }

    // Cuando se recibe la respuesta de una actividad llamada.
    protected void onActivityResult(int requestCode, int resultCode,
            Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
            case RC_CAPTURAR_FOTO:
                // Se agrega la foto a la Galería
                agregarFotoAGaleria(pathFotoSinRecortar);
                // Se recorta la foto.
                recortarImagen(pathFotoSinRecortar);
                break;
            case RC_RECORTAR_FOTO:
                // Obtengo el bitmap resultante.
                Bitmap bitmapFotoRecortada = intent.getExtras().getParcelable(
                        "data");
                // Se almacena la foto en un fichero.
                guardarBitmapEnArchivo(bitmapFotoRecortada);
                // Muestro la imagen recortada en el visor.
                imgFoto.setImageBitmap(bitmapFotoRecortada);
            }
        }
    }

    // Guarda el bitamp de la foto en un archivo.
    private void guardarBitmapEnArchivo(Bitmap foto) {
        // Se crea el archivo con el nombre indicado por la constante.
        File archivoFotoRecortada = null;
        try {
            archivoFotoRecortada = crearArchivoFoto(NOMBRE_FOTO);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (archivoFotoRecortada != null) {
            // Se guarda el bitmap en el archivo.
            try {
                FileOutputStream flujoSalida = new FileOutputStream(
                        archivoFotoRecortada);
                foto.compress(Bitmap.CompressFormat.JPEG, 100, flujoSalida);
                flujoSalida.flush();
                flujoSalida.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // Agrega a la Galería la foto indicada.
    private void agregarFotoAGaleria(String pathFoto) {
        // Se un intent implícito con la acción de
        // escaneo de un fichero multimedia.
        Intent i = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        // Se obtiene la uri del archivo a partir de su path.
        File archivoFotoSinRecortar = new File(pathFoto);
        Uri uri = Uri.fromFile(archivoFotoSinRecortar);
        // Se establece la uri con datos del intent.
        i.setData(uri);
        // Se envía un broadcast con el intent.
        this.sendBroadcast(i);
    }

    // Llama a un intent para recortar la imagen. Recibe el path de la foto.
    private void recortarImagen(String pathFoto) {
        // Se intenta recortar y si no se puede se muestra la imagen escalada.
        try {
            // Se crea un intent estándar para el recortado de imágenes.
            Intent i = new Intent("com.android.camera.action.CROP");
            i.setDataAndType(Uri.fromFile(new File(pathFoto)), "image/*");
            i.putExtra("crop", "true");
            // Ratio.
            i.putExtra("aspectX",
                    getResources().getDimensionPixelSize(R.dimen.ancho_visor));
            i.putExtra("aspectY",
                    getResources().getDimensionPixelSize(R.dimen.alto_visor));
            // Tamaño de salida.
            i.putExtra("outputX",
                    getResources().getDimensionPixelSize(R.dimen.ancho_visor));
            i.putExtra("outputY",
                    getResources().getDimensionPixelSize(R.dimen.alto_visor));
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
            if (result != null) {
                // Se crea el archivo con la imagen recortada.
                guardarBitmapEnArchivo(result);
                // Se muestra la foto en el ImageView.
                imgFoto.setImageBitmap(result);
            }
        }

    }

}