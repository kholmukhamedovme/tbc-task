package me.kholmukhamedov.tbc.data.repository;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import me.kholmukhamedov.tbc.data.remote.ApiService;
import me.kholmukhamedov.tbc.domain.Repository;
import me.kholmukhamedov.tbc.models.converter.AbstractConverter;
import me.kholmukhamedov.tbc.models.data.UserBean;
import me.kholmukhamedov.tbc.models.domain.User;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Repository for fetching content from TBC
 */
public final class RepositoryImpl implements Repository {

    private final ApiService mApiService;
    private final AbstractConverter<UserBean, User> mConverter;

    /**
     * Injects dependencies and initializes converter
     *
     * @param apiService HTTP API for TBC
     * @param converter  converter from data layer bean to domain layer entity
     */
    public RepositoryImpl(@NonNull ApiService apiService,
                          @NonNull AbstractConverter<UserBean, User> converter) {
        mApiService = checkNotNull(apiService, "ApiService is required");
        mConverter = checkNotNull(converter, "AbstractConverter<UserBean, User> is required");
    }

    /**
     * Get list of users from data layer bean and convert them to domain layer entities
     *
     * @return Single RxJava source with list of {@link User} entity
     */
    @Override
    public Single<List<User>> getUsers() {
        return mApiService.getUsers()
                .map(mConverter::convertList);
    }

    /**
     * Update user
     *
     * @param id     user ID
     * @param status {@code true} for activated, {@code false} for deactivated
     * @return Completable RxJava source
     */
    @Override
    public Completable updateUser(int id, boolean status) {
        return mApiService.updateUser(id, status);
    }

}
