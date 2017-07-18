package com.reference.apublic.pluginresource;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by byang059 on 7/17/17.
 */

public class PluginResource extends Resources {

    /**
     * Create a new Resources object on top of an existing set of assets in an
     * AssetManager.
     *
     * @param assets  Previously created AssetManager.
     * @param metrics Current display metrics to consider when
     *                selecting/computing resource values.
     * @param config  Desired device configuration to consider when
     * @deprecated Resources should not be constructed by apps.
     * See {@link Context#createConfigurationContext(Configuration)}.
     */
    public PluginResource(AssetManager assets, DisplayMetrics metrics, Configuration config) {
        super(assets, metrics, config);
    }


    public static PluginResource getPluginResource(String pluginPath, Resources res)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
                   InstantiationException, IllegalAccessException {
        return new PluginResource(getPluginAssetManager(pluginPath), res.getDisplayMetrics(), res
                .getConfiguration());
    }

    public static AssetManager getPluginAssetManager(String pluginPath)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
                   InstantiationException, InvocationTargetException {
        Class<AssetManager> assetManagerClass = AssetManager.class;
        Method addAssetPath = assetManagerClass.getDeclaredMethod("addAssetPath", String.class);
        AssetManager assetManager = assetManagerClass.newInstance();
        addAssetPath.invoke(assetManager, pluginPath);
        return assetManager;
    }

}
