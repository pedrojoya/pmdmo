package es.iessaladillo.pedrojoya.pr084;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

public class App extends Application {

    // Constantes.
    private static final int MAX_MEMORY_CACHE_SIZE = 1000000;
    private static final int MAX_DISK_CACHE_SIZE = 25 * 1024 * 1024;
    private static final String DEFAULT_DISK_CACHE_DIR = "fotos";

    // Variables.
    private static RequestQueue colaPeticiones;
    private static ImageLoader cargadorImagenes;

    // Al iniciar la aplicación.
    @Override
    public void onCreate() {
        // Se crea la cola de peticiones de Volley.
        // Con caché en memoria.
        // colaPeticiones = Volley.newRequestQueue(this);
        // Con caché en disco.
        colaPeticiones = newRequestQueue(this);
        // Se crea el cargador de imágenes.
        cargadorImagenes = new ImageLoader(colaPeticiones, new BitmapLruCache(
                MAX_MEMORY_CACHE_SIZE));

    }

    // Retorna la cola de peticiones de Volley.
    public static RequestQueue getRequestQueue() {
        if (colaPeticiones != null) {
            return colaPeticiones;
        } else {
            throw new IllegalStateException("RequestQueue no inicializada");
        }
    }

    // Retorna el cargador de imágenes.
    public static ImageLoader getImageLoader() {
        return cargadorImagenes;
    }

    // Crea la cola de peticiones utilizando si es posible caché en disco.
    private static RequestQueue newRequestQueue(Context context) {
        // Se intenta obtener el directorio raíz de cache en soporte externo, y
        // si no es posible, se utiliza el interno.
        File raizCache = context.getExternalCacheDir();
        if (raizCache == null) {
            Log.w("Volley", "No se encuentra el directorio de caché externo. "
                    + "Se usará el específico de la aplicación");
            raizCache = context.getCacheDir();
        }
        // Se crea el subdirectorio para las fotos.
        File fotosCache = new File(raizCache, DEFAULT_DISK_CACHE_DIR);
        fotosCache.mkdirs();
        // Se crea la caché en dicho subdirectorio con el tamaño máximo
        // indicado.
        HttpStack pila = new HurlStack();
        Network red = new BasicNetwork(pila);
        DiskBasedCache diskBasedCache = new DiskBasedCache(fotosCache,
                MAX_DISK_CACHE_SIZE);
        // Se crea la cola de peticiones, se inicia y se retorna.
        RequestQueue cola = new RequestQueue(diskBasedCache, red);
        cola.start();
        return cola;
    }

}
