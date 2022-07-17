package com.example.register;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class MessageAdapter extends BaseAdapter {
    private Context messageContext;
    private LinkedList<Message> messageData;

    public MessageAdapter(Context messageContext, LinkedList messageData) {
        this.messageContext = messageContext;
        this.messageData = messageData;
    }

    @Override
    public int getCount() {
        return messageData.size();
    }

    @Override
    public Object getItem(int i) {
        return messageData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //从布局文件movie_item.xml生成视图
        convertView = LayoutInflater.from(messageContext).inflate(R.layout.message_item,null);
        //从movie_item.xml布局文件中获取条目的组件
        ImageView image_message = (ImageView) convertView.findViewById(R.id.image_message);
        //对各个组件设置参数
        image_message.setBackgroundResource(messageData.get(position).getMessageIcon());
        //从movie_item.xml布局文件中获取条目的组件
        TextView name_message = (TextView) convertView.findViewById(R.id.message_name);
        //对各个组件设置参数
        name_message.setText(messageData.get(position).getMessageName());
        //从movie_item.xml布局文件中获取条目的组件
        TextView price_message = (TextView) convertView.findViewById(R.id.price);
        //对各个组件设置参数
        price_message.setText(messageData.get(position).getMessagePrice());

        //返回生成的视图
        return convertView;
    }
}
