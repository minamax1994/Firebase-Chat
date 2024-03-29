package com.example.chatapp;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {


    private List<User> users;
    private Clicklistener clicklistener;
    int flag;

    public UsersAdapter(int flag, List<User> users, Clicklistener clicklistener) {
        this.flag = flag;
        this.users = users;
        this.clicklistener = clicklistener;
    }


    @androidx.annotation.NonNull
    @Override
    public ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder mViewHolder = new ViewHolder(view);

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull final ViewHolder holder, int position) {
        User user = users.get(position);

        holder.userName.setText(user.getName());

        Glide.with(holder.userImage.getContext())
                .load(user.getUrlPhoto())
                .placeholder(R.drawable.user)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.userImage.setImageDrawable(resource);
                        return false;
                    }
                })
                .into(holder.userImage);

    }


    @Override
    public int getItemCount() {
        return users.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        CircleImageView userImage;
        TextView userName;

        public ViewHolder(@androidx.annotation.NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.user_image);
            userName = itemView.findViewById(R.id.user_name);

            if (flag == 1) {
                itemView.findViewById(R.id.last_message).setVisibility(View.GONE);
                itemView.findViewById(R.id.send_forward).setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.send_forward).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clicklistener.onItemClicked(getAdapterPosition());
                    }
                });
            } else if (flag == 0) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clicklistener.onItemClicked(getAdapterPosition());
                    }
                });
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
