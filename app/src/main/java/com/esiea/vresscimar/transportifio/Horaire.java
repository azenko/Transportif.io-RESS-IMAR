package com.esiea.vresscimar.transportifio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.StrictMode;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class Horaire extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<HoraireAPI> horaire;
    private String[] StationID;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.horaires_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reload) {
            getHoraire(true);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horaire);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        if (intent != null) {
            this.StationID = intent.getStringArrayExtra("STATION_ID");
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        Stations stats = new Stations(this);
        myToolbar.setTitle(stats.getLabelFromId(this.StationID[0]));
        setSupportActionBar(myToolbar);

        IntentFilter intentFilter = new IntentFilter(HORAIRE_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new HoraireUpdate(), intentFilter);
        IntentFilter intentFilter2 = new IntentFilter(HORAIRE_DOWNLOADED);
        LocalBroadcastManager.getInstance(this).registerReceiver(new HoraireDownloaded(), intentFilter2);

        getHoraire(false);
    }

    protected void getHoraire(Boolean isReload) {
        this.horaire = new ArrayList<HoraireAPI>();
        for (String stsID : this.StationID) {
            HoraireList hl = new HoraireList(this, stsID ,isReload);
            this.horaire.addAll(hl.getHorairesList());
        }

        this.horaire = HoraireList.SortHoraire(this.horaire);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //pour adapter en grille comme une RecyclerView, avec 2 cellules par ligne
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        //puis créer un MyAdapter, lui fournir notre liste de villes.
        //cet adapter servira à remplir notre recyclerview
        recyclerView.setAdapter(new HoraireAdapter(this.horaire));
    }

    public static final String HORAIRE_UPDATE = "com.esiea.vressimar.transportifio.HORAIRE_UPDATE";
    public static final String HORAIRE_DOWNLOADED = "com.esiea.vressimar.transportifio.HORAIRE_DOWNLOADED";

    public class HoraireUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Context thecontext = getApplicationContext();
            CharSequence text = getString(R.string.reload_toast);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(thecontext, text, duration);
            toast.show();
        }
    }

    public class HoraireDownloaded extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Context thecontext = getApplicationContext();
            CharSequence text = getString(R.string.download_toast);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(thecontext, text, duration);
            toast.show();
        }
    }
}
