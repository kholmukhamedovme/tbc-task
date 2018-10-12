package me.kholmukhamedov.tbc.presentation.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import me.kholmukhamedov.tbc.R;
import me.kholmukhamedov.tbc.di.App;
import me.kholmukhamedov.tbc.models.presentation.UserModel;
import me.kholmukhamedov.tbc.presentation.presenter.MainPresenter;

/**
 * Main activity of the application
 */
public final class MainActivity extends MvpAppCompatActivity implements MainView {

    @Inject
    Picasso mPicasso;
    @Inject
    @InjectPresenter
    MainPresenter mPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mNoInternetConnectionView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;

    /**
     * Provide presenter presented by Dagger to Moxy
     *
     * @return presenter as {@link MainPresenter}
     */
    @ProvidePresenter
    MainPresenter providePresenter() {
        return mPresenter;
    }

    /**
     * Inject dependencies, init and sets views, adapter, listeners and item fragment
     *
     * @param savedInstanceState bundle of data that has been saved while changing configuration
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getMainComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNoInternetConnectionView = findViewById(R.id.no_internet_connection_view);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = findViewById(R.id.recycler_view);

        mAdapter = new MainAdapter(mPicasso, this::onUserActivateClick);

        mSwipeRefreshLayout.setOnRefreshListener(this::onPullToRefresh);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        if (isConnectedToInternet()) {
            mPresenter.loadUsers();
        } else {
            shouldShowNoInternetConnection(true);
        }
    }

    //region MainView

    /**
     * {@inheritDoc}
     */
    @Override
    public void onItemsLoaded(List<UserModel> items) {
        shouldShowNoInternetConnection(false);
        mSwipeRefreshLayout.setRefreshing(false);
        ((MainAdapter) mAdapter).updateItems(items);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void statusChanged(int id, boolean status) {
        ((MainAdapter) mAdapter).statusChanged(id, status);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showError(@StringRes int message) {
        mSwipeRefreshLayout.setRefreshing(false);

        new AlertDialog.Builder(this)
                .setTitle(R.string.error_title)
                .setMessage(message)
                .create()
                .show();
    }

    //endregion

    /**
     * Sets visibility of view for no internet connection according to {@code should} value
     *
     * @param should {@code true} if should show, otherwise {@code false}
     */
    private void shouldShowNoInternetConnection(boolean should) {
        mNoInternetConnectionView.setVisibility(should ? View.VISIBLE : View.GONE);
        mRecyclerView.setVisibility(should ? View.GONE : View.VISIBLE);
    }

    /**
     * Action for pull to refresh listener
     */
    private void onPullToRefresh() {
        mPresenter.loadUsers();
    }

    /**
     * Action for item click listener
     *
     * @param v clicked view
     */
    private void onUserActivateClick(View v) {
        int position = mRecyclerView.getChildLayoutPosition(v);
        UserModel item = ((MainAdapter) mAdapter).getItem(position);
        mPresenter.updateUser(item.getId(), !item.getStatus());
    }

    /**
     * Checks whether connected to internet or not
     *
     * @return {@code true} if connected, otherwise {@code false}
     */
    private boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

}
