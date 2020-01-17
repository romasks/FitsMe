package ru.fitsme.android.data.frameworks.retrofit;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.fitsme.android.data.frameworks.retrofit.entities.AuthToken;
import ru.fitsme.android.data.frameworks.retrofit.entities.FeedbackRequest;
import ru.fitsme.android.data.frameworks.retrofit.entities.LikedItem;
import ru.fitsme.android.data.frameworks.retrofit.entities.OkResponse;
import ru.fitsme.android.data.frameworks.retrofit.entities.OrderUpdate;
import ru.fitsme.android.data.frameworks.retrofit.entities.OrderedItem;
import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsItemRequest;
import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsPaymentRequest;
import ru.fitsme.android.data.repositories.clothes.entity.ClothesPage;
import ru.fitsme.android.data.repositories.favourites.entity.FavouritesPage;
import ru.fitsme.android.data.repositories.orders.entity.OrdersPage;
import ru.fitsme.android.data.repositories.returns.entity.ReturnsPage;
import ru.fitsme.android.domain.entities.auth.SignInfo;
import ru.fitsme.android.data.repositories.clothes.entity.RepoClotheBrand;
import ru.fitsme.android.data.repositories.clothes.entity.RepoClotheColor;
import ru.fitsme.android.data.repositories.clothes.entity.RepoClotheProductName;
import ru.fitsme.android.domain.entities.clothes.ClotheSize;
import ru.fitsme.android.domain.entities.clothes.LikedClothesItem;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.entities.profile.Profile;
import ru.fitsme.android.domain.entities.returns.ReturnsOrder;
import ru.fitsme.android.domain.entities.returns.ReturnsOrderItem;
import ru.fitsme.android.utils.OrderStatus;

public interface ApiService {
    @POST("customers/signin/")
    Single<OkResponse<AuthToken>> signIn(@Body SignInfo signInfo);

    @POST("customers/signup/")
    Single<OkResponse<AuthToken>> signUp(@Body SignInfo signInfo);

    @POST("viewed/")
    Single<OkResponse<LikedClothesItem>> likeItem(@Header("Authorization") String token,
                                                  @Body LikedItem likedItem);

    @GET("clothes/")
    Single<OkResponse<ClothesPage>> getClothes(@Header("Authorization") String token,
                                               @Query("page") int page,
                                               @Query("clothe_type") String productNameList,
                                               @Query("brands") String brandsList,
                                               @Query("colors") String colorsList);

    @GET("clothes/sizes/")
    Single<OkResponse<List<ClotheSize>>> getClotheSizes(@Header("Authorization") String token);

    @GET("viewed/")
    Single<OkResponse<FavouritesPage>> getFavouritesClothes(@Header("Authorization") String token,
                                                            @Query("page") int page);

    @PUT("viewed/{itemId}/")
    Single<OkResponse<FavouritesItem>> removeItemFromFavourites(@Header("Authorization") String token,
                                                                @Path("itemId") int itemId);

    @PUT("viewed/{itemId}/")
    Single<OkResponse<FavouritesItem>> restoreItemToFavourites(@Header("Authorization") String token,
                                                               @Path("itemId") int itemId);

    @POST("orders/items/")
    Single<OkResponse<OrderItem>> addItemToCart(@Header("Authorization") String token,
                                                @Body OrderedItem orderedItem);

    @DELETE("orders/items/{itemId}/")
    Single<Response<Void>> removeItemFromCart(@Header("Authorization") String token,
                                              @Path("itemId") int itemId);

    @GET("orders/")
    Single<OkResponse<OrdersPage>> getOrders(@Header("Authorization") String token,
                                             @Query("page") int page);

    @GET("orders/")
    Single<OkResponse<OrdersPage>> getOrders(@Header("Authorization") String token,
                                             @Query("status") OrderStatus status);

    @GET("orders/{id}/")
    Single<OkResponse<Order>> getOrderById(@Header("Authorization") String token,
                                           @Path("id") long orderId);

    @GET("orders/return/")
    Single<OkResponse<OrdersPage>> getReturnsOrders(@Header("Authorization") String token);

    @PUT("orders/{id}/")
    Single<OkResponse<Order>> updateOrderById(@Header("Authorization") String token,
                                              @Path("id") long orderId,
                                              @Body OrderUpdate order);

    @GET("profile/")
    Single<OkResponse<Profile>> getProfile(@Header("Authorization") String token);

    @PUT("profile/")
    Single<OkResponse<Profile>> setProfile(@Header("Authorization") String token,
                                           @Body Profile profile);

    @GET("returns/")
    Single<OkResponse<ReturnsPage>> getReturnsClothes(@Header("Authorization") String token,
                                                      @Query("page") int page);

    @POST("returns/items/")
    Single<OkResponse<ReturnsOrderItem>> addItemToReturn(@Header("Authorization") String token,
                                                         @Body ReturnsItemRequest request);

    @PUT("returns/{id}/")
    Single<OkResponse<ReturnsOrderItem>> changeReturnsPayment(@Header("Authorization") String token,
                                                              @Path("id") long returnId,
                                                              @Body ReturnsPaymentRequest request);

    @GET("returns/{id}/")
    Single<OkResponse<ReturnsOrder>> getReturnById(@Header("Authorization") String token,
                                                   @Path("id") long returnId);

    @POST("feedback")
    Single<OkResponse<Boolean>> sendFeedback(@Header("Authorization") String token,
                                 @Body FeedbackRequest request);

    @GET("clothes/brands/")
    Single<OkResponse<List<RepoClotheBrand>>> getClotheBrands(@Header("Authorization") String token);

    @GET("clothes/colors")
    Single<OkResponse<List<RepoClotheColor>>> getClotheColors(@Header("Authorization") String token);

    @GET("clothes/productnames")
    Single<OkResponse<List<RepoClotheProductName>>> getClotheProductNames(@Header("Authorization") String token);
}
