package es.iessaladillo.pedrojoya.pr081;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import es.iessaladillo.pedrojoya.pr081.modelos.Clima;

public class MainActivity extends Activity {

    private TextView lblDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getVistas();
        // Se crea la cola de peticiones de la aplicación.
        RequestQueue colaPeticiones = Volley.newRequestQueue(this);
        // Se crea el listener que responderá cuando la petición esté
        // disponible.
        Response.Listener<JSONObject> listenerRespuesta = new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject respuesta) {
                // Se parsea la respuesta.
                Gson gson = new Gson();
                // String climaJSON =
                // "{\"coord\":{\"lon\":-5.45,\"lat\":36.13},\"sys\":{\"message\":0.1193,\"country\":\"ES\",\"sunrise\":1387783806,\"sunset\":1387818740},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"base\":\"gdps stations\",\"main\":{\"temp\":288.15,\"pressure\":1026,\"humidity\":77,\"temp_min\":288.15,\"temp_max\":288.15},\"wind\":{\"speed\":3.1,\"deg\":110},\"clouds\":{\"all\":20},\"dt\":1387813800,\"id\":2522013,\"name\":\"Algeciras\",\"cod\":200}";
                Clima clima = gson.fromJson(respuesta.toString(), Clima.class);
                StringBuilder builder = new StringBuilder(clima.getCity()
                        .getName());
                builder.append("\n" + clima.getList().get(0).getTemp().getDay()
                        + "ºC");
                builder.append("\n"
                        + clima.getList().get(0).getWeather().get(0)
                                .getDescription());
                Date date = new Date(clima.getList().get(0).getDt().longValue());
                Calendar cal = new GregorianCalendar();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setCalendar(cal);
                cal.setTime(date);
                builder.append("\n" + sdf.format(date));
                lblDescripcion.setText(builder.toString());
            }
        };
        // Se crea la petición.
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Algeciras,ES&mode=json&units=metric&cnt=7";
        JsonObjectRequest peticion = new JsonObjectRequest(Method.GET, url,
                null, listenerRespuesta, null);
        // Se añade la petición a la cola de peticiones.
        colaPeticiones.add(peticion);
    }

    private void getVistas() {
        lblDescripcion = (TextView) findViewById(R.id.lblDescripcion);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
