package es.iessaladillo.pedrojoya.pr040;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends Activity implements
        CargaAlumnosAsyncTask.Callbacks {

    // Constantes.
    private static final String URL_DATOS = "http://www.json-generator.com/j/bOZlHhPerm";

    // Vistas.
    private ListView lstAlumnos;

    // Variables.
    private CargaAlumnosAsyncTask tarea = null;
    private MenuItem mnuActualizar;
    private AlumnosAdapter adaptador;

    // Al crearse la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstAlumnos = (ListView) this.findViewById(R.id.lstAlumnos);
        // Si hay conexión a Internet.
        if (isConnectionAvailable()) {
            // Se lanza la tarea para obtener los datos de los alumnos.
            tarea = new CargaAlumnosAsyncTask(this);
            tarea.execute(URL_DATOS);
        } else {
            mostrarToast(getString(R.string.no_hay_conexion_a_internet));
        }
    }

    // Al ser pausada la actividad.
    @Override
    protected void onPause() {
        super.onPause();
        // Se cancela la tarea secundaria.
        if (tarea != null) {
            tarea.cancel(true);
            tarea = null;
        }
    }

    // Al crearse el menú de opciones.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        mnuActualizar = menu.findItem(R.id.mnuActualizar);
        mnuActualizar.setVisible(false);
        mnuActualizar.setActionView(R.layout.actionview_progreso);
        return super.onCreateOptionsMenu(menu);
    }

    // Cuando se termina de ejecutar la tarea secundaria. Recibe la tarea y
    // la cadena JSON resultado.
    @Override
    public void onPostExecute(CargaAlumnosAsyncTask object, String result) {
        // Se oculta el círculo de progreso.
        mnuActualizar.setVisible(false);
        // Se procesa la cadena JSON y se cargan los alumnos en la lista.
        if (!TextUtils.isEmpty(result)) {
            // Se procesa la cadena JSON, obteniendo el ArrayList de alumnos.
            // List<Alumno> alumnos = procesarJSON(result);
            List<Alumno> alumnos = procesarGSON(result);
            cargarAlumnos(alumnos);
        }

    }

    // Carga los alumnos en el ListView. Recibe la lista de alumnos.
    private void cargarAlumnos(List<Alumno> alumnos) {
        // Se crea y asigna el adaptador para el ListView.
        adaptador = new AlumnosAdapter(this, alumnos);
        lstAlumnos.setAdapter(adaptador);
    }

    // Procesa la cadena JSON y retorna el ArrayList de alumnos.
    private List<Alumno> procesarJSON(String result) {
        // Se crea el ArrayList a retornar.
        List<Alumno> alumnos = new ArrayList<Alumno>();
        try {
            // Se obtiene el elemento raíz de la cadena JSON, que es un
            // JSONArray.
            JSONArray alumnosJSON = new JSONArray(result);
            // Por cada objeto JSON del array JSON
            for (int i = 0; i < alumnosJSON.length(); i++) {
                // Se obtiene el objeto JSON correspondiente al alumno.
                JSONObject alumnoJSON = alumnosJSON.getJSONObject(i);
                // Se crea un objeto alumno.
                Alumno alumno = new Alumno();
                // Se escriben las propiedades del alumno, obtenidas del objeto
                // JSON.
                alumno.setNombre(alumnoJSON.getString(Alumno.KEY_NOMBRE));
                alumno.setDireccion(alumnoJSON.getString(Alumno.KEY_DIRECCION));
                alumno.setTelefono(alumnoJSON.getString(Alumno.KEY_TELEFONO));
                alumno.setCurso(alumnoJSON.getString(Alumno.KEY_CURSO));
                // Se añade el alumno al ArrayList.
                alumnos.add(alumno);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Se retorna el ArrayList.
        return alumnos;
    }

    // Procesa la cadena JSON y retorna el ArrayList de alumnos.
    private List<Alumno> procesarGSON(String result) {
        // Se crea el objeto Gson.
        Gson gson = new Gson();
        Type tipoListaAlumnos = new TypeToken<List<Alumno>>() {
        }.getType();
        // Se procesa la cadena JSON.
        List<Alumno> alumnos = gson.fromJson(result, tipoListaAlumnos);
        // Se retorna el ArrayList.
        return alumnos;
    }

    // Retorna si hay conexión a la red o no.
    private boolean isConnectionAvailable() {
        // Se obtiene del gestor de conectividad la información de red.
        ConnectivityManager gestorConectividad = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infoRed = gestorConectividad.getActiveNetworkInfo();
        // Se retorna si hay conexión.
        return (infoRed != null && infoRed.isConnected());
    }

    // Muestra un toast con duración larga.
    private void mostrarToast(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG)
                .show();
    }

}