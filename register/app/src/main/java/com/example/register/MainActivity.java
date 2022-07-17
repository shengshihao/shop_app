package com.example.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.register.bean.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public TextView tv1_load,tv2_load;
    public Button bu1_load,bu2_load;
    public EditText edit1_load,edit2_load;
    private CheckBox cb1_load,cb2_load;
    private  MySQLiteOpenHelper mySQLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

        cb2_load.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if (isCheck){
                    cb1_load.setChecked(true);
                }
            }
        });
        cb1_load.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if (!isCheck){
                    cb2_load.setChecked(false);
                }
            }
        });

        bu1_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edit1_load.getText().toString().trim();
                String password = edit2_load.getText().toString().trim();
                if (!name.isEmpty() && !password.isEmpty()){
                    ArrayList<User> users = new ArrayList<>();
                    ArrayList<String> strings = new ArrayList<>();
                    int count = 1;
                    users = mySQLiteOpenHelper.getAllData();
                    for (int i = 0; i < users.size(); i++) {
                        User user = users.get(i);
                        strings.add(user.getName());
                    }
                    for (int i = 0; i < users.size(); i++) {
                        User user = users.get(i);
                        if (name.equals(user.getName()) && password.equals(user.getPassword()) && count <= strings.size()){
                            if (cb1_load.isChecked()){
                                SharedPreferences spf = getSharedPreferences("spfRecord",MODE_PRIVATE);
                                SharedPreferences.Editor edit = spf.edit();
                                edit.putString("name",name);
                                edit.putString("password",password);
                                edit.putBoolean("isRemember",true);
                                if (cb2_load.isChecked()){
                                    edit.putBoolean("isLoad",true);
                                }else {
                                    edit.putBoolean("isLoad",false);
                                }
                                edit.apply();
                            }else {
                                SharedPreferences spf = getSharedPreferences("spfRecord",MODE_PRIVATE);
                                SharedPreferences.Editor edit = spf.edit();
                                edit.putBoolean("isRemember",false);
                                edit.apply();
                            }
                            Intent intent = new Intent(MainActivity.this,UserActivity.class);
                            intent.putExtra("user_name",edit1_load.getText().toString());
                            intent.putExtra("fName",edit1_load.getText().toString());
                            intent.putExtra("user_gender",user.getGender());
                            intent.putExtra("school","");
                            intent.putExtra("birthTime","");
                            intent.putExtra("city_text","");
                            intent.putExtra("edit","");
                            startActivity(intent);
                            MainActivity.this.finish();
                            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        }
                        if ((name.equals(user.getName()) && !password.equals(user.getPassword())) || (!name.equals(user.getName()) && password.equals(user.getPassword()))){
                            Toast.makeText(MainActivity.this, "请输入正确的用户名或密码", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(MainActivity.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bu2_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,InsertActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }
    private void initView(){
        tv1_load = (TextView) findViewById(R.id.tv1_load);
        tv2_load = (TextView) findViewById(R.id.tv2_load);
        edit1_load = (EditText) findViewById(R.id.edit1_load);
        edit2_load = (EditText) findViewById(R.id.edit2_load);
        bu1_load = (Button) findViewById(R.id.bu1_load);
        bu2_load = (Button) findViewById(R.id.bu2_load);
        cb1_load = (CheckBox) findViewById(R.id.cb1_load);
        cb2_load = (CheckBox) findViewById(R.id.cb2_load);
        mySQLiteOpenHelper = new MySQLiteOpenHelper(this);
    }

    private void initData(){
        SharedPreferences spf = getSharedPreferences("spfRecord",MODE_PRIVATE);
        boolean isRemember = spf.getBoolean("isRemember", false);
        boolean isLoad = spf.getBoolean("isLoad", false);
        String nameInData = spf.getString("name", "");
        String passwordInData = spf.getString("password", "");
        if (isLoad){
            Intent intent = new Intent(MainActivity.this,UserActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
        if (isRemember){
            edit1_load.setText(nameInData);
            edit2_load.setText(passwordInData);
            cb1_load.setChecked(true);
        }
    }
}