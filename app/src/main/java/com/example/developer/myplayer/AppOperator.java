package com.example.developer.myplayer;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.example.developer.myplayer.LocalVideo.VideoInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Developer on 2017/3/2.
 */

public class AppOperator {
    private static ExecutorService mExecutorService;

    public static Executor getExecutor() {
        if (mExecutorService == null) {
            synchronized ((AppOperator.class)) {
                if (mExecutorService == null) {
                    mExecutorService = Executors.newFixedThreadPool(5);
                }
            }
        }
        return mExecutorService;
    }

    public static void runOnThread(Runnable runnable) {
        getExecutor().execute(runnable);
    }

    public static void setLocalFileSP(Context context, List<VideoInfo> list) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LocalFileList", Context.MODE_PRIVATE); //私有数据
        String listJson = new Gson().toJson(list);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString("LocalFiles", listJson);
        Log.d("listJson", listJson);
        editor.apply();//提交修改
    }

    public static List<VideoInfo> getLocalFileSP(Context context) {
        SharedPreferences share = context.getSharedPreferences("LocalFileList", Context.MODE_PRIVATE);
        String localFiles = share.getString("LocalFiles", "");
        if (!TextUtils.isEmpty(localFiles)) {
            List<VideoInfo> videoList = new Gson().fromJson(localFiles, new TypeToken<List<VideoInfo>>() {
            }.getType());
            return videoList;
        } else {
            return null;
        }

    }
}
