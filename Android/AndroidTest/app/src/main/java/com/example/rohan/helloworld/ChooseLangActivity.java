package com.example.rohan.helloworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseLangActivity extends AppCompatActivity {

    @BindView(R.id.langListView)
    ListView langListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_lang);
        ButterKnife.bind(this);

        String[] langs = {"English","Hindi"};
        ListAdapter listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,langs);
        langListView.setAdapter(listAdapter);

        langListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                    {
                        String lang = String.valueOf(adapterView.getItemAtPosition(i));
                        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("values", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("ln",lang);
                        editor.commit();
                        //Toast.makeText(ChooseLangActivity.this,lang,Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
        );
    }

}
