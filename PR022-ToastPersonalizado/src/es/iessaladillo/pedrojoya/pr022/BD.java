package es.iessaladillo.pedrojoya.pr022;

import java.util.ArrayList;
import java.util.Random;

public class BD {

    // Propiedades.
    private static ArrayList<Termino> terminos;
    private static ArrayList<Respuesta> respuestas;
    private static Random aleatorioEjercicios;
    private static Random aleatorioRespuestas;

    static {
        // Se crea el generador de números aleatorios.
        aleatorioEjercicios = new Random(System.nanoTime());
        aleatorioRespuestas = new Random(System.nanoTime());
        // Se crean las respuestas.
        respuestas = new ArrayList<Respuesta>();
        respuestas.add(new Respuesta("manzana", R.drawable.manzana));
        respuestas.add(new Respuesta("fresa", R.drawable.fresa));
        respuestas.add(new Respuesta("plátano", R.drawable.banana));
        respuestas.add(new Respuesta("pera", R.drawable.pear));
        respuestas.add(new Respuesta("aguacate", R.drawable.aguacate));
        respuestas.add(new Respuesta("mora", R.drawable.mora));
        respuestas.add(new Respuesta("limón", R.drawable.lemon));
        respuestas.add(new Respuesta("cereza", R.drawable.cereza));
        respuestas.add(new Respuesta("uva", R.drawable.uva));
        respuestas.add(new Respuesta("naranja", R.drawable.orange));
        respuestas.add(new Respuesta("melón", R.drawable.melon));
        // Se crean los terminos.
        terminos = new ArrayList<Termino>();
        terminos.add(new Termino("apple", respuestas.get(0)));
        terminos.add(new Termino("strawberry", respuestas.get(1)));
        terminos.add(new Termino("banana", respuestas.get(2)));
        terminos.add(new Termino("pear", respuestas.get(3)));
        terminos.add(new Termino("avocado", respuestas.get(4)));
        terminos.add(new Termino("blackberry", respuestas.get(5)));
        terminos.add(new Termino("lemon", respuestas.get(6)));
        terminos.add(new Termino("cherry", respuestas.get(7)));
        terminos.add(new Termino("grape", respuestas.get(8)));
        terminos.add(new Termino("orange", respuestas.get(9)));
        terminos.add(new Termino("melon", respuestas.get(10)));
    }

    public static Ejercicio getRandomEjercicio() {
        int numTermino = aleatorioEjercicios.nextInt(terminos.size());
        Termino termino = terminos.get(numTermino);
        return createEjercicio(termino);
    }

    private static Ejercicio createEjercicio(Termino termino) {
        ArrayList<Respuesta> respEjercicio = new ArrayList<Respuesta>();
        // Se introduce la respuesta correcta.
        respEjercicio.add(termino.getRespuesta());
        Respuesta respuesta;
        while (respEjercicio.size() < Ejercicio.NUM_RESPUESTAS) {
            respuesta = respuestas.get(aleatorioRespuestas.nextInt(respuestas
                    .size()));
            int pos = respEjercicio.indexOf(respuesta);
            if (pos < 0) {
                respEjercicio.add(respuesta);
            }
        }
        // Se crea el ejercicio.
        Ejercicio ejercicio = new Ejercicio(termino.getPregunta(),
                respEjercicio, 0);
        // Se barajan las respuestas.
        ejercicio.shuffleRespuestas();
        return ejercicio;
    }

}
