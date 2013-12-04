package es.iessaladillo.pedrojoya.galileo.datos;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

public class Tienda implements Parcelable {

    // Propiedades.
    private long id;
    private String objectId;
    private String nombre;
    private String direccion;
    private String telefono;
    private String horarios;
    private String website;
    private String email;
    private String urlLogo;
    private long favoritos;
    private Double latitud;
    private Double longitud;

    // Constructores.
    public Tienda(ParseObject parseObject) {
        from(parseObject);
    }

    public Tienda(Cursor cursor) {
        fromCursor(cursor);
    }

    public Tienda() {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String teléfono) {
        this.telefono = teléfono;
    }

    public String getHorarios() {
        return horarios;
    }

    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(long favoritos) {
        this.favoritos = favoritos;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    // Escribe en las propiedades los datos de un ParseObject.
    public void from(ParseObject objeto) {
        objectId = objeto.getObjectId();
        nombre = objeto.getString(BD.Tienda.NOMBRE);
        direccion = objeto.getString(BD.Tienda.DIRECCION);
        telefono = objeto.getString(BD.Tienda.TELEFONO);
        horarios = objeto.getString(BD.Tienda.HORARIOS);
        website = objeto.getString(BD.Tienda.WEBSITE);
        email = objeto.getString(BD.Tienda.EMAIL);
        favoritos = objeto.getLong(BD.Tienda.FAVORITOS);
        urlLogo = objeto.getParseFile(BD.Tienda.ARCHIVO_LOGO).getUrl();
        latitud = objeto.getParseGeoPoint(BD.Tienda.GEO).getLatitude();
        longitud = objeto.getParseGeoPoint(BD.Tienda.GEO).getLongitude();
    }

    // Escribe en las propieades los datos de un cursor.
    public void fromCursor(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex(BD.Tienda._ID));
        objectId = cursor.getString(cursor.getColumnIndex(BD.Tienda.OBJECTID));
        nombre = cursor.getString(cursor.getColumnIndex(BD.Tienda.NOMBRE));
        direccion = cursor
                .getString(cursor.getColumnIndex(BD.Tienda.DIRECCION));
        telefono = cursor.getString(cursor.getColumnIndex(BD.Tienda.TELEFONO));
        horarios = cursor.getString(cursor.getColumnIndex(BD.Tienda.HORARIOS));
        website = cursor.getString(cursor.getColumnIndex(BD.Tienda.WEBSITE));
        email = cursor.getString(cursor.getColumnIndex(BD.Tienda.EMAIL));
        favoritos = cursor.getLong(cursor.getColumnIndex(BD.Tienda.FAVORITOS));
        latitud = cursor
                .getDouble(cursor.getColumnIndex(BD.Tienda.GEO_LATITUD));
        longitud = cursor.getDouble(cursor
                .getColumnIndex(BD.Tienda.GEO_LONGITUD));
        urlLogo = cursor.getString(cursor.getColumnIndex(BD.Tienda.URL_LOGO));
    }

    // Escribe en un ParseObject las propiedades.
    public void to(ParseObject objeto) {
        objeto.put(BD.Tienda.NOMBRE, nombre);
        objeto.put(BD.Tienda.DIRECCION, direccion);
        objeto.put(BD.Tienda.TELEFONO, telefono);
        objeto.put(BD.Tienda.HORARIOS, horarios);
        objeto.put(BD.Tienda.WEBSITE, website);
        objeto.put(BD.Tienda.EMAIL, email);
        objeto.put(BD.Tienda.FAVORITOS, favoritos);
        objeto.put(BD.Tienda.GEO, new ParseGeoPoint(latitud, longitud));
    }

    // Retorna un ContentValues con los datos.
    public ContentValues toContentValues() {
        ContentValues objeto = new ContentValues();
        objeto.put(BD.Tienda.OBJECTID, objectId);
        objeto.put(BD.Tienda.NOMBRE, nombre);
        objeto.put(BD.Tienda.DIRECCION, direccion);
        objeto.put(BD.Tienda.TELEFONO, telefono);
        objeto.put(BD.Tienda.HORARIOS, horarios);
        objeto.put(BD.Tienda.WEBSITE, website);
        objeto.put(BD.Tienda.EMAIL, email);
        objeto.put(BD.Tienda.FAVORITOS, favoritos);
        objeto.put(BD.Tienda.GEO_LATITUD, latitud);
        objeto.put(BD.Tienda.GEO_LONGITUD, longitud);
        objeto.put(BD.Tienda.URL_LOGO, urlLogo);
        return objeto;
    }

    // Desde aquí para que sea Parcelable.

    // Constructor.
    protected Tienda(Parcel in) {
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
        dest.writeString(nombre);
        dest.writeString(direccion);
        dest.writeString(telefono);
        dest.writeString(horarios);
        dest.writeString(website);
        dest.writeString(email);
        dest.writeLong(favoritos);
        dest.writeDouble(latitud);
        dest.writeDouble(longitud);
    }

    // Leer desde un Parcel las propiedades del objeto.
    public void readFromParcel(Parcel in) {
        id = in.readLong();
        objectId = in.readString();
        nombre = in.readString();
        direccion = in.readString();
        telefono = in.readString();
        horarios = in.readString();
        website = in.readString();
        email = in.readString();
        favoritos = in.readLong();
        latitud = in.readDouble();
        longitud = in.readDouble();
    }

    // Creador del objeto Parcelable.
    public static final Parcelable.Creator<Tienda> CREATOR = new Parcelable.Creator<Tienda>() {
        // Crea un objeto a partir de un Parcel.
        public Tienda createFromParcel(Parcel in) {
            return new Tienda(in);
        }

        // Crea un array de objetos del tamaño pasado como parámetro.
        public Tienda[] newArray(int size) {
            return new Tienda[size];
        }
    };
}
