package com.sarmadali.chatmingle.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sarmadali.chatmingle.Adapters.ChatAdapter;
import com.sarmadali.chatmingle.Models.Users;
import com.sarmadali.chatmingle.R;
import com.sarmadali.chatmingle.databinding.FragmentChatsBinding;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {


    public ChatsFragment() {
        // Required empty public constructor
    }

     FragmentChatsBinding chatsBinding;
    ArrayList<Users> list = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        chatsBinding = FragmentChatsBinding.inflate(inflater, container, false);

        ChatAdapter chatAdapter = new ChatAdapter(list, getContext());
        chatsBinding.recyclerViewChat.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        chatsBinding.recyclerViewChat.setLayoutManager(layoutManager);

        firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseDatabase.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Users users = dataSnapshot.getValue(Users.class);
                    users.getUserId(dataSnapshot.getKey());

                    list.add(users);
                }

                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return chatsBinding.getRoot();
    }
}