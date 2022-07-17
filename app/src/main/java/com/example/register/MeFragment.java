package com.example.register;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.register.bean.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment {
    private ImageView user_image;
    private Button bu_exit,bu_edit;
    private TextView fName,user_gender,user_city,user_name,user_edit,user_birthTime,user_city_text,user_school;
    private  MySQLiteOpenHelper mySQLiteOpenHelper;
    private String gender,image64;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
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
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        // Inflate the layout for this fragment
        user_image = (ImageView) view.findViewById(R.id.user_image);
        fName = (TextView) view.findViewById(R.id.user_fName);
        user_gender = (TextView) view.findViewById(R.id.user_gender);
        user_city = (TextView) view.findViewById(R.id.user_city);
        user_name = (TextView) view.findViewById(R.id.user_name);
        user_edit = (TextView) view.findViewById(R.id.user_edit);
        user_birthTime = (TextView) view.findViewById(R.id.user_birthTime);
        user_city_text = (TextView) view.findViewById(R.id.user_city_text);
        user_school = (TextView) view.findViewById(R.id.user_school);
        bu_exit = (Button) view.findViewById(R.id.bu1_exit);
        bu_edit = (Button) view.findViewById(R.id.bu2_edit);
        SharedPreferences spfModify = getActivity().getSharedPreferences("spfModify",Context.MODE_PRIVATE);
        image64 = spfModify.getString("image_64","");
        if (image64!=""){
            user_image.setImageBitmap(imageUtil.base64ToImage(image64));
        }
//        user_image.setImageBitmap(imageUtil.base64ToImage(image64));
        mySQLiteOpenHelper = new MySQLiteOpenHelper(getContext());
        Intent intent = getActivity().getIntent();
        fName.setText(intent.getStringExtra("fName"));
        user_name.setText(intent.getStringExtra("user_name"));
        ArrayList<User> users = mySQLiteOpenHelper.queryFromDbByName(user_name.getText().toString().trim());
        String city = "";
        String fakeName = "";
        String birthTime = "";
        String school = "";
        String edit = "";
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            city = city + user.getCity();
            fakeName = fakeName + user.getFakeName();
            birthTime = birthTime + user.getBirthTime();
            school = school + user.getSchool();
            edit = edit + user.getEdit();
            gender = "性别：" + user.getGender();
        }
        user_gender.setText(gender);
        if (school.equals("null")){
            user_school.setText("还未编辑");
        }else {
            user_school.setText(school);
        }
        if (birthTime.equals("null")){
            user_birthTime.setText("还未编辑");
        }else {
            user_birthTime.setText(birthTime);
        }
        if (fakeName.equals("null")){
            fName.setText(user_name.getText().toString().trim());
        }else {
            fName.setText(fakeName);
        }
        if (city.equals("null")){
            user_city_text.setText("还未编辑");
            user_city.setText("城市：未选");
        }else {
            user_city.setText("城市："+city);
            user_city_text.setText(city);
        }
        if (edit.equals("null")){
            user_edit.setText("还未编辑");
        }else {
            user_edit.setText(edit);
        }
//        user_school.setText(school);
//        user_birthTime.setText(birthTime);
//        fName.setText(fakeName);
//        user_city.setText("城市："+city);
//        user_city_text.setText(city);

        bu_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "";
                Intent intent1 = new Intent(getActivity(),ModifyActivity.class);
                intent1.putExtra("modify_fName",fName.getText().toString().trim());
                intent1.putExtra("modify_name",user_name.getText().toString().trim());
                intent1.putExtra("city_text",user_city_text.getText().toString().trim());
                intent1.putExtra("edit",user_edit.getText().toString().trim());
                intent1.putExtra("birthTime",user_birthTime.getText().toString().trim());
                intent1.putExtra("school",user_school.getText().toString().trim());

                startActivity(intent1);
                getActivity().finish();
            }
        });
        bu_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences spf = getActivity().getSharedPreferences("spfRecord", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = spf.edit();
                edit.putBoolean("isLoad",false);
                edit.apply();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }
}