package com.sarmadali.chatmingle.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sarmadali.chatmingle.Adapters.ChatAdapter;
import com.sarmadali.chatmingle.Models.Users;
import com.sarmadali.chatmingle.databinding.FragmentChatsBinding;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {


    public ChatsFragment() {
        // Required empty public constructor
    }

     FragmentChatsBinding chatsBinding;
    ArrayList<Users> list = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
//    FirebaseUser user;
    DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        chatsBinding = FragmentChatsBinding.inflate(inflater, container, false);

        ChatAdapter chatAdapter = new ChatAdapter(list, getContext());
        chatsBinding.recyclerViewChat.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        chatsBinding.recyclerViewChat.setLayoutManager(layoutManager);

        //database
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String currentID = user.getUid();

        reference = firebaseDatabase.getInstance().getReference().child("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                String currentUserId = firebaseAuth.getCurrentUser().getUid();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Users users = dataSnapshot.getValue(Users.class);
                    users.setUserId(dataSnapshot.getKey());


//                    Users users = new Users();

//                    Log.d("Current User ID","Current UserID: "+currentUserId);
//                    Log.d("User ID","UserID: "+users.getUserId());
                    String childKey = dataSnapshot.getKey();
//                    String childValue = dataSnapshot.getValue().toString();
//                    Log.w("Child Key & Child Value","Key: "+childKey+" Value"+childValue);

                    //to not add current user in the list
                    if (!currentUserId.equals(childKey)){
                        list.add(users);
                    }
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