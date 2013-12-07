package es.iessaladillo.pedrojoya.galileo.datos;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseObject;

public class Foto implements Parcelable, Comparable<Foto> {

    // Propiedades.
    private long id;
    private String objectId;
    String url;
    String descripcion;
    long favoritos;

    // Constructores.
    public Foto(ParseObject objeto) {
        from(objeto);
    }

    public Foto(Cursor cursor) {
        fromCursor(cursor);
    }

    public Foto() {
    }

    // Getters and setters.
    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(long favoritos) {
        this.favoritos = favoritos;
    }

    // Escribe en las propieades los datos de un ParseObject.
    public void from(ParseObject objeto) {
        objectId = objeto.getObjectId();
        url = objeto.getParseFile(BD.Foto.ARCHIVO).getUrl();
        descripcion = objeto.getString(BD.Foto.DESCRIPCION);
        favoritos = objeto.getLong(BD.Foto.FAVORITOS);
    }

    // Escribe en un ParseObject las propiedades.
    public void to(ParseObject objeto) {
        objeto.put(BD.Foto.DESCRIPCION, descripcion);
        objeto.put(BD.Foto.FAVORITOS, favoritos);
    }

    // Escribe en las propiedades los datos de un cursor.
    public void fromCursor(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex(BD.Foto._ID));
        objectId = cursor.getString(cursor.getColumnIndex(BD.Foto.OBJECTID));
        url = cursor.getString(cursor.getColumnIndex(BD.Foto.URL));
        descripcion = cursor.getString(cursor
                .getColumnIndex(BD.Foto.DESCRIPCION));
        favoritos = cursor.getLong(cursor.getColumnIndex(BD.Foto.FAVORITOS));
    }

    @Override
    public int compareTo(Foto another) {
        // Las fotos se ordenan descendientemente en función de su descripción.
        return (descripcion.compareTo(another.descripcion) * -1);
    }

    // Retorna un ContentValues con los datos.
    public ContentValues toContentValues() {
        ContentValues objeto = new ContentValues();
        objeto.put(BD.Foto.OBJECTID, objectId);
        objeto.put(BD.Foto.URL, url);
        objeto.put(BD.Foto.DESCRIPCION, descripcion);
        objeto.put(BD.Foto.FAVORITOS, favoritos);
        return objeto;
    }

    // Desde aquí para que sea Parcelable.

    // Constructor.
    protected Foto(Parcel in) {
        readFromParcel(in);
    }

    // Implementación por defecto.
    public int describeContents() {
        return 0;
    }

    // Escribir las propiedades del objeto en un Parcel de destino.
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(objectId);
        dest.writeString(url);
        dest.writeString(descripcion);
        dest.writeLong(favoritos);
    }

    // Leer desde un Parcel las propiedades del objeto.
    public void readFromParcel(Parcel in) {
        id = in.readLong();
        objectId = in.readString();
        url = in.readString();
        descripcion = in.readString();
        favoritos = in.readLong();
    }

    // Creador del objeto Parcelable.
    public static final Parcelable.Creator<Foto> CREATOR = new Parcelable.Creator<Foto>() {
        // Crea un objeto a partir de un Parcel.
        public Foto createFromParcel(Parcel in) {
            return new Foto(in);
        }

        // Crea un array de objetos del tamaño pasado como parámetro.
        public Foto[] newArray(int size) {
            return new Foto[size];
        }
    };

}
