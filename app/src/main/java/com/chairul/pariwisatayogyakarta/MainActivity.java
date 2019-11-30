package com.chairul.pariwisatayogyakarta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import com.chairul.pariwisatayogyakarta.Adapter.ListTourismAdapter;

import com.chairul.pariwisatayogyakarta.Model.Tourism;
import com.chairul.pariwisatayogyakarta.Model.User;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {
    CircleImageView profile_image;
    TextView username;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    String x;

    private RecyclerView recyclerView;
    private ArrayList<Tourism> list = new ArrayList<>();
    private List<Tourism> lstData;
    private ListTourismAdapter adapter;


    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    int n;
    String url = "http://erporate.com/bootcamp/jsonBootcamp.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        loadJSON();

        lstData = new ArrayList<>();

        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if(user.getImageURL().equals("default")){
                    profile_image.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(MainActivity.this).load(user.getImageURL()).into(profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


    private void loadJSON(){

        requestQueue = Volley.newRequestQueue(MainActivity.this);
        Toast.makeText(MainActivity.this, "Wait ", Toast.LENGTH_SHORT).show();

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    Toast.makeText(MainActivity.this, "Data : " + jsonArray.length(), Toast.LENGTH_SHORT).show();
                    n = jsonArray.length();
                    for (int a = 0; a < jsonArray.length(); a ++){

                        try {


                            JSONObject json = jsonArray.getJSONObject(a);
                            Tourism tourism = new Tourism();
                            tourism.setNama(json.getString("nama_pariwisata"));
                            tourism.setAlamat(json.getString("alamat_pariwisata"));
                            tourism.setDetail(json.getString("detail_pariwisata"));
                            tourism.setGambar(json.getString("gambar_pariwisata"));
                            x = json.getString("nama_pariwisata");
                            lstData.add(tourism);

                        }catch (JSONException e){
                            Toast.makeText(MainActivity.this, "Gagalx Loading", Toast.LENGTH_SHORT).show();

                        }
                        setuprecyclerview(lstData);

                    }

                   
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Gagal Loading : " + e, Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

    private void setuprecyclerview(List<Tourism> lstData) {


        ListTourismAdapter listTourismAdapter = new ListTourismAdapter(this, lstData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listTourismAdapter);

        listTourismAdapter.setOnItemClickCallback(new ListTourismAdapter.OnItemClickCallback() {

            @Override
            public void onItemClicked(Tourism data) {
                showSelectedHero(data);
            }
        });
    }

    private void showSelectedHero(Tourism data) {
        Intent intent = new Intent(MainActivity.this, TourismDetailActivity.class);
        intent.putExtra("Name", data.getNama());
        intent.putExtra("Alamat", data.getAlamat());
        intent.putExtra("Foto", String.valueOf(data.getGambar()));
        intent.putExtra("Detail", data.getDetail());
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,StartActivity.class));
                finish();
        }

        return false;
    }




}
