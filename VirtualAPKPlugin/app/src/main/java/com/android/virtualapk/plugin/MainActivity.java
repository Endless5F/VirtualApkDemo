package com.android.virtualapk.plugin;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "已加载", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.plugin_activity_main);
    }
}
