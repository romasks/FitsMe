package ru.fitsme.android.data.frameworks.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.fitsme.android.data.frameworks.retrofit.entities.AuthToken;
import ru.fitsme.android.data.frameworks.retrofit.entities.OkResponse;
import ru.fitsme.android.domain.entities.clothes.ClothesPage;
import ru.fitsme.android.domain.entities.signinup.SignInInfo;

public interface ApiService {
    @POST("signup/")
    Call<OkResponse<AuthToken>> signUp(@Body SignInInfo signInInfo);

    @POST("signin/")
    Call<OkResponse<AuthToken>> signIn(@Body SignInInfo signInInfo);

    @GET("clothes")
    Call<OkResponse<ClothesPage>> getClothes(@Query("page") int page);
}
