package ru.fitsme.android.data.repositories.returns;

import javax.inject.Inject;

import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.retrofit.WebLoaderNetworkChecker;
import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsItemRequest;
import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsPaymentRequest;
import ru.fitsme.android.data.frameworks.sharedpreferences.IReturnsStorage;
import ru.fitsme.android.domain.boundaries.retunrs.IReturnsActionRepository;
import ru.fitsme.android.domain.entities.returns.ReturnsOrder;
import ru.fitsme.android.domain.entities.returns.ReturnsOrderItem;
import ru.fitsme.android.utils.ReturnsOrderStep;

public class ReturnsActionRepository implements IReturnsActionRepository {

    private final WebLoaderNetworkChecker webLoader;
    private final IReturnsStorage storage;

    @Inject
    ReturnsActionRepository(WebLoaderNetworkChecker webLoader, IReturnsStorage storage) {
        this.webLoader = webLoader;
        this.storage = storage;
    }

    @Override
    public Single<ReturnsOrderItem> addItemToReturn(ReturnsItemRequest request) {
        return Single.create(emitter ->
                webLoader.addItemToReturn(request)
                        .subscribe(response -> emitter.onSuccess(response.getResponse()),
                                emitter::onError));
    }

    @Override
    public Single<ReturnsOrderItem> changeReturnsPayment(ReturnsPaymentRequest request) {
        return Single.create(emitter ->
                webLoader.changeReturnsPayment(request)
                        .subscribe(response -> emitter.onSuccess(response.getResponse()),
                                emitter::onError));
    }

    @Override
    public Single<ReturnsOrder> getReturnById(int returnId) {
        return Single.create(emitter ->
                webLoader.getReturnById(returnId)
                        .subscribe(response -> emitter.onSuccess(response.getResponse()),
                                emitter::onError));
    }

    @Override
    public ReturnsOrderStep getReturnOrderStep() {
        return storage.getReturnOrderStep();
    }

    @Override
    public int getReturnOrderId() {
        return storage.getReturnOrderId();
    }

    @Override
    public int getReturnId() {
        return storage.getReturnId();
    }

    @Override
    public void setReturnOrderStep(ReturnsOrderStep step) {
        if (getReturnOrderStep().before(step))
            storage.setReturnOrderStep(step);
    }

    @Override
    public void setReturnOrderId(int orderId) {
        storage.setReturnOrderId(orderId);
    }

    @Override
    public void setReturnId(int returnId) {
        storage.setReturnId(returnId);
    }
}
