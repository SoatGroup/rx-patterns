package fr.soat.java.rx.model;

/**
 * Created by david.wursteisen on 29/05/2015.
 */
public class Starship {

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
        return "Starship{" +
                "name='" + name + '\'' +
                ", modele='" + modele + '\'' +
                '}';
    }
}
