package me.kholmukhamedov.tbc.presentation.view;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.kholmukhamedov.tbc.R;
import me.kholmukhamedov.tbc.models.presentation.UserModel;

import static android.view.View.OnClickListener;
import static dagger.internal.Preconditions.checkNotNull;

public final class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private final Picasso mPicasso;
    private final List<UserModel> mItems;
    private final OnClickListener mOnClickListener;

    /**
     * Injects dependencies and initializes list of items
     *
     * @param picasso         Picasso
     * @param onClickListener listener on item click event
     */
    MainAdapter(@NonNull Picasso picasso,
                @NonNull OnClickListener onClickListener) {
        mPicasso = checkNotNull(picasso, "Picasso is required");
        mOnClickListener = checkNotNull(onClickListener, "RecyclerView.OnClickListener is required");

        mItems = new ArrayList<>();
    }

    /**
     * Creates view holder and sets listener on click event
     *
     * @param parent   {@inheritDoc}
     * @param viewType {@inheritDoc}
     * @return {@inheritDoc}
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);

        v.setOnClickListener(mOnClickListener);

        return new ViewHolder(v);
    }

    /**
     * Puts image into view holder
     *
     * @param holder   {@inheritDoc}
     * @param position {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {
        UserModel user = mItems.get(position);

        if (user.getHasPhoto()) {
            String imageUrl = mItems.get(position).getPhotoUrl();

            mPicasso.load(imageUrl)
                    .placeholder(R.drawable.ic_photo_placeholder_24dp)
                    .into(holder.mImageView);

            holder.mImageView.setVisibility(View.VISIBLE);
        } else {
            holder.mImageView.setVisibility(View.GONE);
        }

        holder.mNameTextView.setText(user.getName());
        holder.mStatusTextView.setText(user.getStatus() ? "ACTIVATED" : "DEACTIVATED");
        holder.mStatusTextView.setTextColor(user.getStatus() ? Color.GREEN : Color.RED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /**
     * Replaces current list of items with new one
     *
     * @param items new list of items
     */
    public void updateItems(List<UserModel> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * Changes status of user in user list
     *
     * @param id     user ID
     * @param status {@code true} for activated, {@code false} for deactivated
     */
    public void statusChanged(int id, boolean status) {
        for (UserModel user : mItems) {
            if (user.getId() == id) {
                user.setStatus(status);
                notifyDataSetChanged();
                break;
            }
        }
    }

    /**
     * Get item model by index
     *
     * @param position index
     * @return item model
     */
    public UserModel getItem(int position) {
        return mItems.get(position);
    }

    /**
     * View holder of item
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImageView;
        private final TextView mNameTextView;
        private final TextView mStatusTextView;

        ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mNameTextView = itemView.findViewById(R.id.name_text_view);
            mStatusTextView = itemView.findViewById(R.id.status_text_view);
        }

    }

}
