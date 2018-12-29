package com.example.forrestsu.microchat.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.forrestsu.microchat.fragment.ConversationFragment;
import com.example.forrestsu.microchat.fragment.ContactsFragment;
import com.example.forrestsu.microchat.fragment.MeFragment;


public class MyFragmentAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 3;
    private ConversationFragment conversationFragment = null;
    private ContactsFragment contactsFragment = null;
    private MeFragment meFragment = null;

    public MyFragmentAdapter (FragmentManager fm) {
        super(fm);
        conversationFragment = new ConversationFragment();
        contactsFragment = new ContactsFragment();
        meFragment = new MeFragment();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup vp, int position) {
        return super.instantiateItem(vp, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, @Nullable int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position){
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = conversationFragment;
                break;
            case 1:
                fragment = contactsFragment;
                break;
            case 2:
                fragment = meFragment;
                break;
        }
        return fragment;
    }


}
