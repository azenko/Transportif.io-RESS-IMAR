package com.esiea.vresscimar.transportifio;

/**
 * Created by vress on 13/12/2016.
 */

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Stations {

    private List<Station> stationList;
    Context context;

    public Stations(Context _context) {
        this.context = _context;

        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = this.context.getAssets().open("stations.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            this.stationList = parseXML(parser);

        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String[] getStationsLabel() {
        List<String> labelList = new ArrayList<String>();
        String lastLabel = "";
        for (Station sts : this.stationList) {
            if(lastLabel!=sts.getLabel())
            {
                labelList.add(sts.getLabel());
                lastLabel = sts.getLabel();
            }
        }

        String[] labelArray = new String[labelList.size()];
        labelList.toArray(labelArray);
        return labelArray;
    }

    public boolean thereIsAStationCalled(String _stationName) {
        for (Station sts: this.stationList) {
            if(sts.getLabel().toLowerCase().equals(_stationName.toLowerCase()))
                return true;
        }
        return false;
    }

    public List<String> getIdFromLabel(String _stationName) {
        List<String> listString = new ArrayList<String>();

        for (Station sts: this.stationList) {
            if(sts.getLabel().toLowerCase().equals(_stationName.toLowerCase()))
                listString.add(sts.getId());
        }
        return listString;
    }

    public String getLabelFromId(String _stationID) {
        String _label = "";

        for (Station sts: this.stationList) {
            if(sts.getId().toLowerCase().equals(_stationID.toLowerCase()))
                _label = sts.getLabel();
        }

        return _label;
    }

    private ArrayList<Station> parseXML(XmlPullParser parser) throws XmlPullParserException,IOException {
        ArrayList<Station> stationsListe = null;
        int eventType = parser.getEventType();
        Station currentStation = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name = null;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    stationsListe = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    //name = parser.getName();
                    switch(parser.getName())
                    {
                        case "station":
                            if(currentStation==null)
                                currentStation = new Station();
                            break;
                        case "id":
                            currentStation.setId(parser.nextText());
                            break;
                        case "label":
                            currentStation.setLabel(parser.nextText());
                            break;
                    }
                    break;
                case XmlPullParser.END_TAG:
                    switch(parser.getName())
                    {
                        case "station":
                            stationsListe.add(currentStation);
                            currentStation = null;
                            break;
                    }
                    /*name = parser.getName();
                    if (name.equalsIgnoreCase("station") && currentStation != null) {
                        stationsListe.add(currentStation);
                    }*/
            }
            eventType = parser.next();
        }

        return stationsListe;
    }
}
