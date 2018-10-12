package me.kholmukhamedov.tbc.presentation.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.arellomobile.mvp.InjectViewState;

import java.io.IOException;
import java.net.SocketTimeoutException;

import me.kholmukhamedov.tbc.R;
import me.kholmukhamedov.tbc.domain.Interactor;
import me.kholmukhamedov.tbc.models.converter.AbstractConverter;
import me.kholmukhamedov.tbc.models.domain.User;
import me.kholmukhamedov.tbc.models.presentation.UserModel;
import me.kholmukhamedov.tbc.presentation.utils.BasePresenter;
import me.kholmukhamedov.tbc.presentation.utils.RxSchedulerProvider;
import me.kholmukhamedov.tbc.presentation.view.MainView;
import retrofit2.HttpException;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Presenter for {@link me.kholmukhamedov.tbc.presentation.view.MainActivity}
 */
@InjectViewState
public final class MainPresenter extends BasePresenter<MainView> {

    private static final String TAG = "MainPresenter";

    private final Interactor mInteractor;
    private final AbstractConverter<User, UserModel> mConverter;
    private final RxSchedulerProvider mRxSchedulerProvider;

    /**
     * Inject dependencies and initializes converter
     *
     * @param interactor          interactor for fetching content from TBC
     * @param converter           converter from domain layer entity to presentation layer model
     * @param rxSchedulerProvider RxJava scheduler provider
     */
    public MainPresenter(@NonNull Interactor interactor,
                         @NonNull AbstractConverter<User, UserModel> converter,
                         @NonNull RxSchedulerProvider rxSchedulerProvider) {
        mInteractor = checkNotNull(interactor, "Interactor is required");
        mConverter = checkNotNull(converter, "AbstractConverter<User, UserModel> is required");
        mRxSchedulerProvider = checkNotNull(rxSchedulerProvider, "RxSchedulerProvider is required");
    }

    /**
     * Request to load users
     */
    public void loadUsers() {
        getCompositeDisposable().add(
                mInteractor.requestUsers()
                        .compose(mRxSchedulerProvider.getSingleFromIOToMainThread())
                        .map(mConverter::convertList)
                        .subscribe(
                                users -> getViewState().onItemsLoaded(users),
                                this::handleError
                        )
        );
    }

    /**
     * Request to update user
     *
     * @param id     user ID
     * @param status {@code true} for activated, {@code false} for deactivated
     */
    public void updateUser(int id, boolean status) {
        getCompositeDisposable().add(
                mInteractor.updateUser(id, status)
                        .compose(mRxSchedulerProvider.getCompletableFromIOToMainThread())
                        .subscribe(
                                () -> getViewState().statusChanged(id, status),
                                this::handleError
                        )
        );
    }

    /**
     * Handles error according to type
     *
     * @param throwable error
     */
    private void handleError(Throwable throwable) {
        @StringRes
        int messageResource;

        if (throwable instanceof HttpException) {
            messageResource = R.string.error_http_response_code;
        } else if (throwable instanceof SocketTimeoutException) {
            messageResource = R.string.error_timeout;
        } else if (throwable instanceof IOException) {
            messageResource = R.string.error_io_parse;
        } else {
            messageResource = R.string.error_unknown;
        }

        getViewState().showError(messageResource);
    }

}
