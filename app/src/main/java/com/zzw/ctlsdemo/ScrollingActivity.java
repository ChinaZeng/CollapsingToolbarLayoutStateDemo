package com.zzw.ctlsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class ScrollingActivity extends AppCompatActivity {

    private static final String TAG = "ScrollingActivity";

    private AppBarLayout app_bar;
    private int verticalOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        app_bar = (AppBarLayout) findViewById(R.id.app_bar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ScrollingActivity.this,ScrollingBActivity.class);
                startActivity(intent);
            }
        });

        final PtrClassicFrameLayout ptrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return verticalOffset >= 0 && PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrameLayout.refreshComplete();
                    }
                }, 2000);
            }
        });

        app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.e(TAG, "verticalOffset:" + verticalOffset);
                Log.e(TAG, "getTotalScrollRange():" + app_bar.getTotalScrollRange());
                ScrollingActivity.this.verticalOffset = verticalOffset;
            }
        });

        Log.e(TAG, "getTotalScrollRange():" + app_bar.getTotalScrollRange());
    }


}
