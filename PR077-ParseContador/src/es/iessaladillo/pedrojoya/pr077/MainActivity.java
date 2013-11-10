package es.iessaladillo.pedrojoya.pr077;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;

public class MainActivity extends Activity {

    private ParseObject datosMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.initialize(this, "d2PHQCDhqFMHPJEPxaBHCM6VwNJGAOyb4ZA7bvop",
                "4udokv0y95AcXIP6DlbRhlDkmM7x4cwBNQAtskqM");
        // Seguimos la estadísticas de la aplicación.
        ParseAnalytics.trackAppOpened(getIntent());
        // Consultamos el valor del contador.
        // ParseQuery consulta = new ParseQuery("Main");
        ParseQuery<ParseObject> consulta = ParseQuery.getQuery("Main");
        consulta.whereEqualTo("id", "main");
        consulta.findInBackground(new FindCallback<ParseObject>() {
            // consulta.findInBackground(new FindCallback() {

            @Override
            public void done(List<ParseObject> lista, ParseException e) {
                if (e == null) {
                    // Si el objeto ya existe lo obtiene.
                    switch (lista.size()) {
                    case 0:
                        // Si no existe el objeto se crea.
                        datosMain = new ParseObject("Main");
                        datosMain.put("id", "main");
                        datosMain.put("contador", 0);
                        datosMain.saveInBackground();
                        break;
                    case 1:
                        // Si ya existe el objeto se obtiene.
                        datosMain = lista.get(0);
                        break;
                    default:
                        Log.d("main", "Hay más de un objeto Main");
                    }
                } else {
                    Log.d("main", "Error: " + e.getMessage());
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void btnIncrementarOnClick(View v) {
        datosMain.increment("contador");
        datosMain.saveInBackground();
        // Se envía un push.
        ParsePush push = new ParsePush();
        push.setChannel("contador");
        push.setMessage("Nuevo valor: " + datosMain.getInt("contador"));
        push.sendInBackground();
    }

}
