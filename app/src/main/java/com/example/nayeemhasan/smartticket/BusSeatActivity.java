package com.example.nayeemhasan.smartticket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BusSeatActivity extends AppCompatActivity {

    TextView busNameText, busTimeText, busFareText;
    public CheckBox[][] checkBox;
    int ticketCnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_seat);

        busNameText = (TextView) findViewById(R.id.textView10);
        busTimeText = (TextView) findViewById(R.id.busTime);
        busFareText = (TextView) findViewById(R.id.busFare);
        final Intent intent = getIntent();

        busNameText.setText(intent.getStringExtra("bus_Name"));
        busTimeText.setText(intent.getStringExtra("bus_Time"));
        final String busFare = intent.getStringExtra("bus_Fare");
        busFareText.setText("Fare: " + intent.getStringExtra("bus_Fare"));

        checkBox = new CheckBox[9][5];

        for (int i=1; i<=8; i++){
            for (int j=1; j<=4; j++){
                if (i==1){
                    if (j==1) checkBox[i][j] = (CheckBox) findViewById(R.id.A1);
                    else if (j==2) checkBox[i][j] = (CheckBox) findViewById(R.id.A2);
                    else if (j==3) checkBox[i][j] = (CheckBox) findViewById(R.id.A3);
                    else if (j==4) checkBox[i][j] = (CheckBox) findViewById(R.id.A4);
                }

                else if (i==2){
                    if (j==1) checkBox[i][j] = (CheckBox) findViewById(R.id.B1);
                    else if (j==2) checkBox[i][j] = (CheckBox) findViewById(R.id.B2);
                    else if (j==3) checkBox[i][j] = (CheckBox) findViewById(R.id.B3);
                    else if (j==4) checkBox[i][j] = (CheckBox) findViewById(R.id.B4);
                }

                else if (i==3){
                    if (j==1) checkBox[i][j] = (CheckBox) findViewById(R.id.C1);
                    else if (j==2) checkBox[i][j] = (CheckBox) findViewById(R.id.C2);
                    else if (j==3) checkBox[i][j] = (CheckBox) findViewById(R.id.C3);
                    else if (j==4) checkBox[i][j] = (CheckBox) findViewById(R.id.C4);
                }

                else if (i==4){
                    if (j==1) checkBox[i][j] = (CheckBox) findViewById(R.id.D1);
                    else if (j==2) checkBox[i][j] = (CheckBox) findViewById(R.id.D2);
                    else if (j==3) checkBox[i][j] = (CheckBox) findViewById(R.id.D3);
                    else if (j==4) checkBox[i][j] = (CheckBox) findViewById(R.id.D4);
                }

                else if (i==5){
                    if (j==1) checkBox[i][j] = (CheckBox) findViewById(R.id.E1);
                    else if (j==2) checkBox[i][j] = (CheckBox) findViewById(R.id.E2);
                    else if (j==3) checkBox[i][j] = (CheckBox) findViewById(R.id.E3);
                    else if (j==4) checkBox[i][j] = (CheckBox) findViewById(R.id.E4);
                }

                else if (i==6){
                    if (j==1) checkBox[i][j] = (CheckBox) findViewById(R.id.F1);
                    else if (j==2) checkBox[i][j] = (CheckBox) findViewById(R.id.F2);
                    else if (j==3) checkBox[i][j] = (CheckBox) findViewById(R.id.F3);
                    else if (j==4) checkBox[i][j] = (CheckBox) findViewById(R.id.F4);
                }

                else if (i==7){
                    if (j==1) checkBox[i][j] = (CheckBox) findViewById(R.id.G1);
                    else if (j==2) checkBox[i][j] = (CheckBox) findViewById(R.id.G2);
                    else if (j==3) checkBox[i][j] = (CheckBox) findViewById(R.id.G3);
                    else if (j==4) checkBox[i][j] = (CheckBox) findViewById(R.id.G4);
                }

                else if (i==8){
                    if (j==1) checkBox[i][j] = (CheckBox) findViewById(R.id.H1);
                    else if (j==2) checkBox[i][j] = (CheckBox) findViewById(R.id.H2);
                    else if (j==3) checkBox[i][j] = (CheckBox) findViewById(R.id.H3);
                    else if (j==4) checkBox[i][j] = (CheckBox) findViewById(R.id.H4);
                }
            }
        }

        ticketCnt = 0;
        for (int i=1; i<=8; i++) {
            for (int j = 1; j <= 4; j++) {
                checkBox[i][j].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked == true) ticketCnt++;
                        else ticketCnt--;
                    }
                });
            }
        }

        Button seatSubmit = (Button) findViewById(R.id.SeatSubmit);

        seatSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ticketCnt >0 && ticketCnt <=4) {
                    Intent intent1 = new Intent(BusSeatActivity.this, PaymentActivity.class);
                    intent1.putExtra("bus_Name", busNameText.getText().toString());
                    intent1.putExtra("ticket_Fare", busFare);
                    intent1.putExtra("ticket_Cnt", String.valueOf(ticketCnt));
                    startActivity(intent1);
                }
                else if (ticketCnt == 0){
                    Toast.makeText(BusSeatActivity.this, "Select at least one ticket", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(BusSeatActivity.this, "Select at most 4 tickets", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
