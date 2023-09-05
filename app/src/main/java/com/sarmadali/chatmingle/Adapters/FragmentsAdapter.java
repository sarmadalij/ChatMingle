package com.sarmadali.chatmingle.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.sarmadali.chatmingle.Fragments.CallsFragment;
import com.sarmadali.chatmingle.Fragments.ChatsFragment;
import com.sarmadali.chatmingle.Fragments.StatusFragment;

public class FragmentsAdapter extends FragmentStateAdapter {

    public FragmentsAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {

            case 0:
                return new ChatsFragment();
            case 1:
                return new StatusFragment();
            case 2:
                return new CallsFragment();

            default:
                return new ChatsFragment();

        }
    }

    @Override
    public int getItemCount () {
        return 3;
    }

    //IT IS TO CHANGE THE TITLE ON TABLAYOUT ACCORDING TO THE FRAGMENT
    //BUT IT DO NOT WORKS FOR ME. I HAVE DONE IN MAIN ACTIVITY IN DIFFERENT WAY.
//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        String title = null;
//
//        if (position==0){
//            title="CHATS";
//        } else if (position==1) {
//            title="STATUS";
//        } else if (position==2) {
//            title="CALLS";
//        }
//        return title;
//    }
}
