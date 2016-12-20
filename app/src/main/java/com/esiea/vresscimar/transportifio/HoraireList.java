package com.esiea.vresscimar.transportifio;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vress on 14/12/2016.
 */

public class HoraireList {

    private ArrayList<HoraireAPI> horaireList;
    private String StationID;
    private Context context;
    private Stations stationList;
    private Boolean isReload;

    public HoraireList(Context _context, String _StationID, Boolean _isReload) {
        this.StationID = _StationID;
        this.context = _context;
        this.stationList = new Stations(this.context);
        this.isReload = _isReload;

        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            String forgedURL = "http://api.transilien.com/gare/"+this.StationID+"/depart/";
            URL url = new URL(forgedURL);
            URLConnection uc = url.openConnection();
            String userpass = "tnhtn529" + ":" + "z9e3VWz7";
            String basicAuth = "Basic " + new String(Base64.encode(userpass.getBytes(), 0));
            uc.setRequestProperty ("Authorization", basicAuth);

            InputStream in_s =  uc.getInputStream();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            this.horaireList = parseXML(parser);
            if(this.isReload)
                LocalBroadcastManager.getInstance(_context).sendBroadcast(new Intent(Horaire.HORAIRE_UPDATE));
            else
                LocalBroadcastManager.getInstance(_context).sendBroadcast(new Intent(Horaire.HORAIRE_DOWNLOADED));

        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<HoraireAPI> getHorairesList() {
        return this.horaireList;
    }

    private ArrayList<HoraireAPI> parseXML(XmlPullParser parser) throws XmlPullParserException,IOException {
        ArrayList<HoraireAPI> horaireListe = null;
        int eventType = parser.getEventType();
        HoraireAPI currentHoraire = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name = null;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    horaireListe = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    //name = parser.getName();
                    switch(parser.getName())
                    {
                        case "train":
                            if(currentHoraire==null)
                                currentHoraire = new HoraireAPI();
                            break;
                        case "date":
                            String tmpDate = parser.nextText();
                            tmpDate = tmpDate.substring(tmpDate.length()-5);
                            currentHoraire.setDate(tmpDate);
                            break;
                        case "num":
                            currentHoraire.setTrainNum(parser.nextText());
                            break;
                        case "miss":
                            currentHoraire.setTrainMiss(parser.nextText());
                            break;
                        case "term":
                            currentHoraire.setTerminus(stationList.getLabelFromId(parser.nextText()));
                            break;
                    }
                    break;
                case XmlPullParser.END_TAG:
                    switch(parser.getName())
                    {
                        case "train":
                            horaireListe.add(currentHoraire);
                            currentHoraire = null;
                            break;
                    }
                    /*name = parser.getName();
                    if (name.equalsIgnoreCase("station") && currentStation != null) {
                        stationsListe.add(currentStation);
                    }*/
            }
            eventType = parser.next();
        }

        return horaireListe;
    }

    public static ArrayList<HoraireAPI> SortHoraire(ArrayList<HoraireAPI> _horaireList) {
        ArrayList<HoraireAPI> sortedList = _horaireList;

        Collections.sort(sortedList, new Comparator<HoraireAPI>(){

            @Override
            public int compare(HoraireAPI o1, HoraireAPI o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        ArrayList<HoraireAPI> preSortedList = new ArrayList<HoraireAPI>();
        preSortedList.addAll(sortedList);
        sortedList.clear();

        for(HoraireAPI s: preSortedList)
        {
            boolean toAdd = true;
            for(HoraireAPI t: sortedList)
            {
                if(s.getTrainNum().equals(t.getTrainNum()))
                {
                    toAdd = false;
                }
            }
            if(toAdd)
                sortedList.add(s);
        }

        return sortedList;
    }

}
