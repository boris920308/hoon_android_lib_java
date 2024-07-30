package hoon.example.hoon_retrofit;

import io.reactivex.Single;

public class UserRepository {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private UserService userService;

    public UserRepository() {
        userService = RetrofitClient
                .getClient(BASE_URL)
                .create(UserService.class);
    }

    public Single<User> getUser() {
        return userService.getUser(1);
    }
}
