package co.edu.udea.compumovil.gr04_20171.lab3.user.data;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by jonnatan on 4/04/17.
 */

public interface UserInterface {

    @GET("/Users/1")
    void getUser(Callback<User> cb);

    @POST("/Users")
    void createUser(@Body User user, Callback<Response> cb);

    @POST("/login")
    void login(@Body LoginBody loginBody, Callback<Affiliate> cb);
}
