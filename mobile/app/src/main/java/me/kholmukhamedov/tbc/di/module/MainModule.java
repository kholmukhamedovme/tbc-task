package me.kholmukhamedov.tbc.di.module;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import me.kholmukhamedov.tbc.data.remote.ApiService;
import me.kholmukhamedov.tbc.data.repository.RepositoryImpl;
import me.kholmukhamedov.tbc.di.scope.MainScope;
import me.kholmukhamedov.tbc.domain.Interactor;
import me.kholmukhamedov.tbc.domain.Repository;
import me.kholmukhamedov.tbc.models.converter.AbstractConverter;
import me.kholmukhamedov.tbc.models.converter.DataToDomainConverter;
import me.kholmukhamedov.tbc.models.converter.DomainToPresentationConverter;
import me.kholmukhamedov.tbc.models.data.UserBean;
import me.kholmukhamedov.tbc.models.domain.User;
import me.kholmukhamedov.tbc.models.presentation.UserModel;
import me.kholmukhamedov.tbc.presentation.presenter.MainPresenter;
import me.kholmukhamedov.tbc.presentation.utils.RxSchedulerProvider;
import retrofit2.Retrofit;

@Module
public final class MainModule {

    //region Converters
    @MainScope
    @Provides
    AbstractConverter<UserBean, User> provideDataToDomainConverter() {
        return new DataToDomainConverter();
    }

    @MainScope
    @Provides
    AbstractConverter<User, UserModel> provideDomainToDataConverter() {
        return new DomainToPresentationConverter();
    }
    //endregion

    //region Data layer
    @MainScope
    @Provides
    ApiService provideApiService(@NonNull Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @MainScope
    @Provides
    Repository provideRepository(@NonNull ApiService apiService,
                                 @NonNull AbstractConverter<UserBean, User> converter) {
        return new RepositoryImpl(apiService, converter);
    }
    //endregion

    //region Domain layer
    @MainScope
    @Provides
    Interactor provideInteractor(@NonNull Repository repository) {
        return new Interactor(repository);
    }
    //endregion

    //region Presentation layer
    @MainScope
    @Provides
    MainPresenter provideMainPresenter(@NonNull Interactor interactor,
                                       @NonNull AbstractConverter<User, UserModel> converter,
                                       @NonNull RxSchedulerProvider rxSchedulerProvider) {
        return new MainPresenter(interactor, converter, rxSchedulerProvider);
    }
    //endregion

}
