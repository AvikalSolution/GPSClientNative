package com.mvp.mapactivity.view;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mvp.mapactivity.R;
import com.mvp.mapactivity.ChatApplication;
import com.mvp.mapactivity.utills.Constant;
import org.json.JSONException;
import org.json.JSONObject;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class MapDisplayActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Socket mSocket;
    private GoogleMap googleMap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_display);
        getSupportActionBar().hide();
        ChatApplication app = (ChatApplication) getApplication();
        mSocket = app.getSocket();
        mSocket.connect();

        initialize();
        socketEventInitiaize();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;
        drawMarker(this.googleMap,"0.0","0.0");
    }

    private void initialize(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void drawMarker(GoogleMap googleMap,String lat,String lng){
        if(lat=="0"&&lng=="0")
        Constant.snackeBarMessage(this,getString(R.string.server_issue));
        LatLng latLng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng) );
        MarkerOptions markerOptions = new MarkerOptions();
        googleMap.addMarker(markerOptions
                .position(latLng)
                .title("Country")
                //.snippet(myConfirmedBookingData.address)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_location_or)));
              googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String lat;
                    String lng;
                    try {
                        lat = data.getString("latitude");
                        lng = data.getString("longitude");
                        drawMarker(googleMap,lat,lng);
                       // Toast.makeText(getApplicationContext(),username,Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                       System.out.print("msghere"+e.getMessage());
                        return;
                    }

                }
            });
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off("new message", onNewMessage);
    }

    private void socketEventInitiaize(){
        mSocket.on("new message", onNewMessage);

    }




}
