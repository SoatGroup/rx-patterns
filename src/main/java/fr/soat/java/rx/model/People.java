package fr.soat.java.rx.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david.wursteisen on 27/05/2015.
 */
public class People {

    private String name;
    @SerializedName("homeworld")
    private String homeWorldUrl;
    private String gender;
    private String height;

    public String getName() {
        return name;
    }

    public String getHomeWorldUrl() {
        return homeWorldUrl;
    }

    public String getGender() {
        return gender;
    }

    public String getHeight() {
        return height;
    }

    public int getHomeworldId() {
        String strId = getHomeWorldUrl().replaceAll("http(s?)://swapi.co/api/planets/", "").replaceAll("/", "");
        return Integer.valueOf(strId);
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                '}';
    }
}
