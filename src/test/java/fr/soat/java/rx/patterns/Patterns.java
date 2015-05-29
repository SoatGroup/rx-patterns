package fr.soat.java.rx.patterns;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoDatabase;
import fr.soat.java.rx.api.Swapi;
import fr.soat.java.rx.model.People;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import retrofit.RestAdapter;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by david.wursteisen on 27/05/2015.
 */
public class Patterns {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private Swapi remoteApi;

    static {
        Logger.getLogger("org.mongodb").setLevel(Level.OFF);
        Logger.getLogger("com.mongodb").setLevel(Level.OFF);
    }

    @Before
    public void setUp() throws Exception {

        mongoClient = MongoClients.create();

        database = mongoClient.getDatabase("starwars");

        ExecutorService httpExecutor = Executors.newFixedThreadPool(4);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://swapi.co/api/")
                .setExecutors(httpExecutor, httpExecutor)
                .setRequestInterceptor(request -> {
                    request.addHeader("User-Agent", "RxPatterns");
                    request.addHeader("Content-Type", "application/json");
                    request.addHeader("Accept", "*/*");
                })
                        //  .setLogLevel(RestAdapter.LogLevel.BASIC)
                .build();

        remoteApi = restAdapter.create(Swapi.class);
    }

    @After
    public void tearDown() throws Exception {
        mongoClient.close();
    }

    @Test
    public void should_get_from_mongo() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        database.getCollection("people")
                .find()
                .limit(1)
                .toObservable()
                .flatMap(p -> database.getCollection("people").find().first())
                .subscribe(System.out::println, (e) -> latch.countDown(), latch::countDown);

        latch.await();
    }

    @Test
    public void should_chain_async_calls() throws InterruptedException {

        // db.people.insert({peopleId: NumberInt(1)})
        CountDownLatch latch = new CountDownLatch(1);
        database.getCollection("people")
                .find()
                .first()
                .flatMap(doc -> remoteApi.people(doc.getInteger("peopleId")))
                .flatMap(people -> remoteApi.planet(people.getHomeworldId()))
                .subscribe((planet) -> System.out.println("Planet name => " + planet.getName()), (e) -> latch.countDown(),
                        latch::countDown);

        latch.await();
    }

    @Test
    public void should_merge_values() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        database.getCollection("emptyCollection")
                .find()
                .first()
                .doOnSubscribe(() -> System.out.println("Will query MongoDB !"))
                .single()
                .map(doc -> new People(doc.getString("name")))
                .retry(1)
                .onErrorResumeNext(remoteApi.people(-1)
                        .doOnError(
                                (e) -> System.err.println("got this exception : " + e.getMessage() + ". Will fallback with default People"))
                        .onErrorReturn((e) -> new People("Default People")))
                .subscribe(System.out::println, (e) -> latch.countDown(), latch::countDown);

        latch.await();
    }

    @Test
    public void should_switch_on_error() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        remoteApi.people(1).flatMap(luke -> {
            Observable<String> vehicles = Observable.from(luke.getVehiclesIds())
                    .flatMap(remoteApi::vehicle)
                    .map(vehicle -> luke.getName() + " can drive " + vehicle.getName());

            Observable<String> starships = Observable.from(luke.getStarshipsIds())
                    .flatMap(remoteApi::starship)
                    .map(starship -> luke.getName() + " can fly with " + starship.getName());

            return Observable.merge(vehicles, starships);
        }).subscribe(System.out::println, (e) -> latch.countDown(), latch::countDown);

        latch.await();
    }

    @Test
    public void should_compose_async_calls() throws InterruptedException {
        // for(i = 1 ; i <= 61 ; i++) { db.planet.insert({planetId: NumberInt(i)})}

        CountDownLatch latch = new CountDownLatch(1);
        database.getCollection("planet")
                .find()
                .toObservable()
                .observeOn(Schedulers.computation())
                .map(document -> document.getInteger("planetId"))
                .concatMap(id -> remoteApi.planet(id)
                        .flatMap(planet -> Observable.from(planet.getResidentsIds())
                                        .flatMap(peopleId -> remoteApi.people(peopleId))
                                        .map(people -> String.format("%s live on %s planet", people.getName(), planet.getName()))
                        ))
                .subscribe(System.out::println, (e) -> latch.countDown(), latch::countDown);

        latch.await();
    }
}
