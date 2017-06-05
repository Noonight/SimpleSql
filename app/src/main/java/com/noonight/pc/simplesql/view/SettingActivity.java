package com.noonight.pc.simplesql.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.noonight.pc.simplesql.R;
import com.noonight.pc.simplesql.view.fragments.SettingFragment;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_setting);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingFragment()).commit();
    }
}
