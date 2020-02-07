package com.android.virtualapk.host;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.didi.virtualapk.PluginManager;

import java.io.File;

public class MainActivity extends Activity {

    private static final int CODE_FOR_WRITE_PERMISSION = 11111;
    private static final String PLUGIN_PACKAGE_NAME = "com.android.virtualapk.plugin";
    private static final String PLUGIN_NAME = "com.android.virtualapk.plugin.MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //使用兼容库就无需判断系统版本
        int hasWriteStoragePermission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
            //拥有权限，执行操作
            loadPlugin();
        }else{
            //没有权限，向用户请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_FOR_WRITE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //通过requestCode来识别是否同一个请求
        if (requestCode == CODE_FOR_WRITE_PERMISSION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //用户同意，执行操作
                loadPlugin();
            }else{
                //用户不同意，向用户展示该权限作用
                Toast.makeText(this, "权限开启失败，无法加载插件", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadPlugin() {
        try {
            String pluginPath = Environment.getExternalStorageDirectory().getAbsolutePath().concat("/VirtualAPKPlugin.apk");
            File plugin = new File(pluginPath);
            if (!plugin.exists()) {
                Toast.makeText(this, "plugin is null", Toast.LENGTH_SHORT).show();
            }
            PluginManager.getInstance(this).loadPlugin(plugin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPlugin(View view) {
        if (PluginManager.getInstance(this).getLoadedPlugin(PLUGIN_PACKAGE_NAME) == null) {
            Toast.makeText(getApplicationContext(), "未加载插件", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(PLUGIN_PACKAGE_NAME, PLUGIN_NAME));
        startActivity(intent);
    }
}
