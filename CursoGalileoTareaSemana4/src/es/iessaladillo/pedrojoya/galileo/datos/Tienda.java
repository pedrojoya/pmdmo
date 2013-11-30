package es.iessaladillo.pedrojoya.galileo.datos;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseObject;

public class Tienda implements Parcelable {

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
        nombre = objeto.getString(BD.Tienda.NOMBRE);
        direccion = objeto.getString(BD.Tienda.DIRECCION);
        telefono = objeto.getString(BD.Tienda.TELEFONO);
        horarios = objeto.getString(BD.Tienda.HORARIOS);
        website = objeto.getString(BD.Tienda.WEBSITE);
        email = objeto.getString(BD.Tienda.EMAIL);
        favoritos = objeto.getLong(BD.Tienda.FAVORITOS);
        ubicacion = objeto.getString(BD.Tienda.UBICACION);
        urlLogo = objeto.getParseFile(BD.Tienda.ARCHIVO_LOGO).getUrl();
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
        objeto.put(BD.Tienda.UBICACION, ubicacion);
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
