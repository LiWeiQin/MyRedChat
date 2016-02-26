package com.example.qinda.myredapplication.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qinda.myredapplication.R;
import com.example.qinda.myredapplication.service.Helper.AccessibiltyServiceHelper;
import com.example.qinda.myredapplication.utils.PackageUtils;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Button start_or_close_service;
    private Button openWeiChat;
    private TextView service_status;

    public final static String WECHAT_PACKAGENAME = "com.tencent.mm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start_or_close_service = (Button) findViewById(R.id.btn_start_or_close_services);
        openWeiChat = (Button) findViewById(R.id.btn_open_wetchat);
        service_status = (TextView) findViewById(R.id.tv_service_status);
        openWeiChat.setOnClickListener(this);
        start_or_close_service.setOnClickListener(this);
        refreshLayout();

    }

    private void refreshLayout() {
        boolean isRunningAccessiblityService = AccessibiltyServiceHelper.isAccessibilitySettingOn(this);
        service_status.setText(isRunningAccessiblityService ? R.string.activity_tv_text_start : R.string.activity_tv_text);
        service_status.setTextColor(isRunningAccessiblityService ? Color.BLACK : Color.RED);
        start_or_close_service.setText(isRunningAccessiblityService ? R.string.activity_btn_text_end : R.string.activity_btn_text_start);
        openWeiChat.setVisibility(PackageUtils.isAppInstalled(this, WECHAT_PACKAGENAME) ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onResume(){
        super.onResume();
        refreshLayout();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.微信
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_about) {
            AboutActivity.actionTo(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_open_wetchat:
                PackageUtils.openApp(this, WECHAT_PACKAGENAME);
                break;
            case R.id.btn_start_or_close_services:
                if (AccessibiltyServiceHelper.isAccessibilitySettingOn(this))
                    Toast.makeText(getApplicationContext(), R.string.close_accessibility_service_hint, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), R.string.open_accessibility_service_hint, Toast.LENGTH_SHORT).show();
                AccessibiltyServiceHelper.startService(this);
                break;
            default:
                break;
        }

    }
}



