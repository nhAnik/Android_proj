package com.example.nayeemhasan.smartticket;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BusListActivity extends AppCompatActivity {

    private ArrayList<Transport> transports;
    private ListView busList;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);

        transports = new ArrayList<>();
        /*transports.add(new Transport("Hanif EnterPrise",4));
        transports.add(new Transport("Nabil EnterPrise",4));
        transports.add(new Transport("Unique EnterPrise",4));
        transports.add(new Transport("Ena EnterPrise",4));
        transports.add(new Transport("Shyamoli EnterPrise",4));
        transports.add(new Transport("Hanif EnterPrise",4));*/

        busList = (ListView) findViewById(R.id.BusList);
        textView = (TextView) findViewById(R.id.textView8);

        final BusAdapter busAdapter = new BusAdapter();
        busList.setAdapter(busAdapter);

        Intent intent = getIntent();
        final String from = intent.getStringExtra("from");
        final String to = intent.getStringExtra("to");

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jor = new JsonArrayRequest(Request.Method.GET,
                "http://192.168.43.217:1234/SmartTicket/viewTransport.php?start=" + from + "&dest=" + to,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        transports.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject j = response.getJSONObject(i);
                                int id = j.getInt("id");
                                String busName = j.getString("bus_name");
                                float busRating = (float) j.getDouble("rating");
                                int fare = j.getInt("fare");
                                transports.add(new Transport(id, busName, busRating,fare));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            busAdapter.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BusListActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
                    }
                });

        /*StringRequest jor = new StringRequest("http://192.168.56.1:1234/SmartTicket/viewTransport.php?start=" + from + "&dest=" + to,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray result = jsonObject.getJSONArray("return_array");
                            transports.clear();
                            for (int i = 0; i < result.length(); i++) {

                                JSONObject j = result.getJSONObject(i);
                                int id = j.getInt("id");
                                String busName = j.getString("bus_name");
                                float busRating = (float) j.getDouble("rating");
                                transports.add(new Transport(id, busName, busRating));

                                busAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText(error.getMessage());
                    }
                });*/

        requestQueue.add(jor);


    }

    public class BusAdapter extends ArrayAdapter<Transport>{

        public BusAdapter(){
            super(getApplicationContext(), R.layout.bus_row, transports);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;

            if (v == null){
                v = getLayoutInflater().inflate(R.layout.bus_row, parent, false);
            }

            final EditText busName = (EditText) v.findViewById(R.id.BusName);
            final EditText busTime = (EditText) v.findViewById(R.id.TimeText);
            final EditText fareText = (EditText) v.findViewById(R.id.FareText);
            RatingBar busRating = (RatingBar) v.findViewById(R.id.BusRating);
            final Spinner timeSpinner = (Spinner) v.findViewById(R.id.TimeSpinner);
            ImageButton imageButton = (ImageButton) v.findViewById(R.id.imageButton);

            busName.setText(transports.get(position).getName());
            busRating.setRating(transports.get(position).getRatings());
            final int transportFare = transports.get(position).getFare();
            fareText.setText(transportFare+" \\-");

            timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    busTime.setText(timeSpinner.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BusListActivity.this, BusSeatActivity.class);
                    intent.putExtra("bus_Name",busName.getText().toString());
                    intent.putExtra("bus_Time",busTime.getText().toString());
                    intent.putExtra("bus_Fare",String.valueOf(transportFare));
                    startActivity(intent);
                }
            });
            return v;
        }
    }
}
