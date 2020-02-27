package ru.fitsme.android.app.di;

import android.app.Application;
import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.fitsme.android.data.frameworks.retrofit.ApiService;
import ru.fitsme.android.data.frameworks.sharedpreferences.AuthInfoStorage;
import ru.fitsme.android.data.frameworks.sharedpreferences.IAuthInfoStorage;
import ru.fitsme.android.data.frameworks.sharedpreferences.IReturnsStorage;
import ru.fitsme.android.data.frameworks.sharedpreferences.ISettingsStorage;
import ru.fitsme.android.data.frameworks.sharedpreferences.ReturnsStorage;
import ru.fitsme.android.data.frameworks.sharedpreferences.SettingsStorage;
import ru.fitsme.android.data.repositories.auth.AuthRepository;
import ru.fitsme.android.data.repositories.auth.SignRepository;
import ru.fitsme.android.data.repositories.auth.TextValidator;
import ru.fitsme.android.data.repositories.clothes.ClothesRepository;
import ru.fitsme.android.data.repositories.favourites.FavouritesActionRepository;
import ru.fitsme.android.data.repositories.favourites.FavouritesRepository;
import ru.fitsme.android.data.repositories.feedback.FeedbackRepository;
import ru.fitsme.android.data.repositories.orders.OrdersActionRepository;
import ru.fitsme.android.data.repositories.orders.OrdersRepository;
import ru.fitsme.android.data.repositories.profile.ProfileRepository;
import ru.fitsme.android.data.repositories.returns.ReturnsRepository;
import ru.fitsme.android.domain.boundaries.IFeedbackRepository;
import ru.fitsme.android.domain.boundaries.auth.IAuthRepository;
import ru.fitsme.android.domain.boundaries.auth.ISignRepository;
import ru.fitsme.android.domain.boundaries.auth.ITextValidator;
import ru.fitsme.android.domain.boundaries.clothes.IClothesRepository;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesActionRepository;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesRepository;
import ru.fitsme.android.domain.boundaries.orders.IOrdersActionRepository;
import ru.fitsme.android.domain.boundaries.orders.IOrdersRepository;
import ru.fitsme.android.domain.boundaries.profile.IProfileRepository;
import ru.fitsme.android.domain.boundaries.retunrs.IReturnsRepository;
import ru.fitsme.android.domain.interactors.auth.AuthInteractor;
import ru.fitsme.android.domain.interactors.auth.IAuthInteractor;
import ru.fitsme.android.domain.interactors.auth.ISignInteractor;
import ru.fitsme.android.domain.interactors.auth.SignInteractor;
import ru.fitsme.android.domain.interactors.clothes.ClothesInteractor;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.domain.interactors.favourites.FavouritesInteractor;
import ru.fitsme.android.domain.interactors.favourites.IFavouritesInteractor;
import ru.fitsme.android.domain.interactors.feedback.FeedbackInteractor;
import ru.fitsme.android.domain.interactors.feedback.IFeedbackInteractor;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.domain.interactors.orders.OrdersInteractor;
import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.domain.interactors.profile.ProfileInteractor;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.domain.interactors.returns.ReturnsInteractor;
import toothpick.Scope;
import toothpick.Toothpick;
import toothpick.config.Module;

@Singleton
public class DI {
    private Scope appScope;

    @Inject
    public DI(Application application) {
        appScope = Toothpick.openScope(application);
        appScope.installModules(new Module() {{
            bind(IAuthInteractor.class).to(AuthInteractor.class);
            bind(IAuthRepository.class).to(AuthRepository.class);
            bind(ISignRepository.class).to(SignRepository.class);
            bind(ISignInteractor.class).to(SignInteractor.class);
            bind(ITextValidator.class).to(TextValidator.class);
            bind(Context.class).toInstance(application);
            bind(Gson.class).toInstance(gson());
            bind(GsonConverterFactory.class).toInstance(gsonConverterFactory());
            bind(Retrofit.class).toInstance(retrofit());
            bind(ApiService.class).toInstance(apiService(retrofit()));
            bind(Scheduler.class).withName("main").toInstance(AndroidSchedulers.mainThread());
            bind(Scheduler.class).withName("work").toInstance(Schedulers.io());

            bind(IClothesInteractor.class).to(ClothesInteractor.class);
            bind(IClothesRepository.class).to(ClothesRepository.class);

            bind(IFavouritesInteractor.class).to(FavouritesInteractor.class);
            bind(IFavouritesRepository.class).to(FavouritesRepository.class);
            bind(IFavouritesActionRepository.class).to(FavouritesActionRepository.class);

            bind(IOrdersInteractor.class).to(OrdersInteractor.class);
            bind(IOrdersRepository.class).to(OrdersRepository.class);
            bind(IOrdersActionRepository.class).to(OrdersActionRepository.class);

            bind(IProfileInteractor.class).to(ProfileInteractor.class);
            bind(IProfileRepository.class).to(ProfileRepository.class);

            bind(IAuthInfoStorage.class).to(AuthInfoStorage.class);
            bind(ISettingsStorage.class).to(SettingsStorage.class);
            bind(IReturnsStorage.class).to(ReturnsStorage.class);

            bind(IReturnsInteractor.class).to(ReturnsInteractor.class);
            bind(IReturnsRepository.class).to(ReturnsRepository.class);

            bind(IFeedbackInteractor.class).to(FeedbackInteractor.class);
            bind(IFeedbackRepository.class).to(FeedbackRepository.class);
        }});
    }

    @NonNull
    @Named("serverBaseUrl")
    private String serverBaseUrl() {
        return "https://fitsme.ru/";
    }

    @NonNull
    private Gson gson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .serializeNulls()
                .create();
    }

    @NonNull
    private GsonConverterFactory gsonConverterFactory() {
        return GsonConverterFactory.create(gson());
    }

    @NonNull
    private Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(serverBaseUrl())
                .client(okHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory())
                .build();
    }

    @NonNull
    private ApiService apiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @NonNull
    private OkHttpClient okHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    public <T> void inject(T object) {
        Toothpick.inject(object, appScope);
    }
}
