package me.kholmukhamedov.tbc.domain;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import me.kholmukhamedov.tbc.models.domain.User;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Interactor for fetching content from TBC
 */
public final class Interactor {

    private final Repository mRepository;

    /**
     * Inject dependencies
     *
     * @param repository repository for fetching content from TBC
     */
    public Interactor(@NonNull Repository repository) {
        mRepository = checkNotNull(repository, "Repository is required");
    }

    /**
     * Request list of users in domain layer entities
     *
     * @return Single RxJava source with list of {@link User} entity
     */
    public Single<List<User>> requestUsers() {
        return mRepository.getUsers();
    }

    /**
     * Update user
     *
     * @param id     user ID
     * @param status {@code true} for activated, {@code false} for deactivated
     * @return Completable RxJava source
     */
    public Completable updateUser(int id, boolean status) {
        return mRepository.updateUser(id, status);
    }

}
