package com.example.rohan.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.sendButton) Button sendButton;
    @BindView(R.id.message) EditText message;
    @BindView(R.id.textView) TextView textView;

    private String room_name,user_name,temp_key,chat_message,chat_user;
    private DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        room_name = getIntent().getExtras().get("room_name").toString();
        user_name = getIntent().getExtras().get("user_name").toString();
        setTitle("Room - "+room_name);

        root = FirebaseDatabase.getInstance().getReference().child(room_name);
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void append_chat_conversation(DataSnapshot dataSnapshot)
    {
        Iterator i = dataSnapshot.getChildren().iterator();
        while(i.hasNext())
        {
            chat_message = (String) ((DataSnapshot)i.next()).getValue();
            chat_user = (String) ((DataSnapshot)i.next()).getValue();

            textView.append(chat_user + ":" + chat_message+"\nhim.chopr");
        }
    }

    @OnClick(R.id.sendButton)
    public void clicekd()
    {
        Map<String,Object> map = new HashMap<String,Object>();
        temp_key = root.push().getKey();
        root.updateChildren(map);

        DatabaseReference message_root = root.child(temp_key);

        Map<String,Object> map2 = new HashMap<String,Object>();
        map2.put("name",user_name);
        map2.put("msg",message.getText().toString());

        message_root.updateChildren(map2);

    }
}
