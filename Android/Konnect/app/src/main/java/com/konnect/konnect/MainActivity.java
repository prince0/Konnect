package com.konnect.konnect;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.sendbird.android.SendBird;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.edit_text_translate)
    EditText editTextTranslate;

    @BindView(R.id.text_view_translated)
    TextView translated;

    @BindView(R.id.button_translate)
    Button buttonTranslate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        SendBird.init("BB73E26D-78B0-459B-80F5-558D963ED9FB", getApplicationContext());
//
//
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Log.e("plp", "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.



    }
}
