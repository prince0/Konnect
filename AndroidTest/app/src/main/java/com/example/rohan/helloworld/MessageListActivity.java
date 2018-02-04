package com.example.rohan.helloworld;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageListActivity extends AppCompatActivity {

    @BindView(R.id.listViewMessages)
    ListView listViewMessages;
    @BindView(R.id.edittext_chatbox)
    EditText edittext_chatbox;
    @BindView(R.id.button_chatbox_send)
    Button button_chatbox_send;

    private SharedPreferences sharedPreferences;
    private String name,lang;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    private ArrayList<userMessage> messages;
    private CustomAdapter custAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        ButterKnife.bind(this);
        messages = new ArrayList<userMessage>();

        sharedPreferences = this.getSharedPreferences("values", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("userName","");
        lang = sharedPreferences.getString("lang","");

        //messages.add(new userMessage("rohan","HI"));
        //messages.add(new userMessage("rout","Bye!!"));
        custAdapter = new CustomAdapter(this,messages);
        listViewMessages.setAdapter(custAdapter);
        //request_user_name();
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
        //messages.removeAll(messages);
        String chat_message="",chat_user="",chat_time = "";
        while(i.hasNext())
        {
                chat_message = (String) ((DataSnapshot)i.next()).getValue();
                chat_user = (String) ((DataSnapshot)i.next()).getValue();
                chat_time = (String) ((DataSnapshot)i.next()).getValue();
                messages.add(new userMessage(chat_user,chat_message,chat_time));
        }
        custAdapter.notifyDataSetChanged();
        scrollMyListViewToBottom();

    }

    private void scrollMyListViewToBottom() {
        listViewMessages.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                listViewMessages.setSelection(custAdapter.getCount() - 1);
            }
        });
    }


    @OnClick(R.id.button_chatbox_send)
    public void sendmessage()
    {
        String temp = edittext_chatbox.getText().toString().replaceAll(" ","");
        if(temp.length()<=0){
            return;
        }
        Map<String,Object> map = new HashMap<String,Object>();
        String temp_key = root.push().getKey();
        root.updateChildren(map);

        DatabaseReference message_root = root.child(temp_key);

        volleyTest(edittext_chatbox.getText().toString(),lang,message_root);
        edittext_chatbox.setText("");
    }


    public void volleyTest(String query, final String lang, final DatabaseReference message_root)
    {

        //String lang = "hin";
        //String query = "I am himanshu";
        DateFormat df = new SimpleDateFormat("MM/dd h:mm a");
        final String date = df.format(Calendar.getInstance().getTime());

        if(!lang.equals("en")) {
            final RequestQueue queue = Volley.newRequestQueue(this);
            query = query.replaceAll(" ", "%20");
            String url = "https://www.googleapis.com/language/translate/v2?key=AIzaSyB3wlifx5sEmZtPn-Yh6n1Qu6qjMvKSI3g&source=" + lang + "&target=en&q=" + query;

            // Request a string response from the provided URL.
            final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                String result;

                                    JSONObject obj = new JSONObject(response);
                                    String text = obj.toString();
                                    JSONObject temp = (JSONObject) obj.getJSONObject("data").getJSONArray("translations").get(0);
                                    result = temp.getString("translatedText").toString();
                                Map<String, Object> map2 = new HashMap<String, Object>();
                                map2.put("name", name);
                                map2.put("msg", result);
                                map2.put("time",date);
                                message_root.updateChildren(map2);
                            } catch (Exception e) {
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //perform operation here after getting error
                }
            });

            queue.add(stringRequest);
        }else{
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("name", name);
            map2.put("msg", query);
            map2.put("time",date);
            message_root.updateChildren(map2);
        }
    }
}
