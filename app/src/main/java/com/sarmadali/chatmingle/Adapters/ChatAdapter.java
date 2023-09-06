package com.sarmadali.chatmingle.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sarmadali.chatmingle.Models.Users;
import com.sarmadali.chatmingle.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatAdapter  extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    ArrayList<Users> list;
    Context context;

    //constructor
    public ChatAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    //we will inflate the layout in this method
    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_chatslayout, parent, false);
        return new ViewHolder(view);
    }

    // we will set the values of image and text here, in this method
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Users usersModel = list.get(position);
        //setting data
        Picasso.get().load(usersModel.getProfilePic()).placeholder(R.drawable.useravatar)
                .into(holder.profileImage);
        holder.userName.setText(usersModel.getUserName());
//        holder.lastMessage.setText(usersModel.getMsg());
//        holder.timeText.setText(usersModel.getTime());


    }

    // size of items or size of recyclerView
    @Override
    public int getItemCount() {
        return list.size();
    }


    //view holder class
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView profileImage;
        TextView userName;

        TextView lastMessage;

        TextView timeText;

        //constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.profilepicChatting);
            userName = itemView.findViewById(R.id.textUserName);
            lastMessage = itemView.findViewById(R.id.textLastMessage);
            timeText = itemView.findViewById(R.id.textTime);

        }
    }

}
