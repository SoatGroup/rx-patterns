package fr.soat.java.rx.model;

/**
 * Created by david.wursteisen on 29/05/2015.
 */
public class Vehicle {

    private String name;
    private String modele;

    public String getName() {
        return name;
    }

    public String getModele() {
        return modele;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "name='" + name + '\'' +
                ", modele='" + modele + '\'' +
                '}';
    }
}
