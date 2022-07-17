package com.example.register;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.register.bean.User;

import java.util.ArrayList;

public class InsertActivity extends AppCompatActivity {
    public TextView tv1_register,tv2_register,tv3_register;
    public Button bu1_register,bu2_register;
    public RadioGroup radioGroup_register;
    public RadioButton rBu1_register,rBu2_register;
    public EditText edit1_register,edit2_register,edit3_register;
    private MySQLiteOpenHelper mySQLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        tv1_register = (TextView) findViewById(R.id.tv1_register);
        tv2_register = (TextView) findViewById(R.id.tv2_register);
        tv3_register = (TextView) findViewById(R.id.tv3_register);
        edit1_register = (EditText) findViewById(R.id.edit1_register);
        edit2_register = (EditText) findViewById(R.id.edit2_register);
        edit3_register = (EditText) findViewById(R.id.edit3_register);
        radioGroup_register = (RadioGroup) findViewById(R.id.radioGroup_register);
        rBu1_register = (RadioButton) findViewById(R.id.rBu1_register);
        rBu2_register = (RadioButton) findViewById(R.id.rBu2_register);
        bu1_register = (Button) findViewById(R.id.bu1_register);
        bu2_register = (Button) findViewById(R.id.bu2_register);

        mySQLiteOpenHelper = new MySQLiteOpenHelper(this);

        bu1_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int flat = 1;
                ArrayList<User> userList = new ArrayList<>();
                userList = mySQLiteOpenHelper.getAllData();
                for (int i = 0; i < userList.size(); i++) {
                    User u = userList.get(i);
                    if (edit1_register.getText().toString().trim().equals(u.getName())) {
                        flat = 0;
                    }
                }
                if (edit1_register.getText().toString().equals("")){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(InsertActivity.this);
                    alertDialog.setIcon(R.mipmap.ic_launcher);
                    alertDialog.setTitle("温馨提示");
                    alertDialog.setMessage("您还未输入用户名！");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog.show();
                }else if (edit2_register.getText().toString().equals("")){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(InsertActivity.this);
                    alertDialog.setIcon(R.mipmap.ic_launcher);
                    alertDialog.setTitle("温馨提示");
                    alertDialog.setMessage("您还未输入密码！");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog.show();
                }else if (!edit2_register.getText().toString().equals(edit3_register.getText().toString())){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(InsertActivity.this);
                    alertDialog.setIcon(R.mipmap.ic_launcher);
                    alertDialog.setTitle("温馨提示");
                    alertDialog.setMessage("两次密码不一致！");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog.show();
                }else if (!rBu1_register.isChecked() && !rBu2_register.isChecked()){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(InsertActivity.this);
                    alertDialog.setIcon(R.mipmap.ic_launcher);
                    alertDialog.setTitle("温馨提示");
                    alertDialog.setMessage("您还未选择性别！");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog.show();
                }else if (flat == 0){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(InsertActivity.this);
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle("温馨提示");
                    alertDialog.setMessage("注册失败，用户名已被注册！");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog.show();
                } else if (edit2_register.getText().toString().equals(edit3_register.getText().toString()) && flat == 1){
                    String name = edit1_register.getText().toString().trim();
                    String password = edit3_register.getText().toString().trim();
                    String gender = "";
                    if (rBu1_register.isChecked()){
                        gender = "男";
                    }else {
                        gender = "女";
                    }
                    User user = new User();
                    user.setName(name);
                    user.setPassword(password);
                    user.setGender(gender);

                    long rowId = mySQLiteOpenHelper.insertData(user);
                    if (rowId != -1){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(InsertActivity.this);
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setTitle("温馨提示");
                        alertDialog.setMessage("注册成功！");
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                                startActivity(intent);
                                InsertActivity.this.finish();
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.show();
                    }else {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(InsertActivity.this);
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setTitle("温馨提示");
                        alertDialog.setMessage("注册失败，请重新注册！");
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                }
            }
        });
        bu2_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                startActivity(intent);
                InsertActivity.this.finish();
            }
        });

    }
}