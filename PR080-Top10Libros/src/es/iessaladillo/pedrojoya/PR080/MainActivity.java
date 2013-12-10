package es.iessaladillo.pedrojoya.PR080;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity {

	private View lstLibros;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Se obtienen las vistas.
		getVistas();
	}

	// Obtiene y configura las vistas.
	private void getVistas() {
		lstLibros = findViewById(R.id.lstLibros);
		// Se obtienen los datos para la lista.
		ArrayList<Libro> datos = obtenerDatos();
		// Se crea el adaptador.
		adaptador = new ArrayAdapter<Libro>(this, resource, datos);

	}

	private ArrayList<Libro> obtenerDatos() {
		// Se crea el ArrayList de libros.
		ArrayList<Libro> lista = new ArrayList<Libro>();
		lista.add(new Libro(
				R.drawable.yo_fui_a_egb,
				"Yo fui a EGB",
				"Jorge Díaz",
				"Si aprendiste los ríos y las cordilleras mientras mordisqueabas una goma Milán, si comiste empanadillas en Móstoles, si estabas entre dos tierras y no encontrabas el sitio de tu recreo, si para ti el tiempo era oro y jugabas al precio justo, seguro que fuiste a EGB.",
				17.95, "http://libros.fnac.es/a921180/Jorge-Diaz-Yo-fui-a-EGB"));
		lista.add(new Libro(
				R.drawable.el_francotirador_paciente,
				"El francotirador paciente",
				"Arturo Pérez-Reverte",
				"La ciudad es un campo de batalla. Un artista callejero lanza desafíos como si fueran bombas. El único arte posible es un ajuste de cuentas. Un encargo editorial pone a Alejandra Varela, especialista en arte urbano, tras la pista de Sniper, un reconocido artista del grafiti, promotor de acciones callejeras al límite de la legalidad —algunas de ellas con resultados fatales— del que casi nadie ha visto jamás el rostro ni conoce el paradero.",
				18.53,
				"http://libros.fnac.es/a938262/Arturo-Perez-Reverte-El-francotirador-paciente"));
		lista.add(new Libro(
				R.drawable.no_estamos_locos,
				"No estamos locos",
				"El gran Wyoming",
				"El polifacético Gran Wyoming abandona la pequeña pantalla para ofrecernos este relato lleno de humor, ironía y crítica social que nos cuenta crudamente porqué estamos atrapados en este terrible momento político e ideológico.",
				16.63,
				"http://libros.fnac.es/a917414/Gran-Wyoming-No-estamos-locos"));
		lista.add(new Libro(
				R.drawable.legado_en_los_huesos,
				"Legado en los huesos",
				"Dolores Redondo",
				"Más emoción, más tensión, más revelaciones en la esperada segunda entrega de la trilogía del Baztán. El juicio contra el padrastro de la joven Johana Márquez está a punto de comenzar.",
				17.58,
				"http://libros.fnac.es/a937953/Dolores-Redondo-Legado-en-los-huesos"));
		return lista;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
