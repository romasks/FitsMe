package ru.fitsme.android.app.di;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

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
import ru.fitsme.android.data.repositories.ResourceRepository;
import ru.fitsme.android.data.repositories.SignInUpRepository;
import ru.fitsme.android.data.repositories.TextValidator;
import ru.fitsme.android.data.repositories.UserInfoRepository;
import ru.fitsme.android.data.repositories.clothes.ClothesIndexRepository;
import ru.fitsme.android.data.repositories.clothes.ClothesLikeRepository;
import ru.fitsme.android.data.repositories.clothes.ClothesRepository;
import ru.fitsme.android.data.repositories.favourites.FavouritesActionRepository;
import ru.fitsme.android.data.repositories.favourites.FavouritesRepository;
import ru.fitsme.android.domain.boundaries.clothes.IClothesIndexRepository;
import ru.fitsme.android.domain.boundaries.clothes.IClothesLikeRepository;
import ru.fitsme.android.domain.boundaries.clothes.IClothesRepository;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesActionRepository;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesRepository;
import ru.fitsme.android.domain.boundaries.signinup.IResourceRepository;
import ru.fitsme.android.domain.boundaries.signinup.ISignInUpRepository;
import ru.fitsme.android.domain.boundaries.signinup.ITextValidator;
import ru.fitsme.android.domain.boundaries.signinup.IUserInfoRepository;
import ru.fitsme.android.domain.interactors.auth.ISignInUpInteractor;
import ru.fitsme.android.domain.interactors.auth.SignInUpInteractor;
import ru.fitsme.android.domain.interactors.clothes.ClothesInteractor;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.domain.interactors.favourites.FavouritesInteractor;
import ru.fitsme.android.domain.interactors.favourites.IFavouritesInteractor;
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
            bind(ISignInUpInteractor.class).to(SignInUpInteractor.class);
            bind(ISignInUpRepository.class).to(SignInUpRepository.class);
            bind(IUserInfoRepository.class).to(UserInfoRepository.class);
            bind(ITextValidator.class).to(TextValidator.class);
            bind(IResourceRepository.class).to(ResourceRepository.class);
            bind(Context.class).toInstance(application);
            bind(Gson.class).toInstance(gson());
            bind(GsonConverterFactory.class).toInstance(gsonConverterFactory());
            bind(Retrofit.class).toInstance(retrofit());
            bind(ApiService.class).toInstance(apiService(retrofit()));
            bind(AuthInfoStorage.class).to(AuthInfoStorage.class);
            bind(Scheduler.class).withName("main").toInstance(AndroidSchedulers.mainThread());
            bind(Scheduler.class).withName("work").toInstance(Schedulers.io());

            bind(IClothesInteractor.class).to(ClothesInteractor.class);
            bind(IClothesRepository.class).to(ClothesRepository.class);
            bind(IClothesIndexRepository.class).to(ClothesIndexRepository.class);
            bind(IClothesLikeRepository.class).to(ClothesLikeRepository.class);

            bind(IFavouritesInteractor.class).to(FavouritesInteractor.class);
            bind(IFavouritesRepository.class).to(FavouritesRepository.class);
            bind(IFavouritesActionRepository.class).to(FavouritesActionRepository.class);
        }});
    }

    @NonNull
    @Named("serverBaseUrl")
    private String serverBaseUrl() {
        return "https://fitsme.ru/";
    }

    @NonNull
    private Gson gson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
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
