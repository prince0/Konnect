package com.example.rohan.helloworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;

/**
 * Created by rohan on 2/3/2018.
 */

public class CustomAdapter extends ArrayAdapter<userMessage>
{

    private SharedPreferences sharedPreferences = getContext().getSharedPreferences("values",Context.MODE_PRIVATE);
    private String userName = sharedPreferences.getString("userName","");
    private String lang = sharedPreferences.getString("lang","");

    public CustomAdapter(@NonNull Context context, ArrayList<userMessage> messages)
    {
        super(context,R.layout.item_message_recieved,messages);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.item_message_recieved,parent,false);
        userMessage msg = getItem(position);

        TextView text_message_name = (TextView) customView.findViewById(R.id.text_message_name);
        TextView text_message_body_reciever = (TextView) customView.findViewById(R.id.text_message_body_reciever);
        TextView text_message_body_sender = (TextView) customView.findViewById(R.id.text_message_body_sender);
        TextView text_view_me = (TextView) customView.findViewById(R.id.text_view_me);
        TextView timestampReceiver = (TextView) customView.findViewById(R.id.text_view_timestamp_receiver);
        TextView timestampSender = (TextView) customView.findViewById(R.id.text_view_timestamp_sender);


        if(msg.getUserName().equals(userName))
        {
            text_message_body_reciever.setVisibility(View.INVISIBLE);
            text_message_name.setVisibility(View.INVISIBLE);
            text_message_body_sender.setText(msg.getChatMessage());
            volleyTest(msg.getChatMessage(),lang,text_message_body_sender);
            timestampReceiver.setVisibility(View.INVISIBLE);
            timestampSender.setVisibility(View.VISIBLE);
            timestampSender.setText(msg.getTime());
        }
        else
        {
            text_view_me.setVisibility(View.INVISIBLE);
            text_message_body_sender.setVisibility(View.INVISIBLE);
            text_message_name.setText(msg.getUserName());
            text_message_body_reciever.setText(msg.getChatMessage());
            volleyTest(msg.getChatMessage(),lang,text_message_body_reciever);
            timestampSender.setVisibility(View.INVISIBLE);
            timestampReceiver.setVisibility(View.VISIBLE);
            timestampReceiver.setText(msg.getTime());
        }

        return customView;
    }

    public void volleyTest(String query, String lang, final TextView view)
    {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        //String lang = "hin";
        //String query = "I am himanshu";

        query = query.replaceAll(" ", "%20");
        String url ="https://www.googleapis.com/language/translate/v2?key=AIzaSyB3wlifx5sEmZtPn-Yh6n1Qu6qjMvKSI3g&source=en&target="+lang+"&q="+query;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject obj = new JSONObject(response);
                            String text = obj.toString();
                            JSONObject temp = (JSONObject) obj.getJSONObject("data").getJSONArray("translations").get(0);
                            String result = temp.getString("translatedText").toString();
                            view.setText(result);
                        } catch(Exception e) {}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //perform operation here after getting error
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
