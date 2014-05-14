package es.iessaladillo.pedrojoya.pr022;

import java.util.ArrayList;
import java.util.Random;

public class BD {

    // Propiedades.
    private static ArrayList<Ejercicio> ejercicios;
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
        // Se crean los ejecicios.
        ejercicios = new ArrayList<Ejercicio>();
        ejercicios.add(new Ejercicio("apple", getRandomRespuestas(0), 0));
        ejercicios.add(new Ejercicio("strawberry", getRandomRespuestas(1), 0));
        ejercicios.add(new Ejercicio("banana", getRandomRespuestas(2), 0));
        ejercicios.add(new Ejercicio("pear", getRandomRespuestas(3), 0));
        ejercicios.add(new Ejercicio("avocado", getRandomRespuestas(4), 0));
        ejercicios.add(new Ejercicio("blackberry", getRandomRespuestas(5), 0));
    }

    public static Ejercicio getRandomEjercicio() {
        int numEjercicio = aleatorioEjercicios.nextInt(ejercicios.size());
        Ejercicio ejercicio = ejercicios.get(numEjercicio);
        ejercicio.shuffleRespuestas();
        return ejercicio;
    }

    private static ArrayList<Respuesta> getRandomRespuestas(int correcta) {
        ArrayList<Respuesta> respEjercicio = new ArrayList<Respuesta>();
        int numIncluidas = 0;
        int[] incluidas = new int[Ejercicio.NUM_RESPUESTAS];
        // Se introduce la correcta.
        respEjercicio.add(respuestas.get(correcta));
        incluidas[numIncluidas++] = correcta;
        while (numIncluidas < Ejercicio.NUM_RESPUESTAS) {
            int numRespuesta = aleatorioRespuestas.nextInt(respuestas.size());
            if (!yaIncluida(incluidas, numRespuesta)) {
                respEjercicio.add(respuestas.get(numRespuesta));
                incluidas[numIncluidas++] = numRespuesta;
            }
        }
        return respEjercicio;
    }

    private static boolean yaIncluida(int[] array, int valor) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == valor) {
                return true;
            }
        }
        return false;
    }
}
