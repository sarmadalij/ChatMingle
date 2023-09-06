package com.sarmadali.chatmingle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sarmadali.chatmingle.databinding.ActivityChattingBinding;

public class ChattingActivity extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbarchatting;
    ActivityChattingBinding chattingBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chattingBinding = ActivityChattingBinding.inflate(getLayoutInflater());
        setContentView(chattingBinding.getRoot());

        toolbarchatting = findViewById(R.id.toolbarchatting);
        setSupportActionBar(toolbarchatting);

        //3 vertical dots in toolbar color change
        toolbarchatting.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

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
}