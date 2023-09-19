package com.sarmadali.chatmingle.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.sarmadali.chatmingle.Models.MessagesModel;
import com.sarmadali.chatmingle.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter{

    ArrayList<MessagesModel> messagesList;
    Context context;

    String recId;
    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;
    //constructor
    public MessageAdapter(ArrayList<MessagesModel> messagesModels, Context context) {
        this.messagesList = messagesModels;
        this.context = context;
    }

    //constructor to delete the message

    public MessageAdapter(ArrayList<MessagesModel> messagesList, Context context, String recId) {
        this.messagesList = messagesList;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the layout as per condition sender or receiver
        if (viewType == SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender_layout,
                    parent, false);
            return new SenderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_receiver_layout,
                    parent, false);
            return new ReceiverViewHolder(view);
        }

    }

    //method for viewholder

    @Override
    public int getItemViewType(int position) {

        if (messagesList.get(position).getMessageUserId()
                .equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        } else {
            return RECEIVER_VIEW_TYPE;
        }
//        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessagesModel mModel = messagesList.get(position);

        // to delete the message
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle("Delete Message")
                        .setMessage("Are you sure? You want to delete this message")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                String senderRoom = FirebaseAuth.getInstance().getUid() + recId;

                                database.getReference().child("Chats").child(senderRoom)
                                        .child(mModel.getMsgId())
                                        .removeValue();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

                return false;
            }
        });
        if (holder.getClass() == SenderViewHolder.class){

            ((SenderViewHolder)holder).senderMsg.setText(mModel.getMessageText());
            //for time
            // Assuming mModel.getTimeStamp() returns a timestamp in milliseconds
            long timestampInMillis = mModel.getTimeStamp();
            // Create a SimpleDateFormat instance to format the timestamp
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());

            // Format the timestamp
            String formattedTime = sdf.format(new Date(timestampInMillis));

            ((SenderViewHolder)holder).senderTime.setText(formattedTime);


        } else {

            ((ReceiverViewHolder)holder).receiverMsg.setText(mModel.getMessageText());
            //for time
            long timestampInMillis = mModel.getTimeStamp();
            // Create a SimpleDateFormat instance to format the timestamp
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());

            // Format the timestamp
            String formattedTime = sdf.format(new Date(timestampInMillis));

            ((ReceiverViewHolder)holder).receiverTime.setText(formattedTime);
        }

    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    //view holder for receiver layout
    public class ReceiverViewHolder extends RecyclerView.ViewHolder {

        TextView receiverMsg, receiverTime;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            receiverMsg = itemView.findViewById(R.id.receiverText);
            receiverTime = itemView.findViewById(R.id.receiverTimeText);
        }
    }

    //view holder for sender layout
    public class SenderViewHolder extends RecyclerView.ViewHolder {

        TextView senderMsg, senderTime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMsg = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);
        }
    }
}
