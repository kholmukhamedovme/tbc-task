package me.kholmukhamedov.tbc.domain;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import me.kholmukhamedov.tbc.models.domain.User;

/**
 * Repository for fetching content from TBC
 */
public interface Repository {

    /**
     * Get list of users in domain layer entities
     *
     * @return Single RxJava source with list of {@link User} entity
     */
    Single<List<User>> getUsers();

    /**
     * Update user
     *
     * @param id     user ID
     * @param status {@code true} for activated, {@code false} for deactivated
     * @return Completable RxJava source
     */
    Completable updateUser(int id,
                           boolean status);

}
