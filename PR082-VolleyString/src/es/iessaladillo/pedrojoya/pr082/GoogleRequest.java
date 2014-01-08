package es.iessaladillo.pedrojoya.pr082;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

public class GoogleRequest extends StringRequest {

    // Constructor.
    public GoogleRequest(String nombre, Listener<String> listener,
            ErrorListener errorListener) {
        super(Request.Method.GET, "http://www.google.es/search?hl=es&q=\""
                + nombre + "\"", listener, errorListener);
        // TODO Auto-generated constructor stub
    }

    // Para establecer los elementos de la cabecera.
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
        return params;
    }

}
