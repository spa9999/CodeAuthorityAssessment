package com.in.praneethambati.codeauthoritytask.GetCityName;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.in.praneethambati.codeauthoritytask.Landing_Activity.LandingActivity;
import com.in.praneethambati.codeauthoritytask.R;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

public class GetCityNameActivity extends AppCompatActivity {

    Button getCityNameBTN;
    EditText userLatET,userLngET;
    TextView retCityNameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_city_name);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        getCityNameBTN = (Button) findViewById(R.id.getCityNameBTN);
        userLatET = (EditText) findViewById(R.id.userLatET);
        userLngET = (EditText) findViewById(R.id.userLngET);
        retCityNameTV = (TextView) findViewById(R.id.retCityNameTV);

        getCityNameBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextInputLayout lngLayout = (TextInputLayout) findViewById(R.id.lngLayout);
                String strLng = lngLayout.getEditText().getText().toString();

                final TextInputLayout latLayout = (TextInputLayout) findViewById(R.id.latLayout);
                String strLat = latLayout.getEditText().getText().toString();


                if(TextUtils.isEmpty(strLng)) {

                    lngLayout.setErrorEnabled(true);
                    lngLayout.setError("Ex: Longitude: -96.830809");
                }
                if(TextUtils.isEmpty(strLat)) {

                    latLayout.setErrorEnabled(true);
                    latLayout.setError("Ex: Latitude: 33.041347");
                 }
                 else if(TextUtils.isEmpty(strLng)&& !TextUtils.isEmpty(strLat)){
                     lngLayout.setErrorEnabled(true);
                     lngLayout.setError("Ex: Longitude: -96.830809");
                     latLayout.setErrorEnabled(false);
                 }
               else if(!TextUtils.isEmpty(strLng)&& TextUtils.isEmpty(strLat)){
                    lngLayout.setErrorEnabled(false);
                    latLayout.setError("Ex: Latitude: 33.041347");
                    latLayout.setErrorEnabled(true);
                }
                 else{
                    lngLayout.setErrorEnabled(false);
                    latLayout.setErrorEnabled(false);
                    double Lat = Double.parseDouble(userLatET.getText().toString());
                    double Lng = Double.parseDouble(userLngET.getText().toString());
                   // Snackbar.make(v, getCityNameLL(Lat,Lng) , Snackbar.LENGTH_LONG).show();
                     if((Lat <= 0 || Lat >= 0) && (Lng>=0 ||Lng<=0)){
                         retCityNameTV.setText(getCityNameLL(Lat, Lng));
                     }

                }




            }
        });
    }

    public String getCityNameLL(double latitude, double longitude){
        String currentCity = "";

        Geocoder geocoder = new Geocoder(GetCityNameActivity.this, Locale.getDefault());
        List<Address> addressList;
        try{
            addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if(addressList.size() > 0){
                currentCity = addressList.get(0).getLocality();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return currentCity;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(GetCityNameActivity.this, LandingActivity.class);
        startActivity(intent);
    }
}
