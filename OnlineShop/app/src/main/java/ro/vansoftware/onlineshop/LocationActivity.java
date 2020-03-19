package ro.vansoftware.onlineshop;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LocationActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        findViewById(R.id.buttonRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "We need permission", Toast.LENGTH_LONG).show();
                ((TextView) findViewById(R.id.location)).setText("We need permission");
                ((TextView) findViewById(R.id.address)).setText("");
            }
        }
    }

    private boolean checkUserHasPermissions() {
        return  ! (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
    }

    private void requestUserPermissions(){
        Log.d(TAG,"Requesting permission...");
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void getCurrentLocation() throws SecurityException {
        if (!checkUserHasPermissions()) {
            requestUserPermissions();
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            ((TextView) findViewById(R.id.location)).setText(location.toString());
                            Geocoder geocoder = new Geocoder(LocationActivity.this, Locale.getDefault());


                            List<Address> addresses  = null;
                            try {
                                addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
                                String address = addresses.get(0).getAddressLine(0);

                                ((TextView) findViewById(R.id.address)).setText(address);


                            } catch (IOException e) {
                                e.printStackTrace();
                            }




                        }
                    }
                });
    }
}
