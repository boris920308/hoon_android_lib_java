package hoon.example.hoon_retrofit;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {
    @GET("users/{userId}")
    Single<User> getUser(@Path("userId") int userId);
}
