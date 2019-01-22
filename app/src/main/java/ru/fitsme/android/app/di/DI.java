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
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.fitsme.android.data.frameworks.retrofit.ApiService;
import ru.fitsme.android.data.frameworks.sharedpreferences.AuthInfoStorage;
import ru.fitsme.android.data.repositories.ResourceRepository;
import ru.fitsme.android.data.repositories.SignInUpRepository;
import ru.fitsme.android.data.repositories.TextValidator;
import ru.fitsme.android.data.repositories.UserInfoRepository;
import ru.fitsme.android.domain.boundaries.IResourceRepository;
import ru.fitsme.android.domain.boundaries.ISignInUpRepository;
import ru.fitsme.android.domain.boundaries.ITextValidator;
import ru.fitsme.android.domain.boundaries.IUserInfoRepository;
import ru.fitsme.android.domain.interactors.auth.ISignInUpInteractor;
import ru.fitsme.android.domain.interactors.auth.SignInUpInteractor;
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
        }});
    }

    @NonNull
    @Named("serverBaseUrl")
    private String serverBaseUrl() {
        return "http://81.90.181.10:8080/customers/";
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
        return new OkHttpClient.Builder().build();
    }

    public <T> void inject(T object) {
        Toothpick.inject(object, appScope);
    }
}
