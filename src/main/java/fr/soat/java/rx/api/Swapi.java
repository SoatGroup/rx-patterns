package fr.soat.java.rx.api;

import fr.soat.java.rx.model.Page;
import fr.soat.java.rx.model.People;
import fr.soat.java.rx.model.Planet;
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

}
