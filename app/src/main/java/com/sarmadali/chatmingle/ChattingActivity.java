package com.sarmadali.chatmingle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.sarmadali.chatmingle.databinding.ActivityChattingBinding;
import com.squareup.picasso.Picasso;

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
        String senderId = firebaseAuth.getUid();
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
        Toast.makeText(this, "Back pressed in ChattingActivity", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ChattingActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}