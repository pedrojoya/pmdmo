package es.iessaladillo.pedrojoya.galileo.fragmentos;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import es.iessaladillo.pedrojoya.galileo.actividades.TiendaActivity;
import es.iessaladillo.pedrojoya.galileo.datos.BD;
import es.iessaladillo.pedrojoya.galileo.datos.Tienda;
import es.iessaladillo.pedrojoya.galileo.dialogos.ErrorDialogFragment;

public class TiendasMapaFragment extends SupportMapFragment implements
        ConnectionCallbacks, OnConnectionFailedListener, LocationListener,
        OnInfoWindowClickListener, InfoWindowAdapter {

    public static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    public static final long INTERVALO_SOLICITUD_POSICION = 5000;
    public static final long INTERVALO_RAPIDO_SOLICITUD_POSICION = 1000;
    public static final LatLng ALGECIRAS = new LatLng(36.1066, -5.44343);

    private LocationClient clienteLocalizacion;
    private LocationRequest solicitudPosicion;
    private GoogleMap mapa;
    private Builder limitesBuilder;
    private HashMap<Marker, Tienda> marcadoresTiendas = new HashMap<Marker, Tienda>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapa = getMap();
        // Se crea un cliente de localización, siendo el propio fragmento el
        // listener cuando se establezca la conexión o si falla.
        clienteLocalizacion = new LocationClient(getActivity(), this, this);
        // Se crea una solicitud periódica de posición al cliente de
        // localización.
        solicitudPosicion = LocationRequest.create();
        solicitudPosicion.setInterval(INTERVALO_SOLICITUD_POSICION);
        solicitudPosicion.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        solicitudPosicion
                .setFastestInterval(INTERVALO_RAPIDO_SOLICITUD_POSICION);
    }

    @Override
    public void onConnected(Bundle arg0) {
        if (clienteLocalizacion.isConnected()) {
            Location localizacion = clienteLocalizacion.getLastLocation();
            if (localizacion != null) {
                actualizarPosicion(localizacion);
                limitesBuilder.include(new LatLng(localizacion.getLatitude(),
                        localizacion.getLongitude()));
                posicionarMapa();
            }
        }

    }

    @Override
    public void onDisconnected() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(getActivity(),
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                Log.e("ERROR", Log.getStackTraceString(e));
            }
        } else {
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                    connectionResult.getErrorCode(), getActivity(),
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);

            if (errorDialog != null) {
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(errorDialog);
                errorFragment.show(getActivity().getSupportFragmentManager(),
                        "dialog");
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        actualizarPosicion(location);
    }

    private void actualizarPosicion(Location location) {
        LatLng posicion = new LatLng(location.getLatitude(),
                location.getLongitude());
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, 10));
        mapa.setMyLocationEnabled(true);
    }

    @Override
    public void onStart() {
        // Conectamos el fragmento al cliente de localización.
        clienteLocalizacion.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        // Hacemos que el fragmento deje de ser listener del cliente de
        // localización.
        if (clienteLocalizacion.isConnected()) {
            clienteLocalizacion.removeLocationUpdates(this);
        }
        // Desconectamos el fragmento del cliente de localización
        clienteLocalizacion.disconnect();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!comprobarGooglePlayServices()) {
            Log.d("pedro", "No Google Play Services");
        } else {
            configurarMapa();
        }
    }

    private void configurarMapa() {
        // Se coloca en el mapa la localización actual.
        mapa.setMyLocationEnabled(true);
        // Se establezco el tipo de mapa que se mostrar.
        mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // Se configura la interfaz del mapa.
        mapa.getUiSettings().setZoomControlsEnabled(true);
        mapa.getUiSettings().setCompassEnabled(true);
        mapa.getUiSettings().setMyLocationButtonEnabled(true);
        mapa.getUiSettings().setRotateGesturesEnabled(true);
        mapa.getUiSettings().setScrollGesturesEnabled(true);
        mapa.getUiSettings().setTiltGesturesEnabled(true);
        mapa.getUiSettings().setZoomGesturesEnabled(true);
        // El propio fragmento actúa como listener cuando se pulsa en la ventana
        // de información de un marcador.
        mapa.setOnInfoWindowClickListener(this);
        // El propio fragmento actúa como adaptador para pintar las ventanas de
        // información de los marcadores.
        // mapa.setInfoWindowAdapter(this);
        // Se crean los limites en los que debe posicionarse el mapa para que se
        // muestren todas los marcadores.
        limitesBuilder = new LatLngBounds.Builder();
        marcadoresTiendas.clear();
        CursorLoader cLoader = new CursorLoader(this.getActivity(),
                BD.Tienda.CONTENT_URI, BD.Tienda.ALL, null, null, null);
        Cursor cursor = cLoader.loadInBackground();
        if (cursor != null) {
            cursor.moveToFirst();
            do {
                Tienda tienda = new Tienda(cursor);
                LatLng posicion = new LatLng(tienda.getLatitud(),
                        tienda.getLongitud());
                MarkerOptions options = new MarkerOptions().position(posicion)
                        .title(tienda.getNombre())
                        .snippet(tienda.getDireccion());
                Marker marcador = mapa.addMarker(options);
                marcadoresTiendas.put(marcador, tienda);
                // Se añade el marcador a los límites del mapa.
                limitesBuilder.include(posicion);
            } while (cursor.moveToNext());
        }
        // Cuando el layout esté disponible se mueve la cámara para que se
        // coloce en una posición que incluya
        // todos los marcadores (dejando un padding de 50px)
        final View mapView = getView();
        if (mapView.getViewTreeObserver().isAlive()) {
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(
                    new OnGlobalLayoutListener() {
                        @SuppressWarnings("deprecation")
                        @SuppressLint("NewApi")
                        // We check which build version we are using.
                        @Override
                        public void onGlobalLayout() {
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                mapView.getViewTreeObserver()
                                        .removeGlobalOnLayoutListener(this);
                            } else {
                                mapView.getViewTreeObserver()
                                        .removeOnGlobalLayoutListener(this);
                            }
                            posicionarMapa();
                        }

                    });
        }
    }

    private void posicionarMapa() {
        LatLngBounds limites = limitesBuilder.build();
        mapa.moveCamera(CameraUpdateFactory.newLatLngBounds(limites, 50));
    }

    private boolean comprobarGooglePlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getActivity());

        if (ConnectionResult.SUCCESS == resultCode) {
            return true;
        } else {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode,
                    getActivity(), 0);
            if (dialog != null) {
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(dialog);
                errorFragment.show(getActivity().getSupportFragmentManager(),
                        "errorDialog");
            }
            return false;
        }
    }

    @Override
    public void onInfoWindowClick(Marker marcador) {
        Tienda tienda = marcadoresTiendas.get(marcador);
        mostrarDetalleTienda(tienda.getObjectId());
    }

    // Muestra la actividad de detalle de la tienda.
    private void mostrarDetalleTienda(String objectIdTienda) {
        // Se crea el intent para llamar a la actividad de detalle de tienda, y
        // se le pasa la tienda como dato extra.
        Intent i = new Intent(getActivity(), TiendaActivity.class);
        i.putExtra(TiendaActivity.EXTRA_TIENDA, objectIdTienda);
        // Se envía el intent.
        startActivity(i);
    }

    @Override
    public View getInfoContents(Marker arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public View getInfoWindow(Marker arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    // @Override
    // public View onCreateView(LayoutInflater inflater, ViewGroup container,
    // Bundle savedInstanceState) {
    // // Se infla el layout correspondiente.
    // View v = inflater.inflate(R.layout.fragment_tiendas_mapa, container,
    // false);
    // // Se retorna la vista que debe mostrar el fragmento.
    // return v;
    // }

}
