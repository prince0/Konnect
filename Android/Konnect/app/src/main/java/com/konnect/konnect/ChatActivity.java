package com.konnect.konnect;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        ChatFragment chatFragment = new ChatFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout,chatFragment).commit();

    }
}
