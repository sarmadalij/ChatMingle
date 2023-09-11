package com.sarmadali.chatmingle.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sarmadali.chatmingle.ChattingActivity;
import com.sarmadali.chatmingle.Models.Users;
import com.sarmadali.chatmingle.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

        //get last message from the user chat from database
        //and dispaly on chat lists
        FirebaseDatabase.getInstance().getReference().child("Chats")
                .child(FirebaseAuth.getInstance().getUid()+usersModel.getUserId())
                .orderByChild("timeStamp")
                .limitToLast(1)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                //color for text
                                                int customColor = Color.argb(255, 0, 255, 0);

                                                if (snapshot.hasChildren()){
                                                    for (DataSnapshot snapshot1 : snapshot.getChildren()
                                                         ) {
                                                        //setting last message
                                                        holder.lastMessage.setText(snapshot1.child("messageText")
                                                                .getValue().toString());
                                                        holder.lastMessage.setTextColor(customColor);

                                                        //setting time
                                                        long timestampInMillis = (long) snapshot1.child("timeStamp").getValue();
                                                        // Create a SimpleDateFormat instance to format the timestamp
                                                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());

                                                        // Format the timestamp
                                                        String formattedTime = sdf.format(new Date(timestampInMillis));
                                                        holder.timeText.setText(formattedTime);

                                                    }
                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
//        holder.lastMessage.setText(usersModel.getMsg());
//        holder.timeText.setText(usersModel.getTime());

        //send data to chatting activity from chats profiles
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ChattingActivity.class);

                intent.putExtra("userId", usersModel.getUserId());
                intent.putExtra("userPic", usersModel.getProfilePic());
                intent.putExtra("userName", usersModel.getUserName());

                context.startActivity(intent);
                // Finish the current activity
                ((Activity) context).finish();
            }
        });

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
