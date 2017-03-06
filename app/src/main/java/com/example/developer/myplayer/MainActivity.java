package com.example.developer.myplayer;

import android.content.Context;
import android.graphics.Color;
import android.icu.util.VersionInfo;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.developer.myplayer.LocalVideo.VideoInfo;
import com.example.developer.myplayer.ext.titles.ScaleTransitionPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String[] mTitle = new String[]{"本地视频", "积液视频"};
    private List<String> mDataList = Arrays.asList(mTitle);
    private ViewPager mViewPager;
    public static List<VideoInfo> mVideoList = new ArrayList<>();// 视频信息集合
    private MyPagerAdapter myPagerAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    AppOperator.setLocalFileSP(MainActivity.this, mVideoList);
                    mViewPager.setAdapter(myPagerAdapter);
                    Log.d("视频文件列表:", mVideoList.size()+"");
                    break;
                default:
                    break;
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        FragmentManager fm = getSupportFragmentManager();
        myPagerAdapter = new MyPagerAdapter(fm);
        mViewPager.setAdapter(myPagerAdapter);
        initMagicIndicator6();

        //列表缓存在本地
//        if (AppOperator.getLocalFileSP(MainActivity.this) != null) {
//            mVideoList = AppOperator.getLocalFileSP(MainActivity.this);
//            mViewPager.setAdapter(myPagerAdapter);
//            Log.d("视频文件列表2:", mVideoList.get(0).getFileName());
//        } else {
//            AppOperator.runOnThread(new Runnable() {//子线程扫描文件
//                @Override
//                public void run() {
//                    mVideoList.clear();
//                    scanVideoFile(mVideoList, Environment.getExternalStorageDirectory());
//                    mHandler.sendEmptyMessage(1);
//                }
//            });
//        }

        AppOperator.runOnThread(new Runnable() {//子线程扫描文件
                @Override
                public void run() {
                    mVideoList.clear();
                    scanVideoFile(mVideoList, Environment.getExternalStorageDirectory());
                    mHandler.sendEmptyMessage(1);
                }
            });

    }

    //子线程扫描视频文件
    private void scanVideoFile(final List<VideoInfo> list, final File file) {

        file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName();
                int i = name.indexOf('.');
                if (i != -1) {
                    name = name.substring(i);
                    if (name.equalsIgnoreCase(".mp4")
                            || name.equalsIgnoreCase(".3gp")
                            || name.equalsIgnoreCase(".wmv")
                            || name.equalsIgnoreCase(".ts")
                            || name.equalsIgnoreCase(".rmvb")
                            || name.equalsIgnoreCase(".mov")
                            || name.equalsIgnoreCase(".m4v")
                            || name.equalsIgnoreCase(".avi")
                            || name.equalsIgnoreCase(".m3u8")
                            || name.equalsIgnoreCase(".3gpp")
                            || name.equalsIgnoreCase(".3gpp2")
                            || name.equalsIgnoreCase(".mkv")
                            || name.equalsIgnoreCase(".flv")
                            || name.equalsIgnoreCase(".divx")
                            || name.equalsIgnoreCase(".f4v")
                            || name.equalsIgnoreCase(".rm")
                            || name.equalsIgnoreCase(".asf")
                            || name.equalsIgnoreCase(".ram")
                            || name.equalsIgnoreCase(".mpg")
                            || name.equalsIgnoreCase(".v8")
                            || name.equalsIgnoreCase(".swf")
                            || name.equalsIgnoreCase(".m2v")
                            || name.equalsIgnoreCase(".asx")
                            || name.equalsIgnoreCase(".ra")
                            || name.equalsIgnoreCase(".ndivx")
                            || name.equalsIgnoreCase(".xvid")) {
                        VideoInfo vi = new VideoInfo();
                        vi.setFileName(pathname.getName());
                        vi.setFilePath(pathname.getAbsolutePath());
                        list.add(vi);
//                        Log.d("list","1");
                        return true;
                    }
                } else if (pathname.isDirectory()) {//如果是文件夹的话
                    scanVideoFile(list, pathname);
                }
//                Log.d("list",pathname.getAbsolutePath());
                return false;
            }
        });
    }


    private void initMagicIndicator6() {
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator1);
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                BezierPagerIndicator indicator = new BezierPagerIndicator(context);
                indicator.setColors(Color.parseColor("#ff4a42"), Color.parseColor("#fcde64"), Color.parseColor("#73e8f4"), Color.parseColor("#76b0ff"), Color.parseColor("#c683fe"));
                return null;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }
}
