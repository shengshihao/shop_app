package com.example.register;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

public class IntroNameAdapter extends BaseAdapter {
    private Context mContext;
    private LinkedList<IntroName> mData;

    public IntroNameAdapter(Context mContext, LinkedList mData) {
        this.mContext = mContext;
        this.mData = mData;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //从布局文件movie_item.xml生成视图
        convertView = LayoutInflater.from(mContext).inflate(R.layout.gym_item,null);
        //从movie_item.xml布局文件中获取条目的组件
        TextView messageName = (TextView) convertView.findViewById(R.id.gymName);
        //对各个组件设置参数
        messageName.setText(mData.get(position).getGymName());

        //返回生成的视图
        return convertView;
    }
}
