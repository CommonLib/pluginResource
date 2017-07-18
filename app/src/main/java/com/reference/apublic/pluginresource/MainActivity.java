package com.reference.apublic.pluginresource;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File apkFile = new File(externalStorageDirectory, "plugin.apk");
        if(apkFile.exists()){
            try {
                PluginResource pluginResource = PluginResource
                        .getPluginResource(apkFile.getAbsolutePath(), getResources());
                String dexOutputDir = getApplicationInfo().dataDir;
                DexClassLoader dexClassLoader = new DexClassLoader(apkFile.getAbsolutePath(),
                        dexOutputDir, null, getClassLoader());

                Class<?> res = dexClassLoader.loadClass("net.ossrs.yasea.R$string");
                Field authSettings = res.getDeclaredField("app_name");
                int resId = (int) authSettings.get(res);
                String string = pluginResource.getString(resId);
                Toast.makeText(this, string, Toast.LENGTH_LONG).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(MainActivity.this, "not find plugin file", Toast.LENGTH_SHORT).show();
        }

    }
}
