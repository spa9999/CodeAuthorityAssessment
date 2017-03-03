package com.in.praneethambati.codeauthoritytask.GetImageFromURL;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.in.praneethambati.codeauthoritytask.GetCityName.GetCityNameActivity;
import com.in.praneethambati.codeauthoritytask.Landing_Activity.LandingActivity;
import com.in.praneethambati.codeauthoritytask.R;
import com.squareup.picasso.Picasso;

public class GetImageFromUrlActivity extends AppCompatActivity {

    Button getImageBTN;
    EditText URL_ET;
    ImageView imgIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_image_from_url);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        getImageBTN = (Button) findViewById(R.id.getImageBTN);
        URL_ET = (EditText) findViewById(R.id.URL_ET);
        imgIV = (ImageView) findViewById(R.id.imgIV);

        getImageBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                Context context = imgIV.getContext();
                String Avatar = URL_ET.getText().toString();
                Picasso.with(context)
                        .load(Avatar)
                        .centerCrop()
                        .resize(200,200)
                        .into(imgIV);
            }
        });
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
        Intent intent = new Intent(GetImageFromUrlActivity.this, LandingActivity.class);
        startActivity(intent);
    }
}
