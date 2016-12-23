package com.ournet.weather.data;

import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dumitru Cantea on 12/20/16.
 */

public class Place implements ILocation {
    public int id;
    public String name;
    public String timezone;
    public double longitude;
    public double latitude;
    public String country_code;
    public Place region;
    public boolean isSelected = false;
    private JSONObject json;

    private Map<String, String> names = new HashMap();

    public Place(){

    }

    public Place(float longitude, float latitude){
        this.longitude=longitude;
        this.latitude=latitude;
    }

    public String name(String lang) {
        return names.containsKey(lang) ? names.get(lang) : this.name;
    }

    public JSONObject getJson() {
        return this.json;
    }

    public static Place create(JSONObject data) {
        if (data == null) {
            return null;
        }
        Place place = new Place();
        place.json = data;

        Log.i("place", data.toString());

        try {
            place.id = data.getInt("id");
            place.name = data.getString("name");
            if(data.has("timezone")){
                place.timezone = data.getString("timezone");
            }
            if (data.has("longitude")) {
                place.longitude = data.getDouble("longitude");
            }
            if (data.has("latitude")) {
                place.latitude = data.getDouble("latitude");
            }
            if (data.has("country_code")) {
                place.country_code = data.getString("country_code").toUpperCase();
            }
            if (data.has("isSelected")) {
                place.isSelected = data.getBoolean("isSelected");
            }
            if (data.has("region")) {
                place.region = Place.create(data.getJSONObject("region"));
            }

            // is from API
            if (data.has("alternatenames")) {
                String alternatenames = data.getString("alternatenames");
                if (alternatenames != null) {
                    String[] names = alternatenames.split(";");
                    for (int i = 0; i < names.length; i++) {
                        String name = names[i].substring(0, names[i].length() - 4);
                        String lang = names[i].substring(name.length() + 1, name.length() + 3);
                        place.names.put(lang, name);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return place;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
