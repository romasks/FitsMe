package ru.fitsme.android.data.frameworks.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.fitsme.android.utils.ReturnsOrderStep;

@Singleton
public class ReturnsStorage implements IReturnsStorage {

    private static final String PREF_NAME = "returnsPref";
    private static final String STEP_KEY = "stepKey";
    private static final String RETURN_ID_KEY = "returnIdKey";
    private static final String ORDER_ID_KEY = "orderIdKey";

    private SharedPreferences sharedPreferences;

    @Inject
    ReturnsStorage(Context appContext) {
        sharedPreferences = appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public ReturnsOrderStep getReturnOrderStep() {
        int step = sharedPreferences.getInt(STEP_KEY, 0);
        return ReturnsOrderStep.getByStepNumber(step);
    }

    @Override
    public int getReturnId() {
        return sharedPreferences.getInt(RETURN_ID_KEY, 0);
    }

    @Override
    public int getReturnOrderId() {
        return sharedPreferences.getInt(ORDER_ID_KEY, 0);
    }

    @Override
    public void setReturnOrderStep(ReturnsOrderStep step) {
        sharedPreferences.edit()
                .putInt(STEP_KEY, step.getStep())
                .apply();
    }

    @Override
    public void setReturnId(int returnId) {
        sharedPreferences.edit()
                .putInt(RETURN_ID_KEY, returnId)
                .apply();
    }

    @Override
    public void setReturnOrderId(int orderId) {
        sharedPreferences.edit()
                .putInt(ORDER_ID_KEY, orderId)
                .apply();
    }
}
