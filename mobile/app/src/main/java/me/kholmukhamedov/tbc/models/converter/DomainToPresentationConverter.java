package me.kholmukhamedov.tbc.models.converter;

import android.support.annotation.NonNull;

import me.kholmukhamedov.tbc.models.domain.Item;
import me.kholmukhamedov.tbc.models.domain.User;
import me.kholmukhamedov.tbc.models.presentation.ItemModel;
import me.kholmukhamedov.tbc.models.presentation.UserModel;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Converter from data layer bean to domain layer entity
 */
public final class DomainToPresentationConverter extends AbstractConverter<User, UserModel> {

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public UserModel convert(@NonNull User user) {
        checkNotNull(user, "User must be non-null");

        return new UserModel(
                user.getId(),
                user.getName(),
                user.getHasPhoto(),
                user.getStatus()
        );
    }

}
