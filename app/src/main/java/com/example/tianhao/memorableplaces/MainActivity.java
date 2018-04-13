package com.example.tianhao.memorableplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> places = new ArrayList<String>();
    static ArrayList<LatLng> locations = new ArrayList<LatLng>();
    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.tianhao.memorableplaces", Context.MODE_PRIVATE);
        ArrayList<String> latitudes = new ArrayList<>();
        ArrayList<String> longtitudes = new ArrayList<>();

        places.clear();
        latitudes.clear();
        longtitudes.clear();
        locations.clear();

        try{
            places = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places",ObjectSerializer.serialize(new ArrayList<String>())));
            latitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lats",ObjectSerializer.serialize(new ArrayList<String>())));
            longtitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lons",ObjectSerializer.serialize(new ArrayList<String>())));
        }catch (Exception e){
            e.printStackTrace();
        }

        if(places.size() > 0 && latitudes.size() > 0 && longtitudes.size() > 0){
            if(places.size() == latitudes.size() && places.size() == longtitudes.size()){
                for (int i=0; i < latitudes.size(); i++){
                    locations.add(new LatLng(Double.parseDouble(latitudes.get(i)), Double.parseDouble(longtitudes.get(i))));
                }
            }
        }else{
            places.add("Add a new place...");
            locations.add(new LatLng(0,0));
        }



        ListView listView = findViewById(R.id.listVIew);



        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, places);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("placeNumber", position);
                startActivity(intent);
            }
        });


    }
}
