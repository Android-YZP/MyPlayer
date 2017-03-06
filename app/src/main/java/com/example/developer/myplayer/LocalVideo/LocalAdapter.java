package com.example.developer.myplayer.LocalVideo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.developer.myplayer.R;

import java.util.List;

/**
 * Created by Developer on 2017/3/2.
 */

public class LocalAdapter extends BaseAdapter {
    private List<VideoInfo> mDataLists;
    private Context mContext;
    private ImageView mIVPic;
    private TextView mTVName;

    public LocalAdapter(List<VideoInfo> mDataLists, Context context) {
        this.mDataLists = mDataLists;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mDataLists.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext, R.layout.item_local, null);
        mIVPic = (ImageView) convertView.findViewById(R.id.iv_pic);
        mTVName = (TextView) convertView.findViewById(R.id.tv_name);
        mIVPic.setImageResource(R.drawable.test);
        mTVName.setText(mDataLists.get(position).getFileName());
        return convertView;
    }
}
