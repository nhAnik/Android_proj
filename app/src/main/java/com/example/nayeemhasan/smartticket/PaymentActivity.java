package com.example.nayeemhasan.smartticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PaymentActivity extends AppCompatActivity {

    TextView busNameText, busFareText, ticketCnt, totalFareText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        busNameText = (TextView) findViewById(R.id.BusNameText);
        busFareText = (TextView) findViewById(R.id.FarePerTicket);
        ticketCnt = (TextView) findViewById(R.id.CountTicket);
        totalFareText = (TextView) findViewById(R.id.TotalText);

        Intent intent = getIntent();

        busNameText.setText(intent.getStringExtra("bus_Name"));
        busFareText.setText(intent.getStringExtra("ticket_Fare"));
        ticketCnt.setText(intent.getStringExtra("ticket_Cnt"));

        int ticket_fare = Integer.parseInt(intent.getStringExtra("ticket_Fare"));
        int ticket_cnt =  Integer.parseInt(intent.getStringExtra("ticket_Cnt"));
        totalFareText.setText(String.valueOf(ticket_fare*ticket_cnt));
    }

}
