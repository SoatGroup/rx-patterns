package fr.soat.java.rx.model;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @SerializedName("vehicles")
    private List<String> vehiclesUrl = Collections.emptyList();
    @SerializedName("starships")
    private List<String> starshipsUrl = Collections.emptyList();

    public People() {
    }

    public People(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public String getGender() {
        return gender;
    }

    public String getHeight() {
        return height;
    }

    public int getHomeworldId() {
        String strId = homeWorldUrl.replaceAll("http(s?)://swapi.co/api/planets/", "").replaceAll("/", "");
        return Integer.valueOf(strId);
    }

    public Set<Integer> getVehiclesIds() {
        return convert(vehiclesUrl, url -> url.replaceAll("http(s?)://swapi.co/api/vehicles/", "").replaceAll("/", ""));
    }

    public Set<Integer> getStarshipsIds() {
        return convert(starshipsUrl, url -> url.replaceAll("http(s?)://swapi.co/api/starships/", "").replaceAll("/", ""));
    }

    private Set<Integer> convert(List<String> urlssource, Function<String, String> mapFunction) {
        return urlssource
                .stream()
                .map(mapFunction)
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }


    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                '}';
    }
}
