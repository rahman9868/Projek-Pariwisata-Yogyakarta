package com.chairul.pariwisatayogyakarta;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chairul.pariwisatayogyakarta.Model.Tourism;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class TourismDetailActivity extends AppCompatActivity {
   TextView ur_loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourism_detail);

        String nama = getIntent().getExtras().getString("Name");
        String alamat = getIntent().getExtras().getString("Alamat");
        String detail = getIntent().getExtras().getString("Detail");
        String image = getIntent().getExtras().getString("Foto");

        TextView dt_nama = (TextView) findViewById(R.id.txtNamaTourism);
        TextView dt_alamat = (TextView) findViewById(R.id.txtalamat);
        TextView dt_detail = (TextView) findViewById(R.id.txtDetail);
        ur_loc = (TextView)findViewById(R.id.txtLocation);
        ImageView img_gambar = (ImageView) findViewById(R.id.imgDetail);


        dt_nama.setText(nama);
        dt_alamat.setText(alamat);
        dt_detail.setText(detail);
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher);

        Glide.with(this).load(image).apply(requestOptions).into(img_gambar);

        FusedLocationProviderClient fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this);

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this,
                new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location !=  null) {

                            ur_loc.setText("You are in : "+ location.getLatitude() + "," + location.getLongitude());
                        }

                    }
                });

    }
}
