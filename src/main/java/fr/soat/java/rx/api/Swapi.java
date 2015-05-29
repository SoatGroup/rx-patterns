package fr.soat.java.rx.api;

import fr.soat.java.rx.model.Page;
import fr.soat.java.rx.model.People;
import fr.soat.java.rx.model.Planet;
import fr.soat.java.rx.model.Starship;
import fr.soat.java.rx.model.Vehicle;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by david.wursteisen on 27/05/2015.
 */
public interface Swapi {

    @GET("/planets/{id}/")
    Observable<Planet> planet(@Path("id") int id);

    @GET("/planets/")
    Observable<Page<Planet>> planets();

    @GET("/planets/")
    Observable<Page<Planet>> planets(@Query("page") int page);


    @GET("/people/{id}/")
    Observable<People> people(@Path("id") int id);

    @GET("/people/")
    Observable<Page<People>> peoples();

    @GET("/people/")
    Observable<Page<People>> peoples(@Query("page") int page);

    @GET("/vehicles/{id}/")
    Observable<Vehicle> vehicle(@Path("id") int id);

    @GET("/vehicles/")
    Observable<Page<Vehicle>> vehicles();

    @GET("/vehicles/")
    Observable<Page<Vehicle>> vehicles(@Query("page") int page);

    @GET("/starships/{id}/")
    Observable<Starship> starship(@Path("id") int id);

    @GET("/starships/")
    Observable<Page<Starship>> starships();

    @GET("/starships/")
    Observable<Page<Starship>> starships(@Query("page") int page);


}
