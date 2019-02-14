package ru.fitsme.android.data.frameworks.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.fitsme.android.data.frameworks.retrofit.entities.AuthToken;
import ru.fitsme.android.data.frameworks.retrofit.entities.OkResponse;
import ru.fitsme.android.domain.entities.signinup.SignInInfo;

public interface ApiService {
    @POST("signup/")
    Call<OkResponse<AuthToken>> signUp(@Body SignInInfo signInInfo);

    @POST("signin/")
    Call<OkResponse<AuthToken>> signIn(@Body SignInInfo signInInfo);
}
