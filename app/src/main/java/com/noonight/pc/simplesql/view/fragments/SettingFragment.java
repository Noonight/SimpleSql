package com.noonight.pc.simplesql.view.fragments;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.noonight.pc.simplesql.R;

/**
 * Created by PC on 5/29/2017.
 */

public class SettingFragment extends PreferenceFragment {

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
