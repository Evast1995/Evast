package com.hchen.walkworld.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by hjiang on 16-2-17.
 */
public class BaseActivity extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
    }
}
