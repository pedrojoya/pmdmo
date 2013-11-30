package es.iessaladillo.pedrojoya.galileo;

import android.app.Application;
import android.os.StrictMode;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

import es.iessaladillo.pedrojoya.galileo.actividades.MainActivity;

public class Aplicacion extends Application {

    public static RequestQueue colaPeticiones;

    @Override
    public void onCreate() {
        super.onCreate();

        // Inicializamos Parse con el código de aplicación y el código de
        // cliente.
        Parse.initialize(this, "fB8fwNmsRef34Xg4k6NXxzVx3HPeLAfrkdluKnTi",
                "TNTaEvszGOoyRx0D1vaFfiM9UCurUm9Q1LcsADZV");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);

        // Si la versión es superior a la 9 habilitamos la consultas a Internet
        // en el mismo hilo de la UI (si no da error el PushService).
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // Al pulsar las notificaciones se mostrará la MainActivity.
        PushService.setDefaultPushCallback(this, MainActivity.class);
        // Se subscribe al canal de comentarios.
        PushService.subscribe(getApplicationContext(), "comentarios",
                MainActivity.class);
        // Se guarda en Parse la instalación actual de la aplicación.
        ParseInstallation.getCurrentInstallation().saveInBackground();
        // Se crea la cola de peticiones para Volley.
        colaPeticiones = Volley.newRequestQueue(getApplicationContext());
    }

}
