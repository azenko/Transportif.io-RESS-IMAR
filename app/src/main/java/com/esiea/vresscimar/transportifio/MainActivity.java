package com.esiea.vresscimar.transportifio;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    private Stations theStationsBlocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        theStationsBlocks = new Stations(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, theStationsBlocks.getStationsLabel());
        AutoCompleteTextView stationList = (AutoCompleteTextView)
                findViewById(R.id.station);
        stationList.setAdapter(adapter);

        Button button = (Button) findViewById(R.id.btnChooseStation);
        button.setOnClickListener(this);

        Button button2 = (Button) findViewById(R.id.btnSeeUsOnMaps);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://www.google.fr/maps/place/ESIEA+Paris-Ivry-Laval+-+Ecole+d'ing%C3%A9nieurs+du+num%C3%A9rique/@48.814235,2.377833,15z/data=!4m5!3m4!1s0x0:0x10ff8b28c767ad3f!8m2!3d48.814235!4d2.377833?sa=X&ved=0ahUKEwjIuIiqnvrQAhWHrxoKHQrXDd8Q_BIIbTAK"));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        AutoCompleteTextView station = (AutoCompleteTextView) findViewById(R.id.station);
        boolean isValidStation = theStationsBlocks.thereIsAStationCalled(station.getText().toString());
        Button button = (Button)v;
        if(isValidStation) {
            List<String> stationID = theStationsBlocks.getIdFromLabel(station.getText().toString());
            Intent intent = new Intent(MainActivity.this, Horaire.class);
            String[] stsID = new String[stationID.size()];
            stationID.toArray(stsID);
            intent.putExtra("STATION_ID", stsID);
            startActivity(intent);
        }
        else {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle(getString(R.string.nostation_alert_title));
            adb.setMessage(getString(R.string.nostation_alert_text)).setCancelable(false).setNeutralButton(getString(R.string.nostation_alert_button), new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog ad = adb.create();
            ad.show();
        }
    }
}
