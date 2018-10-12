package me.kholmukhamedov.tbc.models.converter;

import android.support.annotation.NonNull;

import me.kholmukhamedov.tbc.models.data.UserBean;
import me.kholmukhamedov.tbc.models.domain.User;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Converter from data layer bean to domain layer entity
 */
public final class DataToDomainConverter extends AbstractConverter<UserBean, User> {

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public User convert(@NonNull UserBean bean) {
        checkNotNull(bean, "Bean must be non-null");

        return new User(
                bean.getId(),
                bean.getName(),
                bean.getHasPhoto(),
                bean.getStatus()
        );
    }

}
