package ru.fitsme.android.domain.interactors.returns;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import java.util.List;

import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsItemRequest;
import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsPaymentRequest;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.returns.ReturnsOrder;
import ru.fitsme.android.domain.entities.returns.ReturnsOrderItem;
import ru.fitsme.android.domain.interactors.BaseInteractor;

public interface IReturnsInteractor extends BaseInteractor {

    LiveData<PagedList<ReturnsOrder>> getReturnsPagedListLiveData();
    LiveData<PagedList<Order>> getReturnOrdersPagedListLiveData();

    LiveData<Boolean> getReturnsIsEmpty();
    LiveData<Boolean> getReturnOrdersIsEmpty();

    ObservableField<String> getShowMessage();

    boolean itemIsInCart(int position);

    Single<ReturnsOrderItem> addItemsToReturn(List<ReturnsItemRequest> requestsList);
    Single<ReturnsOrderItem> addItemToReturn(ReturnsItemRequest request);

    Single<ReturnsOrderItem> changeReturnsPayment(ReturnsPaymentRequest request);

    Single<ReturnsOrder> getReturnById(int returnId);
}
