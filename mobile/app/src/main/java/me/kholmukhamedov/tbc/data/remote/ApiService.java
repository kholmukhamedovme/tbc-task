package me.kholmukhamedov.tbc.data.remote;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Single;
import me.kholmukhamedov.tbc.models.data.UserBean;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Retrofit interface of TBC API
 */
public interface ApiService {

    /**
     * Base URL
     * TODO: Change it if running on real device
     */
    String HOST = "http://10.0.2.2:80";

    /**
     * Get list of all users
     *
     * @return Single RxJava source with array of {@link UserBean} bean
     */
    @GET("/users/")
    Single<ArrayList<UserBean>> getUsers();

    /**
     * Update user data
     *
     * @param id     user ID
     * @param status {@code true} for activated, {@code false} for deactivated
     * @return Completable RxJava source
     */
    @PUT("/users/")
    Completable updateUser(@Query("id") int id,
                           @Query("status") boolean status);

}
