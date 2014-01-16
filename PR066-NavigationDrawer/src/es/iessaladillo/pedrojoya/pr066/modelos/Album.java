package es.iessaladillo.pedrojoya.pr066.modelos;

import android.os.Parcel;
import android.os.Parcelable;

// Clase modelo de datos de álbum
public class Album implements NavigationDrawerItem, Parcelable {

    // Propiedades.
    int fotoResId;
    String nombre;
    String anio;

    // Constructores.
    public Album(int fotoResId, String nombre, String anio) {
        this.fotoResId = fotoResId;
        this.nombre = nombre;
        this.anio = anio;
    }

    public Album() {
    }

    // Constructor para Parcelable.
    public Album(Parcel parcel) {
        // Mismo orden que al escribir el parcel.
        this.fotoResId = parcel.readInt();
        this.nombre = parcel.readString();
        this.anio = parcel.readString();
    }

    // Retorna si es ítem de cabecera de sección.
    public boolean isHeader() {
        return false;
    }

    // Getters y Setters.
    public int getFotoResId() {
        return fotoResId;
    }

    public void setFotoResId(int fotoResId) {
        this.fotoResId = fotoResId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(fotoResId);
        dest.writeString(nombre);
        dest.writeString(anio);
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {

        @Override
        public Album createFromParcel(final Parcel in) {
            // Llamo al constructor del álbum pasándole el parcel.
            return new Album(in);
        }

        @Override
        public Album[] newArray(final int size) {
            return new Album[size];
        }
    };

}
