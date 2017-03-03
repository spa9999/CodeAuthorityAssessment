package com.in.praneethambati.codeauthoritytask.Landing_Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.in.praneethambati.codeauthoritytask.GetCityName.GetCityNameActivity;
import com.in.praneethambati.codeauthoritytask.GetImageFromURL.GetImageFromUrlActivity;
import com.in.praneethambati.codeauthoritytask.R;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class LandingActivity extends AppCompatActivity implements View.OnClickListener{


    Button getCurrentLocationBTN, getImageLandingBTN, getCityLandingBTN;
    TextView cityNameTV, latLngTV;

    private static final int MY_PERMISSION_REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        getCurrentLocationBTN = (Button) findViewById(R.id.getLocationBTN);
        getImageLandingBTN = (Button) findViewById(R.id.getImageLandingBTN);
        getCityLandingBTN = (Button) findViewById(R.id.getCityLandingBTN);


        getCurrentLocationBTN.setOnClickListener(this);
        getImageLandingBTN.setOnClickListener(this);
        getCityLandingBTN.setOnClickListener(this);
    }

    public String getAddressDetails(double latitude, double longitude){
        String currentCity = "";
        String currentAddress = "";
        String currentPostalCode = "";
        String currentState ="";
        String result="";

        Geocoder geocoder = new Geocoder(LandingActivity.this, Locale.getDefault());
        List<Address> addressList;
        try{
            addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if(addressList.size() > 0){
                currentCity = addressList.get(0).getLocality();
                currentAddress = addressList.get(0).getAddressLine(0);
                currentPostalCode = addressList.get(0).getPostalCode();
                currentState = addressList.get(0).getAdminArea();

                result = currentAddress+"\n"+currentCity+" - "+ currentState + "\n"+currentPostalCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.getLocationBTN:

                LayoutInflater inflater = LayoutInflater.from(this);
                View dialogLayout = inflater.inflate(R.layout.location_detail, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle);

                builder.setView(dialogLayout);

                builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                });

                AlertDialog customAlertDialog = builder.create();



        if(ContextCompat.checkSelfPermission(LandingActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    if(ActivityCompat.shouldShowRequestPermissionRationale(LandingActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)) {
                        ActivityCompat.requestPermissions(LandingActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);
                    }
                    else{
                        ActivityCompat.requestPermissions(LandingActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);
                    }
                }
                else{
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    try{
                        cityNameTV = (TextView) dialogLayout.findViewById(R.id.cityNameTV);
                        latLngTV = (TextView) dialogLayout.findViewById(R.id.latLngTV);
                        latLngTV.setText("Latitude: "+location.getLatitude()+", Longitude: "+location.getLongitude());
                        cityNameTV.setText(getAddressDetails(location.getLatitude(),location.getLongitude()));
                        customAlertDialog.show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(LandingActivity.this, "Location Not Found!", Toast.LENGTH_LONG);
                    }
                }


                break;
            case R.id.getImageLandingBTN:
                Intent goToGetImg = new Intent(LandingActivity.this, GetImageFromUrlActivity.class);
                startActivity(goToGetImg);
                break;
            case R.id.getCityLandingBTN:
                            Intent goToGetCity = new Intent(LandingActivity.this, GetCityNameActivity.class);
                            startActivity(goToGetCity);
                break;
        }
    }
}
