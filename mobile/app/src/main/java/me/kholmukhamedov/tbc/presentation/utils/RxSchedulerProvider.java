package me.kholmukhamedov.tbc.presentation.utils;

import io.reactivex.CompletableTransformer;
import io.reactivex.SingleTransformer;

/**
 * Interface of RxJava scheduler provider
 */
public interface RxSchedulerProvider {

    /**
     * Get RxJava {@link io.reactivex.Single} source that observed on IO scheduler and subscribed on
     * main thread of Android scheduler
     *
     * @param <T> type of source's emission
     * @return observed and subscribed source
     */
    <T> SingleTransformer<T, T> getSingleFromIOToMainThread();

    /**
     * Get RxJava {@link io.reactivex.Completable} source that observed on IO scheduler and subscribed on
     * main thread of Android scheduler
     *
     * @return observed and subscribed source
     */
    CompletableTransformer getCompletableFromIOToMainThread();

}
