package hoon.example.hoon_retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("users/{userId}")
    Call<User> getUser(@Path("userId") int userId);
}
