package ru.fitsme.android.data.frameworks.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.fitsme.android.data.frameworks.retrofit.entities.AuthToken;
import ru.fitsme.android.data.frameworks.retrofit.entities.OkResponse;

public interface ApiService {
    @POST("signup")
    Call<OkResponse<AuthToken>> signUp(@Query("login") String login, @Query("password") String password);

    @POST("signin")
    Call<OkResponse<AuthToken>> signIn(@Query("login") String login, @Query("password") String password);


}
