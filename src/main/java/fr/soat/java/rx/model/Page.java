package fr.soat.java.rx.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david.wursteisen on 27/05/2015.
 */
public class Page<T> {

    private int count;
    @SerializedName("next")
    private String nextUrl;
    @SerializedName("previous")
    private String previousUrl;
    private List<T> results;

    public int getCount() {
        return count;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public String getPreviousUrl() {
        return previousUrl;
    }

    public List<T> getResults() {
        return new ArrayList<>(results);
    }
}
