package com.example.register;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.register.bean.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ModifyActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_TAKE = 1;
    public static final int REQUEST_CODE_CHOOSE = 0;
    private ImageView modify_image;
    private EditText modify_name,modify_edit,modify_birthTime,modify_city_text,modify_school;
    private Button bu_take,bu_photo,bu_modify,bu_exit;
    private  MySQLiteOpenHelper mySQLiteOpenHelper;
    private Uri imageUri;
    private String imageBase64,image64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        initView();
        SharedPreferences spfModify = getSharedPreferences("spfModify",MODE_PRIVATE);
        image64 = spfModify.getString("image_64","");
        if (image64!=""){
            modify_image.setImageBitmap(imageUtil.base64ToImage(image64));
        }
        Intent intent = getIntent();
        modify_name.setText(intent.getStringExtra("modify_fName"));
        modify_edit.setText(intent.getStringExtra("edit"));
        modify_birthTime.setText(intent.getStringExtra("birthTime"));
        modify_city_text.setText(intent.getStringExtra("city_text"));
        modify_school.setText(intent.getStringExtra("school"));
        final String name = intent.getStringExtra("modify_name");
        mySQLiteOpenHelper = new MySQLiteOpenHelper(this);
        bu_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setName(name);
                user.setFakeName(modify_name.getText().toString());
                user.setBirthTime(modify_birthTime.getText().toString().trim());
                user.setSchool(modify_school.getText().toString().trim());
                user.setCity(modify_city_text.getText().toString().trim());
                user.setEdit(modify_edit.getText().toString().trim());
                long rowId = mySQLiteOpenHelper.updateData(user);
                if (rowId > 0){
                    Toast.makeText(ModifyActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                }
                Intent intent1 = new Intent(ModifyActivity.this, UserActivity.class);
                intent1.putExtra("fName",modify_name.getText().toString().trim());
                intent1.putExtra("user_name",name);
                intent1.putExtra("edit",modify_edit.getText().toString().trim());
                intent1.putExtra("birthTime",modify_birthTime.getText().toString().trim());
                intent1.putExtra("city_text",modify_city_text.getText().toString().trim());
                intent1.putExtra("school",modify_school.getText().toString().trim());
                SharedPreferences spfModify = getSharedPreferences("spfModify",MODE_PRIVATE);
                SharedPreferences.Editor edit = spfModify.edit();
                edit.putString("image_64",imageBase64);
                edit.apply();
                startActivity(intent1);
                ModifyActivity.this.finish();
            }
        });

        bu_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ModifyActivity.this, UserActivity.class);
                intent1.putExtra("fName",modify_name.getText().toString().trim());
                intent1.putExtra("user_name",name);
                intent1.putExtra("edit",modify_edit.getText().toString().trim());
                intent1.putExtra("birthTime",modify_birthTime.getText().toString().trim());
                intent1.putExtra("city_text",modify_city_text.getText().toString().trim());
                intent1.putExtra("school",modify_school.getText().toString().trim());
                startActivity(intent1);
                ModifyActivity.this.finish();
            }
        });

        bu_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ModifyActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    doTake();
                }else {
                    ActivityCompat.requestPermissions(ModifyActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

                    ActivityCompat.requestPermissions(ModifyActivity.this,new String[]{Manifest.permission.CAMERA},1);
                }
            }
        });

        bu_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ModifyActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    ActivityCompat.requestPermissions(ModifyActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
                }
            }
        });

    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_CODE_CHOOSE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                doTake();
            }else {
                Toast.makeText(this,"你没有获得摄像头权限",Toast.LENGTH_LONG).show();
            }
        }else if (requestCode == 0){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openAlbum();
            }else {
                Toast.makeText(this,"你没有获得访问相册的权限",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void doTake() {
        File imageTemp = new File(getExternalCacheDir(),"imageOut.jpeg");
        if (imageTemp.exists()){
            imageTemp.delete();
        }
        try {
            imageTemp.createNewFile();
        } catch (IOException e){
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT > 24){
            imageUri = FileProvider.getUriForFile(this,"com.example.register.fileprovider",imageTemp);
        } else{
            imageUri = Uri.fromFile(imageTemp);
        }
        Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,REQUEST_CODE_TAKE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    modify_image.setImageBitmap(bitmap);
                    String imageToBase64 = imageUtil.imageToBase64(bitmap);
                    imageBase64 = imageToBase64;


                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
                }
            }
        }else if (requestCode == REQUEST_CODE_CHOOSE){
            if (Build.VERSION.SDK_INT < 19) {
                handleImageBeforeApi19(data);
            }else {
                handleImageOnApi19(data);
            }
        }
    }
    private void handleImageBeforeApi19(Intent data){
        Uri uri = data.getData();
        String path = getImagePath(uri, null);
        displayImage(path);
    }

    @TargetApi(19)
    private void handleImageOnApi19(Intent data) {
        String path = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            String documentId = DocumentsContract.getDocumentId(uri);
            if (TextUtils.equals(uri.getAuthority(),"com.android.providers.media.documents")){
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                path = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null);
            }else if (TextUtils.equals(uri.getAuthority(),"com.android.providers.downloads.documents")){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                path = getImagePath(contentUri, null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            path = getImagePath(uri, null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            path = uri.getPath();
        }
        displayImage(path);
    }

    private void displayImage(String path){
        if (path != null){
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            modify_image.setImageBitmap(bitmap);
            String imageToBase64 = imageUtil.imageToBase64(bitmap);
            imageBase64 = imageToBase64;
        }
    }
    private String getImagePath(Uri uri,String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString((cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
            }
            cursor.close();
        }
        return path;
    }

    public void initView(){
        modify_image = (ImageView) findViewById(R.id.modify_image);
        modify_name = (EditText) findViewById(R.id.modify_name);
        modify_edit= (EditText) findViewById(R.id.modify_edit);
        modify_birthTime = (EditText) findViewById(R.id.modify_birthTime);
        modify_city_text = (EditText) findViewById(R.id.modify_city_text);
        modify_school = (EditText) findViewById(R.id.modify_school);
        bu_take = (Button) findViewById(R.id.bu_take);
        bu_photo = (Button) findViewById(R.id.bu_photo);
        bu_modify = (Button) findViewById(R.id.bu_modify);
        bu_exit = (Button) findViewById(R.id.bu_exit);
    }
}