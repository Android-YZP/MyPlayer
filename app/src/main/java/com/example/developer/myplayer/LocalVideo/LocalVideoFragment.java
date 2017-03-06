package com.example.developer.myplayer.LocalVideo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.developer.myplayer.MainActivity;
import com.example.developer.myplayer.PlayerActivity;
import com.example.developer.myplayer.R;

/**
 * Created by Developer on 2017/3/2.
 */

public class LocalVideoFragment extends Fragment {

    private ListView mLVLocal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local, null);
        mLVLocal = (ListView) view.findViewById(R.id.lv_local);
        TextView textView = new TextView(getContext());
        textView.setText("正在加载中.....");
        mLVLocal.setEmptyView(textView);
        if (MainActivity.mVideoList.size() > 0) {
            mLVLocal.setAdapter(new LocalAdapter(MainActivity.mVideoList, getContext()));
        }
        mLVLocal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoInfo videoInfo = MainActivity.mVideoList.get(position);
                Intent intent = new Intent(getContext(), PlayerActivity.class);
                intent.putExtra("name", videoInfo.getFileName());
                intent.putExtra("path", videoInfo.getFilePath());
                startActivity(intent);

            }
        });


        return view;
    }
}
