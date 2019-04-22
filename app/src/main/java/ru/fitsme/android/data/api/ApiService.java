package ru.fitsme.android.data.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.fitsme.android.data.entities.response.AuthToken;
import ru.fitsme.android.data.entities.request.OrderUpdate;
import ru.fitsme.android.data.entities.request.OrderedItem;
import ru.fitsme.android.data.entities.request.LikedItem;
import ru.fitsme.android.data.entities.response.OkResponse;
import ru.fitsme.android.data.entities.response.clothes.ClothesPage;
import ru.fitsme.android.data.entities.response.favourites.FavouritesPage;
import ru.fitsme.android.data.entities.response.orders.OrdersPage;
import ru.fitsme.android.data.entities.response.clothes.LikedClothesItem;
import ru.fitsme.android.data.entities.response.orders.Order;
import ru.fitsme.android.data.entities.response.orders.OrderItem;
import ru.fitsme.android.data.entities.response.signinup.SignInInfo;

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

    @GET("viewed/")
    Call<OkResponse<FavouritesPage>> getFavouritesClothes(@Header("Authorization") String token,
                                                          @Query("page") int page);

    @DELETE("viewed/{itemId}/")
    Call<Void> deleteFavouritesItem(@Header("Authorization") String token,
                                            @Path("itemId") int itemId);

    @POST("orders/items/")
    Call<OkResponse<OrderItem>> addItemToCart(@Header("Authorization") String token,
                                              @Body OrderedItem orderedItem);

    @GET("orders/")
    Call<OkResponse<OrdersPage>> getOrders(@Header("Authorization") String token,
                                           @Query("page") int page);

    @PUT("orders/{id}/")
    Call<OkResponse<Order>> updateOrderById(@Header("Authorization") String token,
                                            @Path("id") long orderId,
                                            @Body OrderUpdate order);
}
