package hoon.example.hoon_retrofit;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("users/{userId}")
    Single<User> getUser(@Path("userId") int userId);
}
