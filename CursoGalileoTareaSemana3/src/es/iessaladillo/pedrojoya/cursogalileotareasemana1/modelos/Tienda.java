package es.iessaladillo.pedrojoya.cursogalileotareasemana1.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseObject;

public class Tienda implements Parcelable {

    // Constantes.
    public static String TABLE_NAME = "Tienda";
    public static String FLD_OBJECTID = "objectId";
    public static String FLD_NOMBRE = "nombre";
    public static String FLD_DIRECCION = "direccion";
    public static String FLD_TELEFONO = "telefono";
    public static String FLD_HORARIOS = "horarios";
    public static String FLD_WEBSITE = "website";
    public static String FLD_EMAIL = "email";
    public static String FLD_FAVORITOS = "favoritos";
    public static String FLD_UBICACION = "ubicacion";
    public static String FLD_LOGO = "logo";

    // Propieadades.
    private String objectId;
    private String nombre;
    private String direccion;
    private String telefono;
    private String horarios;
    private String website;
    private String email;
    private String urlLogo;
    long favoritos;
    String ubicacion;

    // Constructores.
    public Tienda(ParseObject objeto) {
        from(objeto);
    }

    public Tienda() {
    }

    // Getters and setters.
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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    // Escribe en las propieades los datos de un ParseObject.
    public void from(ParseObject objeto) {
        objectId = objeto.getObjectId();
        nombre = objeto.getString(FLD_NOMBRE);
        direccion = objeto.getString(FLD_DIRECCION);
        telefono = objeto.getString(FLD_TELEFONO);
        horarios = objeto.getString(FLD_HORARIOS);
        website = objeto.getString(FLD_WEBSITE);
        email = objeto.getString(FLD_EMAIL);
        favoritos = objeto.getLong(FLD_FAVORITOS);
        ubicacion = objeto.getString(FLD_UBICACION);
        urlLogo = objeto.getParseFile(FLD_LOGO).getUrl();
    }

    // Escribe en un ParseObject las propiedades.
    public void to(ParseObject objeto) {
        objeto.put(FLD_NOMBRE, nombre);
        objeto.put(FLD_DIRECCION, direccion);
        objeto.put(FLD_TELEFONO, telefono);
        objeto.put(FLD_HORARIOS, horarios);
        objeto.put(FLD_WEBSITE, website);
        objeto.put(FLD_EMAIL, email);
        objeto.put(FLD_FAVORITOS, favoritos);
        objeto.put(FLD_UBICACION, ubicacion);
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
        dest.writeString(objectId);
        dest.writeString(nombre);
        dest.writeString(direccion);
        dest.writeString(telefono);
        dest.writeString(horarios);
        dest.writeString(website);
        dest.writeString(email);
        dest.writeLong(favoritos);
        dest.writeString(ubicacion);
    }

    // Leer desde un Parcel las propiedades del objeto.
    public void readFromParcel(Parcel in) {
        objectId = in.readString();
        nombre = in.readString();
        direccion = in.readString();
        telefono = in.readString();
        horarios = in.readString();
        website = in.readString();
        email = in.readString();
        favoritos = in.readLong();
        ubicacion = in.readString();
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
