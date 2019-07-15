package ru.fitsme.android.data.frameworks.retrofit;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.fitsme.android.data.frameworks.retrofit.entities.AuthToken;
import ru.fitsme.android.data.frameworks.retrofit.entities.OrderUpdate;
import ru.fitsme.android.data.frameworks.retrofit.entities.OrderedItem;
import ru.fitsme.android.data.frameworks.retrofit.entities.LikedItem;
import ru.fitsme.android.data.frameworks.retrofit.entities.OkResponse;
import ru.fitsme.android.data.repositories.clothes.entity.ClothesPage;
import ru.fitsme.android.data.repositories.favourites.entity.FavouritesPage;
import ru.fitsme.android.data.repositories.orders.entity.OrdersPage;
import ru.fitsme.android.domain.entities.auth.SignInfo;
import ru.fitsme.android.domain.entities.clothes.LikedClothesItem;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.utils.OrderStatus;

public interface ApiService {
    @POST("customers/signin/")
    Single<OkResponse<AuthToken>> signIn(@Body SignInfo signInfo);

    @POST("customers/signup/")
    Single<OkResponse<AuthToken>> signUp(@Body SignInfo signInfo);

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
    Call<OkResponse<Void>> deleteFavouritesItem(@Header("Authorization") String token,
                                            @Path("itemId") int itemId);

    @POST("orders/items/")
    Call<OkResponse<OrderItem>> addItemToCart(@Header("Authorization") String token,
                                              @Body OrderedItem orderedItem);

    @GET("orders/")
    Call<OkResponse<OrdersPage>> getOrders(@Header("Authorization") String token,
                                           @Query("page") int page);

    @GET("orders/")
    Call<OkResponse<OrdersPage>> getOrders(@Header("Authorization") String token,
                                           @Query("status") OrderStatus status);

    @PUT("orders/{id}/")
    Call<OkResponse<Order>> updateOrderById(@Header("Authorization") String token,
                                            @Path("id") long orderId,
                                            @Body OrderUpdate order);
}
