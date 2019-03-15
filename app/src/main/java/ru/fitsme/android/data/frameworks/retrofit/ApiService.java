package ru.fitsme.android.data.frameworks.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.fitsme.android.data.frameworks.retrofit.entities.AuthToken;
import ru.fitsme.android.data.frameworks.retrofit.entities.LikedItem;
import ru.fitsme.android.data.frameworks.retrofit.entities.OkResponse;
import ru.fitsme.android.data.repositories.clothes.entity.ClothesPage;
import ru.fitsme.android.domain.entities.clothes.LikedClothesItem;
import ru.fitsme.android.domain.entities.signinup.SignInInfo;

public interface ApiService {
    @POST("customers/signup/")
    Call<OkResponse<AuthToken>> signUp(@Body SignInInfo signInInfo);

    @POST("customers/signin/")
    Call<OkResponse<AuthToken>> signIn(@Body SignInInfo signInInfo);

    @GET("clothes/")
    Call<OkResponse<ClothesPage>> getClothes(@Header("Authorization") String token,
                                             @Query("page") int page);

    @POST("viewed/")
    Call<OkResponse<LikedClothesItem>> likeItem(@Header("Authorization") String token,
                                                @Body LikedItem likedItem);

    @GET("/viewed/?page={pageNumber}")
    Call<OkResponse<ClothesPage>> getFavouritesClothes(@Header("Authorization") String token,
                                                  @Path("pageNumber") int pageNumber);
}
