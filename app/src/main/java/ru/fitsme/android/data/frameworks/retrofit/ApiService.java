package ru.fitsme.android.data.frameworks.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.fitsme.android.data.frameworks.retrofit.entities.AuthToken;

public interface ApiService {
    @POST("auth/signup")
    Call<AuthToken> signUp(@Query("login") String login, @Query("password") String password);

    @POST("auth/signin")
    Call<AuthToken> signIn(@Query("login") String login, @Query("password") String password);


}
