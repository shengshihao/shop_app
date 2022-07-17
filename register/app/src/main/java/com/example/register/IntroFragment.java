package com.example.register;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IntroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntroFragment extends Fragment {
    private Context mContext;
    private ListView listView;
    private ImageView imageView;
    private TextView textView;
    private LinkedList<IntroName> mData = new LinkedList<IntroName>();
    private IntroNameAdapter introNameAdapter = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IntroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IntroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IntroFragment newInstance(String param1, String param2) {
        IntroFragment fragment = new IntroFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro, container, false);
        imageView = (ImageView) view.findViewById(R.id.gymPicture);
        imageView.setBackgroundResource(R.mipmap.inspire);
        textView = (TextView) view.findViewById(R.id.gymIntro);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        listView = (ListView) view.findViewById(R.id.list_item);

        mContext = getContext();
        IntroName g1 = new IntroName("spark");
        IntroName g2 = new IntroName("mini");
        IntroName g3 = new IntroName("mavic2pro");
        IntroName g4 = new IntroName("御Mavic Pro");
        IntroName g5 = new IntroName("精灵 Phantom 4 Pro V2.0");
        IntroName g6 = new IntroName("悟Inspire 2");


        mData.add(g1);
        mData.add(g2);
        mData.add(g3);
        mData.add(g4);
        mData.add(g5);
        mData.add(g6);
//        mData.add(g7);
//        mData.add(g8);
//        mData.add(g9);
//        mData.add(g10);
//        mData.add(g11);
//        mData.add(g12);
//        mData.add(g13);
//        mData.add(g14);
//        mData.add(g15);
//        mData.add(g16);
//        mData.add(g17);
//        mData.add(g18);
//        mData.add(g19);
//        mData.add(g20);
//        mData.add(g21);
//        mData.add(g22);

        introNameAdapter = new IntroNameAdapter(mContext,(LinkedList<IntroName>)mData);
        listView.setAdapter(introNameAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    imageView.setBackgroundResource(R.mipmap.spark);
                    textView.setText("晓是大疆在2017年推出的一款超小型自拍无人机。\n" +
                            "\n" +
                            "Spark 采用了不可折叠的设计，总体宽度约为 14 厘米，长度大概是 16 厘米，和一台 iPad mini 差不多大，轴距大概是 200mm，飞机主体和手掌差不多大小。由于体积太小，相应的电池、电机和各种传感器模块也会小一点，飞行和拍摄性也受到一定限制。");
                }
                if (i == 1){
                    imageView.setBackgroundResource(R.mipmap.mini);
                    textView.setText("御Mavic MINI\n" +
                            "\n" +
                            "刚刚发布的御Mavic Mini仅有249克，可以说是针对很多国家无人机管理法律法规量身定制的一款机器。由于重量没有超过250克，放飞御Mavic Mini无须进行繁琐的申报、审批流程，更加适合个人出游自拍。");
                }
                if (i == 2){
                    imageView.setBackgroundResource(R.mipmap.mavic2pro);
                    textView.setText("御Mavic2提供专业版、变焦版两种不同的版本，差异在于使用的镜头。\n" +
                            "\n" +
                            "专业版采用的是哈苏相机，配备1英寸 2000 万像素 CMOS 传感器，有效感光面积约为“御”Mavic Pro 相机传感器的 4 倍，最大感光度从 3200 提升至 12800，具备更出色的暗光性能、动态范围以及色彩还原能力。");
                }
                if (i == 3){
                    imageView.setBackgroundResource(R.mipmap.air);
                    textView.setText("御Mavic 2 Air\n" +
                            "\n" +
                            "mavic 2 Air也是一款主打“轻便”特征的无人机，它的整体重量（430g）比Mavic 2轻了50%，但比Mavic Mini重180g左右，相对来说 Mavic 2 Air在性能上也比Mini更强大一些。\n" +
                            "\n" +
                            "两者同样是配备1200万像素相机，Air支持拍摄分辨率高达 8192×4096 的球形全景照片，支持以 100Mbps 码流录制 4K/30fps 视频，支持1080p/120fps慢动作拍摄，HDR 模式支持手动调节强度范围。");
                }
                if (i == 4){
                    imageView.setBackgroundResource(R.mipmap.p4pv2);
                    textView.setText("精灵 Phantom 4 Pro V2.0\n" +
                            "\n" +
                            "搭载1英寸 2000万像素Exmor R CMOS传感器，支持拍摄4K高清视频，提供30分钟的续航，30米前后视避障，5向环境感知。最远控制距离达到7公里，在极速模式下每小时飞行速度高达72公里");
                }
                if (i == 5){
                    imageView.setBackgroundResource(R.mipmap.inspire2);
                    textView.setText("悟Inspire 2在产品上已经划分到专业级领域，作为全新的专业影视航拍平台，适合高端电影、视频创作者使用。\n" +
                            "\n" +
                            "悟Inspire 2采用DJI 大疆最新的影像处理系统CineCore 2.1，CineCore 2.1内置于机身头部，可通过快拆接口支持多款相机接入，目前支持禅思X7、X5S、X4S三款相机。\n" +
                            "\n" +
                            "默认搭配DJI 大疆全新推出的禅思X7相机，最高可录制6K CinemaDNG / RAW和5.2K Apple ProRes视频；支持JPEG+DNG 10张连拍，以及单张2400万像素每秒20帧的DNG无限连拍。\n" +
                            "\n" +
                            "录制过程中支持使用标准 CinemaDNG 以及 Apple ProRes 格式，还可直接使用影视行业常用的后期处理软件进行处理。支持exFAT*或FAT32标准文件系统，无需额外软件，通过 DJI CINESSD 读卡器就能够直接读出素材。");
                }

//                if (i >= 7){
//                    imageView.setBackgroundResource(R.mipmap.img_1);
//                    textView.setText("        正在更新当中，尽请期待！");
//                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setIcon(R.mipmap.ic_launcher);
                alertDialog.setTitle("温馨提示");
                alertDialog.setMessage("谢谢您的点赞");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
                return true;
            }
        });

        return view;
    }
}