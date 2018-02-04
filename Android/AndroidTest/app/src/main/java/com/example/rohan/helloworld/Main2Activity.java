package com.example.rohan.helloworld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.nameText)
    EditText nameText;

    @BindView(R.id.langText)
    EditText langText;

    @BindView(R.id.submitButton)
    Button submitButton;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        sharedPreferences = this.getSharedPreferences("values",Context.MODE_PRIVATE);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.submitButton)
    public void submit()
    {
        String userName = nameText.getText().toString();
        String lang = langText.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName",userName);
        editor.putString("lang",lang);
        editor.commit();

        Intent intent = new Intent(this,MessageListActivity.class);
        startActivity(intent);
    }

}
