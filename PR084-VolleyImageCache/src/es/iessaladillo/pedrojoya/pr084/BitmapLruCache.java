package es.iessaladillo.pedrojoya.pr084;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

// Caché en memoria de imágenes.
public class BitmapLruCache extends LruCache<String, Bitmap> implements
        ImageCache {

    // Constructor. Recibe el tamaño máximo de caché.
    public BitmapLruCache(int maxSize) {
        super(maxSize);
    }

    // Retorna el tamaño en bytes del elemento en caché con dicha clave y valor.
    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    // Retorna la imagen correspondiente a una clave (url).
    @Override
    public Bitmap getBitmap(String key) {
        return get(key);
    }

    // Escribe en la caché una imagen y le asocia una clave (url).
    @Override
    public void putBitmap(String key, Bitmap bitmap) {
        put(key, bitmap);
    }
}