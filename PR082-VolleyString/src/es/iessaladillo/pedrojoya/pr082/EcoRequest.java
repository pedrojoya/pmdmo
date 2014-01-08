package es.iessaladillo.pedrojoya.pr082;

import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

public class EcoRequest extends StringRequest {

    // Constantes.
    private final static String URL_ECO = "http://www.informaticasaladillo.es/echo.php";

    // Variables.
    Map<String, String> params;

    // Constructor.
    public EcoRequest(Map<String, String> params, Listener<String> listener,
            ErrorListener errorListener) {
        super(Request.Method.POST, URL_ECO, listener, errorListener);
        this.params = params;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
