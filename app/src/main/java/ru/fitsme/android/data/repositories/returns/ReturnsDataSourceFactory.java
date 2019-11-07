package ru.fitsme.android.data.repositories.returns;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoaderNetworkChecker;
import ru.fitsme.android.domain.entities.returns.ReturnsItem;

public class ReturnsDataSourceFactory extends DataSource.Factory<Integer, ReturnsItem> {

    private final WebLoaderNetworkChecker webLoader;

    private MutableLiveData<ReturnsRepository> sourceLiveData = new MutableLiveData<>();
    private ReturnsRepository latestSource = null;

    @Inject
    ReturnsDataSourceFactory(WebLoaderNetworkChecker webLoader) {
        this.webLoader = webLoader;
    }

    @NotNull
    @Override
    public DataSource<Integer, ReturnsItem> create() {
        latestSource = new ReturnsRepository(webLoader);
        sourceLiveData.postValue(latestSource);
        return latestSource;
    }
}
