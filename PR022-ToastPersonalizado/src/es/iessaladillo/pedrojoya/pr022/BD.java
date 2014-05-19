package es.iessaladillo.pedrojoya.pr022;

import java.util.ArrayList;
import java.util.Random;

// Clase que simula la base de datos.
public class BD {

    // Colecciones (simula las tablas).
    private static ArrayList<Termino> mTerminos;
    private static ArrayList<Respuesta> mRespuestas;

    // Auxiliares.
    private static Random aleatorioTerminos;
    private static Random aleatorioRespuestas;

    // Código estático que se genera la primera vez que se usa la clase.
    static {
        // Se obtienen los generadores de números aleatorios.
        aleatorioTerminos = new Random(System.nanoTime());
        aleatorioRespuestas = new Random(System.nanoTime());
        // Se crean las "tablas".
        mRespuestas = new ArrayList<Respuesta>();
        mTerminos = new ArrayList<Termino>();
        // Se insertan los datos iniciales en las tablas.
        insertarDatosIniciales();
    }

    // Inserta los "registros" en las "tablas" correspondientes.
    private static void insertarDatosIniciales() {
        insertarTerminoConRespuestaCorrecta("apple", "manzana",
                R.drawable.manzana);
        insertarTerminoConRespuestaCorrecta("strawberry", "fresa",
                R.drawable.fresa);
        insertarTerminoConRespuestaCorrecta("banana", "plátano",
                R.drawable.banana);
        insertarTerminoConRespuestaCorrecta("pear", "pera", R.drawable.pear);
        insertarTerminoConRespuestaCorrecta("avocado", "aguacate",
                R.drawable.aguacate);
        insertarTerminoConRespuestaCorrecta("blackberry", "mora",
                R.drawable.mora);
        insertarTerminoConRespuestaCorrecta("lemon", "limón", R.drawable.lemon);
        insertarTerminoConRespuestaCorrecta("cherry", "cereza",
                R.drawable.cereza);
        insertarTerminoConRespuestaCorrecta("grape", "uva", R.drawable.uva);
        insertarTerminoConRespuestaCorrecta("orange", "naranja",
                R.drawable.orange);
        insertarTerminoConRespuestaCorrecta("melon", "melón", R.drawable.melon);
        insertarTerminoConRespuestaCorrecta("blueberry", "arándano",
                R.drawable.arandano);
        insertarTerminoConRespuestaCorrecta("peach", "melocotón",
                R.drawable.melocoton);
        insertarTerminoConRespuestaCorrecta("water melon", "sandía",
                R.drawable.sandia);
        insertarTerminoConRespuestaCorrecta("onion", "cebolla",
                R.drawable.cebolla);
        insertarTerminoConRespuestaCorrecta("potato", "patata",
                R.drawable.patata);
        insertarTerminoConRespuestaCorrecta("tomato", "tomate",
                R.drawable.tomate);
        insertarTerminoConRespuestaCorrecta("garlic", "ajo", R.drawable.ajo);
        insertarTerminoConRespuestaCorrecta("pumpkin", "calabaza",
                R.drawable.calabaza);
        insertarTerminoConRespuestaCorrecta("pepper", "pimiento",
                R.drawable.pimiento);
        insertarTerminoConRespuestaCorrecta("aubergine / eggplant",
                "berenjena", R.drawable.berenjena);
        insertarTerminoConRespuestaCorrecta("broccoli", "brécol",
                R.drawable.brocoli);
        insertarTerminoConRespuestaCorrecta("beer", "cerveza",
                R.drawable.cerveza);
        insertarTerminoConRespuestaCorrecta("cheese", "queso", R.drawable.queso);
        insertarTerminoConRespuestaCorrecta("bread", "pan", R.drawable.pan);
    }

    // Inserta un término y su respuesta correcta.
    private static void insertarTerminoConRespuestaCorrecta(String pregunta,
            String textoRespuesta, int resIdImagenRespuesta) {
        // Se crea la respuesta y se "inserta en la tabla" correspondiente.
        Respuesta respuesta = new Respuesta(textoRespuesta,
                resIdImagenRespuesta);
        mRespuestas.add(respuesta);
        // Se crea el término y se "inserta en la tabla" correspondiente.
        mTerminos.add(new Termino(pregunta, respuesta));
    }

    // Retorna un ejercicio creado con un término aleatorio y con respuestas
    // aleatorias (que incluyen obviamente la correcta).
    public static Ejercicio getRandomEjercicio() {
        return createEjercicio(mTerminos.get(aleatorioTerminos
                .nextInt(mTerminos.size())));
    }

    // Crea un ejercicio a partir de un término, obteniendo respuesta
    // aleatorias, incluyendo obviamente la correcta.
    private static Ejercicio createEjercicio(Termino termino) {
        // Se crea un array para las respuestas del ejercicio al que se le añade
        // inicialmente la respuesta correcta.
        ArrayList<Respuesta> respEjercicio = new ArrayList<Respuesta>();
        respEjercicio.add(termino.getRespuesta());
        // Se añaden otras respuestas aleatoriamente hasta llegar al número de
        // respuestas por ejercicio.
        Respuesta respuesta;
        while (respEjercicio.size() < Ejercicio.NUM_RESPUESTAS) {
            respuesta = mRespuestas.get(aleatorioRespuestas.nextInt(mRespuestas
                    .size()));
            // Si no se ha añadido previamente se añade.
            if (respEjercicio.indexOf(respuesta) < 0) {
                respEjercicio.add(respuesta);
            }
        }
        // Se crea el ejercicio (la respuesta correcta siempre tiene el índice 0
        // porque la hemos añadido la primera).
        Ejercicio ejercicio = new Ejercicio(termino.getPregunta(),
                respEjercicio, 0);
        // Se barajan las respuestas para que la correcta no sea siempre la 0.
        ejercicio.shuffleRespuestas();
        // Se retorna el ejercicio creado.
        return ejercicio;
    }

}
