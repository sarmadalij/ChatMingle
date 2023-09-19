package com.sarmadali.chatmingle;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sarmadali.chatmingle.Adapters.MessageAdapter;
import com.sarmadali.chatmingle.Models.MessagesModel;
import com.sarmadali.chatmingle.databinding.ActivityChattingBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChattingActivity extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbarchatting;
    ActivityChattingBinding chattingBinding;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chattingBinding = ActivityChattingBinding.inflate(getLayoutInflater());
        setContentView(chattingBinding.getRoot());

        toolbarchatting = findViewById(R.id.toolbarchatting);
        setSupportActionBar(toolbarchatting);

        //3 vertical dots in toolbar color change
        toolbarchatting.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        //database
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        //getting data from the intent
        final String senderId = firebaseAuth.getUid();
        String receiverId = getIntent().getStringExtra("userId");
        String userName = getIntent().getStringExtra("userName");
        String userProfile = getIntent().getStringExtra("userPic");

        //setting data on Views
        Picasso.get().load(userProfile).placeholder(R.drawable.useravatar)
                .into(chattingBinding.toolbarchatting.profilepicChatting);

        chattingBinding.toolbarchatting.userNameChatting.setText(userName);

        //back arrow - to move back to chats profile (Fragment)
        chattingBinding.toolbarchatting.chattingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ChattingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        final ArrayList<MessagesModel> messagesModels = new ArrayList<>();
        final MessageAdapter messageAdapter = new MessageAdapter(messagesModels, this, receiverId);

        chattingBinding.chatRecyclerView.setAdapter(messageAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        chattingBinding.chatRecyclerView.setLayoutManager(layoutManager);

        final String senderRoom = senderId + receiverId;
        final String receiverRoom = receiverId + senderId;

        //to display messages on recyclerView
        firebaseDatabase.getReference().child("Chats")
                .child(senderRoom)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                //to clear recyclerview so it should not show all
                                //message when one msg is send
                                messagesModels.clear();

                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                    //data to be showed
                                    MessagesModel model = dataSnapshot1.getValue(MessagesModel.class);
                                    model.setMsgId(dataSnapshot1.getKey());

                                    messagesModels.add(model);
                                }
                                messageAdapter.notifyDataSetChanged();

                                //to scroll recyclerview to the latest message
                                //when message activity is called
                                chattingBinding.chatRecyclerView.scrollToPosition(messagesModels.size() - 1);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }

                        });
        //on send imageView button for messages
        chattingBinding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save message to database
                String msg = chattingBinding.writeMessage.getText().toString();
                final MessagesModel mModel = new MessagesModel(senderId, msg);
                mModel.setTimeStamp(new Date().getTime());

                chattingBinding.writeMessage.setText("");

                firebaseDatabase.getReference().child("Chats")
                        .child(senderRoom)
                        .push()
                        .setValue(mModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                firebaseDatabase.getReference().child("Chats")
                                        .child(receiverRoom)
                                        .push()
                                        .setValue(mModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });
                            }
                        });

            }
        });

        //to handle the scrolling in message recyclerView
        chattingBinding.writeMessage.getViewTreeObserver().addOnGlobalLayoutListener
                (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                chattingBinding.writeMessage.getWindowVisibleDisplayFrame(r);
                int screenHeight = chattingBinding.writeMessage.getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;

                // If keypadHeight is positive, the keyboard is open
                if (keypadHeight > screenHeight * 0.15) {
                    // Keyboard is open, Scroll to the latest message
                    chattingBinding.chatRecyclerView.scrollToPosition(messagesModels.size() - 1);
                }
            }
        });
    }

    //to inflate the menu && toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chattingmenu, menu);
        return true;
    }

    //action on menu item clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.viewcontact) {
            Toast.makeText(this, "This is View Contact", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.report) {
            Toast.makeText(this, "This is Report", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.block) {
            Toast.makeText(this, "This is block", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.search) {
            Toast.makeText(this, "This is search", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.mutenotifications) {
            Toast.makeText(this, "This is mute notification", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("ChattingActivity", "Back pressed in ChattingActivity");
        Intent intent = new Intent(ChattingActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}