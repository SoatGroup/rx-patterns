package fr.soat.java.rx.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david.wursteisen on 27/05/2015.
 */
public class Planet {

    private String climate;
    private String diamter;
    private String name;
    @SerializedName("residents")
    private Set<String> residentsUrls;
    private String terrain;
    private String population;

    public String getClimate() {
        return climate;
    }

    public String getDiamter() {
        return diamter;
    }

    public String getName() {
        return name;
    }

    public Set<String> getResidentsUrls() {
        return new HashSet<>(residentsUrls);
    }

    public Set<Integer> getResidentsIds() {
        return getResidentsUrls()
                .stream()
                .map(url -> url.replaceAll("http(s?)://swapi.co/api/people/", "").replaceAll("/", ""))
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }

    public String getTerrain() {
        return terrain;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "name='" + name + '\'' +
                '}';
    }
}
