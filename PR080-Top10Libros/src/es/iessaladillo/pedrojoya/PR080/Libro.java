package es.iessaladillo.pedrojoya.PR080;

import android.os.Parcel;
import android.os.Parcelable;

public class Libro {

	// Propiedades.
	int resIdPortada;
	String titulo;
	String autor;
	String resumen;
	double precio;
	String compra;

	public Libro(int resIdPortada, String titulo, String autor, String resumen,
			double precio, String compra) {
		this.resIdPortada = resIdPortada;
		this.titulo = titulo;
		this.autor = autor;
		this.resumen = resumen;
		this.precio = precio;
		this.compra = compra;
	}

	// Getters and setters.
	public int getResIdPortada() {
		return resIdPortada;
	}

	public void setResIdPortada(int resIdPortada) {
		this.resIdPortada = resIdPortada;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getCompra() {
		return compra;
	}

	public void setCompra(String compra) {
		this.compra = compra;
	}

	// Desde aquí para que sea Parcelable.

	// Constructor.
	protected Libro(Parcel in) {
		readFromParcel(in);
	}

	// Implementación por defecto.
	public int describeContents() {
		return 0;
	}

	// Escribir las propiedades del objeto en un Parcel de destino.
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(resIdPortada);
		dest.writeString(titulo);
		dest.writeString(autor);
		dest.writeString(resumen);
		dest.writeDouble(precio);
		dest.writeString(compra);
	}

	// Leer desde un Parcel las propiedades del objeto.
	public void readFromParcel(Parcel in) {
		resIdPortada = in.readInt();
		titulo = in.readString();
		autor = in.readString();
		resumen = in.readString();
		precio = in.readDouble();
		compra = in.readString();
	}

	// Creador del objeto Parcelable.
	public static final Parcelable.Creator<Libro> CREATOR = new Parcelable.Creator<Libro>() {
		// Crea un objeto a partir de un Parcel.
		public Libro createFromParcel(Parcel in) {
			return new Libro(in);
		}

		// Crea un array de alumnos del tamaño pasado como parámetro.
		public Libro[] newArray(int size) {
			return new Libro[size];
		}
	};

}
