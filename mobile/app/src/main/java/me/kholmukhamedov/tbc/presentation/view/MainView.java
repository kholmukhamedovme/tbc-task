package me.kholmukhamedov.tbc.presentation.view;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import me.kholmukhamedov.tbc.models.presentation.UserModel;

/**
 * Interface for activity/fragment to implement in order to interact with presenter
 */
public interface MainView extends MvpView {

    /**
     * Reaction on loading list of users
     *
     * @param users list of users in presentation layer models
     */
    @StateStrategyType(AddToEndSingleStrategy.class)
    void onItemsLoaded(List<UserModel> users);

    /**
     * Reaction on updating user
     *
     * @param id     user ID
     * @param status {@code true} for activated, {@code false} for deactivated
     */
    void statusChanged(int id, boolean status);

    /**
     * Reaction to error event
     *
     * @param message error message
     */
    @StateStrategyType(SkipStrategy.class)
    void showError(@StringRes int message);

}
