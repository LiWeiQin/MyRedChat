package com.example.qinda.myredapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import com.example.qinda.myredapplication.R;

/**
 * Created by Qinda on 2016/1/10.
 */
public class AboutActivity extends ActionBarActivity {


    public static void actionTo(Context context) {
        Intent i = new Intent(context, AboutActivity.class);
        context.startActivity(i);
    }

    private TextView about_me;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        about_me = (TextView) findViewById(R.id.about_applcationdemo);
        about_me.setAutoLinkMask(Linkify.ALL);
        about_me.setMovementMethod(LinkMovementMethod.getInstance());
        about_me.setText("http://www.google.cn");
    }
}
