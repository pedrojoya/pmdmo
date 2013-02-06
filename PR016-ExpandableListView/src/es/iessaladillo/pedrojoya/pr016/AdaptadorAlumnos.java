package es.iessaladillo.pedrojoya.pr016;

import java.util.ArrayList;
import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

// Clase adaptador de la lista de alumnos.
class AdaptadorAlumnos extends BaseExpandableListAdapter {

	// Variables miembro.
	private Activity contexto; // Actividad que lo usa.
	private ExpandableListView lista; // Lista que usa el adaptador.
	private ArrayList<String> grupos; // Nombres de los grupos.
	private ArrayList<ArrayList<Alumno>> alumnos; // Alumnos por grupo.
	private LayoutInflater inflador; // Inflador de layouts.
	private int colorMenorDeEdad; // Color para alumno menor de edad.
	private int colorMayorDeEdad; // Color para alumno mayor de edad.

	// Clase interna privada contenedor de vistas de hijo.
	private class ContenedorVistasHijo {
		// Variables miembro.
		TextView lblNombre;
		TextView lblCurso;
	}

	// Clase interna privada contenedor de vistas de grupo.
	private class ContenedorVistasGrupo {
		TextView lblEncCiclo;
		ImageView imgIndicador;
		View separador;
		LinearLayout llEncColumnas;
	}

	// Constructor.
	public AdaptadorAlumnos(Activity contexto, ExpandableListView lista,
			ArrayList<String> grupos, ArrayList<ArrayList<Alumno>> alumnos) {
		// Hago una copia de los parámetros del constructor.
		this.contexto = contexto;
		this.lista = lista;
		this.grupos = grupos;
		this.alumnos = alumnos;
		// Obtengo un objeto inflador de layouts.
		inflador = this.contexto.getLayoutInflater();
		// Obtengo los colores de menor y mayor de edad.
		Resources recursos = this.contexto.getResources();
		colorMenorDeEdad = recursos.getColor(R.color.rojo);
		colorMayorDeEdad = recursos.getColor(R.color.negro);
	}

	// Obtiene el objeto de datos de un miembro de un grupo.
	@Override
	public Alumno getChild(int posGrupo, int posHijo) {
		// Retorno el alumno correspondiente.
		return alumnos.get(posGrupo).get(posHijo);
	}

	// Obtiene el id de un miembro de un grupo
	@Override
	public long getChildId(int posGrupo, int posHijo) {
		// No gestionamos los ids.
		return 0;
	}

	// Cuando se va a pintar un hijo de un grupo.
	@Override
	public View getChildView(int posGrupo, int posHijo, boolean isLastChild,
			View convertview, ViewGroup parent) {
		ContenedorVistasHijo contenedor;
		// Intento reciclar.
		View fila = convertview;
		if (fila == null) {
			// Inflo el layout de fila.
			fila = inflador.inflate(R.layout.fila, null);
			// Creo el contenedor de vistas para la fila.
			contenedor = new ContenedorVistasHijo();
			contenedor.lblNombre = (TextView) fila.findViewById(R.id.lblNombre);
			contenedor.lblCurso = (TextView) fila.findViewById(R.id.lblCurso);
			fila.setTag(contenedor);
		} else {
			// Obtengo el contenedor desde la prop Tag.
			contenedor = (ContenedorVistasHijo) fila.getTag();
		}
		// Establezco los valores en las vistas.
		Alumno alumno = alumnos.get(posGrupo).get(posHijo);
		contenedor.lblNombre.setText(alumno.getNombre());
		contenedor.lblCurso.setText(alumno.getCurso());
		if (alumno.getEdad() < 18) {
			contenedor.lblNombre.setTextColor(colorMenorDeEdad);
		} else {
			contenedor.lblNombre.setTextColor(colorMayorDeEdad);
		}
		// Retorno la vista-fila.
		return fila;
	}

	// Obtiene cuántos hijos tiene un grupo.
	@Override
	public int getChildrenCount(int posGrupo) {
		// Retorno el número de alumnos de un grupo.
		return alumnos.get(posGrupo).size();
	}

	// Obtiene un ArrayList con todos los hijos de un grupo.
	@Override
	public ArrayList<Alumno> getGroup(int posGrupo) {
		// Retorno el ArrayList de alumnos de un grupo.
		return alumnos.get(posGrupo);
	}

	// Obtiene el número de grupos.
	@Override
	public int getGroupCount() {
		// Retorno el número de grupos.
		return alumnos.size();
	}

	// Obtiene el id de un grupo.
	@Override
	public long getGroupId(int posGrupo) {
		// No gestionamos los ids.
		return 0;
	}

	// Cuando se va a pintar el encabezado de un grupo.
	@Override
	public View getGroupView(int posGrupo, boolean isExpanded,
			View convertview, ViewGroup parent) {
		ContenedorVistasGrupo contenedor;
		// Intento reciclar.
		View fila = convertview;
		if (fila == null) {
			// Inflo el layout de la fila.
			fila = inflador.inflate(R.layout.grupo, null);
			// Creo el contenedor de vistas para la fila.
			contenedor = new ContenedorVistasGrupo();
			contenedor.lblEncCiclo = (TextView) fila
					.findViewById(R.id.lblEncCiclo);
			contenedor.imgIndicador = (ImageView) fila
					.findViewById(R.id.imgIndicador);
			contenedor.separador = (View) fila.findViewById(R.id.separador);
			contenedor.llEncColumnas = (LinearLayout) fila
					.findViewById(R.id.llEncColumnas);
			fila.setTag(contenedor);
		} else {
			// Obtengo el contenedor desde la prop Tag de la fila.
			contenedor = (ContenedorVistasGrupo) fila.getTag();
		}
		// Establezco los valores en las vistas.
		contenedor.lblEncCiclo.setText(grupos.get(posGrupo));
		// Si el grupo no tiene hijos oculto el icono de despliegue y la
		// cabecera de columnas.
		if (getChildrenCount(posGrupo) == 0) {
			contenedor.imgIndicador.setVisibility(View.INVISIBLE);
			contenedor.separador.setVisibility(View.GONE);
			contenedor.llEncColumnas.setVisibility(View.GONE);
		} else {
			// Hago visible el indicador de expansión.
			contenedor.imgIndicador.setVisibility(View.VISIBLE);
			// Si el grupo está expandido muestro el icono de ya expandido
			// y la cabecera de columnas.
			if (isExpanded) {
				contenedor.imgIndicador
						.setImageResource(R.drawable.expander_ic_maximized);
				contenedor.separador.setVisibility(View.VISIBLE);
				contenedor.llEncColumnas.setVisibility(View.VISIBLE);
			} else {
				// Si el grupo no está expandido muestro el icono de expandir
				// y oculto la cabecera de columnas.
				contenedor.imgIndicador
						.setImageResource(R.drawable.expander_ic_minimized);
				contenedor.separador.setVisibility(View.GONE);
				contenedor.llEncColumnas.setVisibility(View.GONE);
			}
		}
		// Retorno la vista-fila.
		return fila;
	}

	@Override
	public boolean hasStableIds() {
		// Comportamiento por defecto.
		return false;
	}

	@Override
	public boolean isChildSelectable(int posGrupo, int posHijo) {
		// Comportamiento por defecto.
		return true;
	}

	// // Si quiero hacer que mi lista siempre aparezca expandida puedo volver a
	// // a expandir el grupo cuando se trate de plegar.
	// @Override
	// public void onGroupCollapsed(int posGrupo) {
	// // Hago que el grupo se expanda.
	// lista.expandGroup(posGrupo);
	// }

}