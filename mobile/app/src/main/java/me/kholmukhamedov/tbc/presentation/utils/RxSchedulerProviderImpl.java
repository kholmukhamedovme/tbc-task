package me.kholmukhamedov.tbc.presentation.utils;

import io.reactivex.CompletableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Default implementation of {@link RxSchedulerProvider}
 */
public final class RxSchedulerProviderImpl implements RxSchedulerProvider {

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> SingleTransformer<T, T> getSingleFromIOToMainThread() {
        return single -> single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableTransformer getCompletableFromIOToMainThread() {
        return completable -> completable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
