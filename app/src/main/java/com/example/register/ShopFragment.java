package com.example.register;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.register.bean.User;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopFragment extends Fragment {
    private TextView assistance;
    private VideoView AG_video;
    private Context mContext;
    private ListView listView;
    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private LinkedList<Message> messageData = new LinkedList<Message>();
    private MessageAdapter messageAdapter = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        // Inflate the layout for this fragment
        mySQLiteOpenHelper = new MySQLiteOpenHelper(getContext());
        assistance = (TextView) view.findViewById(R.id.assistance);
        AG_video = (VideoView) view.findViewById(R.id.AG_video);
        listView = (ListView) view.findViewById(R.id.list_message);

        Intent intent = getActivity().getIntent();
        String user_name = intent.getStringExtra("user_name");
        ArrayList<User> users = mySQLiteOpenHelper.queryFromDbByName(user_name);
        int id = 0;
        for (int i = 0; i < users.size(); i++) {
            id = users.get(i).getId();
        }
        assistance.setText("感谢您惠顾");

//        File file = new File(Environment.getExternalStorageDirectory()+"/video_AGHZ.mp4");
        File file = new File(Environment.getExternalStorageDirectory()+"/我眼中的成都多姿多彩_高清 1080P.mp4");
//        File file = new File("/mnt/sdcard/我眼中的成都多姿多彩_高清 1080P.mp4");
        if (file.exists()){
            AG_video.setVideoPath(file.getAbsolutePath());
        }else {
            Toast.makeText(getActivity(),"不存在视频",Toast.LENGTH_LONG).show();
        }

        MediaController mc = new MediaController(getActivity());
        AG_video.setMediaController(mc);
        AG_video.requestFocus();
        AG_video.start();
        AG_video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(getActivity(),"播放完毕",Toast.LENGTH_LONG).show();
            }
        });

        mContext = getContext();
        Message m1 = new Message("spark","¥ 2499.00", R.mipmap.spark);
        Message m2 = new Message("mini","¥ 2699.00", R.mipmap.mini);
        Message m3 = new Message("mavic2pro","¥ 9888.00", R.mipmap.mavic2pro);
        Message m4 = new Message("mavic air","¥ 4999.90", R.mipmap.air);
        Message m5 = new Message("精灵 Phantom 4 Pro V2.0","¥ 9888.00", R.mipmap.p4pv2);
        Message m6 = new Message("悟Inspire 2","¥ 32888.00", R.mipmap.inspire2);
        Message m7 = new Message("更多精彩周边礼品尽请期待","", R.mipmap.rebot);
        messageData.add(m1);
        messageData.add(m2);
        messageData.add(m3);
        messageData.add(m4);
        messageData.add(m5);
        messageData.add(m6);
        messageData.add(m7);

        messageAdapter = new MessageAdapter(mContext,(LinkedList<Message>)messageData);
        listView.setAdapter(messageAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setIcon(messageData.get(i).getMessageIcon());
                alertDialog.setTitle(messageData.get(i).getMessageName());
                alertDialog.setMessage(messageData.get(i).getMessagePrice());
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();

            }
        });
        return view;
    }
    private void initData(){
        SharedPreferences spf = getActivity().getSharedPreferences("spfRecord",Context.MODE_PRIVATE);
        boolean isRemember = spf.getBoolean("isRemember", false);
        boolean isLoad = spf.getBoolean("isLoad", false);
        String nameInData = spf.getString("name", "");
        String passwordInData = spf.getString("password", "");

    }
}