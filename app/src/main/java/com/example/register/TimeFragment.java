package com.example.register;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeFragment extends Fragment {
    private EditText inputTime;
    private Button getTime,startTime,stopTime;
    private TextView time,minute,hour,day;
    private long i = 0;
    private Timer timer = null;
    private TimerTask timerTask = null;
    private long l1 = System.currentTimeMillis();
    private long l2;
    private long second;
    private long m;
    private long h;
    private long d;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TimeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimeFragment newInstance(String param1, String param2) {
        TimeFragment fragment = new TimeFragment();
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
        View view = inflater.inflate(R.layout.fragment_time,container,false);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(2022,9,10);
        l2 = calendar.getTimeInMillis();
//        getTime = (Button) view.findViewById(R.id.gettime);
//        startTime = (Button) view.findViewById(R.id.starttime);
//        stopTime = (Button) view.findViewById(R.id.stoptime);
        time = (TextView) view.findViewById(R.id.time);
        minute = (TextView) view.findViewById(R.id.m);
        hour = (TextView) view.findViewById(R.id.h);
        day = (TextView) view.findViewById(R.id.day);
        second = (l2-l1)/1000%60;
        m = ((l2-l1) / 1000) / 60 % 60;
        h = (l2 - l1) / 1000 / 60 / 60 % 24;
        d = (l2-l1) / 1000 / 60 / 60 / 24;
        day.setText((l2-l1) / 1000 / 60 / 60 / 24 + "");
        if (h >= 0 && h <10){
            hour.setText("  0" + h + "");
        }else{
            hour.setText("  " + h + "");
        }
        if (m >= 0 && m <10){
            minute.setText("  0" + m + "");
        }else{
            minute.setText("  " + m + "");
        }
        if (second >= 0 && second <10){
            time.setText("  0" + second + "");
        }else{
            time.setText("  " + second + "");
        }
        long s = l2 - l1;
        startTime();
        return view;
    }
    private void computeTime(){
        second --;
        if (second < 0){
            m--;
            second = 59;
        }
        if (m < 0){
            h--;
            m = 59;
        }
        if (h < 0){
            d--;
            h = 23;
        }
        if (d < 0){
            d = 0;
            h = 0;
            m = 0;
            second = 0;
        }
    }

    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what == 1){
                computeTime();
                day.setText(d + "");
                if (h >= 0 && h <10){
                    hour.setText("  0" + h + "");
                }else{
                    hour.setText("  " + h + "");
                }
                if (m >= 0 && m <10){
                    minute.setText("  0" + m + "");
                }else{
                    minute.setText("  " + m + "");
                }
                if (second >= 0 && second <10){
                    time.setText("  0" + second + "");
                }else{
                    time.setText("  " + second + "");
                }
                if (second == 0 && m == 0 && h == 0 && d == 0){
                    timer.cancel();
                }
            }
            startTime();
        };
    };

    public void startTime(){
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = mHandler.obtainMessage();
                message.what = 1;
                mHandler.sendMessage(message);
            }
        };
        timer.schedule(timerTask,1000);
    }

    public void stopTime(){
        timer.cancel();
    }
}