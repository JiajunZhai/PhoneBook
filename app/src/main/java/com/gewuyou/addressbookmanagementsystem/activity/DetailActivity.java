package com.gewuyou.addressbookmanagementsystem.activity;

import static com.gewuyou.addressbookmanagementsystem.constants.StringConstant.MAN;
import static com.gewuyou.addressbookmanagementsystem.constants.StringConstant.MESSAGE;
import static com.gewuyou.addressbookmanagementsystem.constants.StringConstant.PHONE;
import static com.gewuyou.addressbookmanagementsystem.constants.StringConstant.WOMAN;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.gewuyou.addressbookmanagementsystem.MainActivity;
import com.gewuyou.addressbookmanagementsystem.R;
import com.gewuyou.addressbookmanagementsystem.activity.interfaces.APPActivity;
import com.gewuyou.addressbookmanagementsystem.data.dao.ContactDao;
import com.gewuyou.addressbookmanagementsystem.data.entity.Contact;

public class DetailActivity extends AppCompatActivity implements APPActivity {
    //主要用于展示联系人的详细信息，并提供相关操作，如拨打电话、发送短信、删除、修改等。
    private static final int CALL_PHONE_REQUEST_CODE = 1;

    private Contact contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // 初始化页面
        initView();
        // 刷新页面数据
        finishViewData(false);
    }


    private void makePhoneCall(String phoneNumber) {
        // 拨打电话的意图
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        // 拨打电话号码
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        finishViewData(true);
    }

    @Override
    public void initView() {
        // 获取Id
        int id = getIntent().getIntExtra("id", 0);
        //获取数据
        contact = ContactDao.getOneById(String.valueOf(id));
        // 拨打电话
        Button call_phone = findViewById(R.id.detail_phone_picture);
        call_phone.setOnClickListener(
                v -> {
                    // 检查是否拥有打电话的权限
                    if (ContextCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        Log.i(PHONE, "有权限拨打电话");
                        // 拥有权限，直接拨打电话
                        makePhoneCall(contact.getPhone().trim());
                    } else {
                        Log.i(PHONE, "没有权限拨打电话");
                        // 没有权限，向用户申请权限
                        ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_REQUEST_CODE);
                    }
                }
        );
        // 发送短信
        Button message = findViewById(R.id.detail_massage);
        message.setOnClickListener(
                v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    // 设置数据
                    intent.setData(Uri.parse("smsto:" + contact.getPhone().trim()));
                    Log.i(MESSAGE, "发送短信");
                    startActivity(intent);
                }
        );
        // 返回
        Button back = findViewById(R.id.detail_back);
        back.setOnClickListener(
                v -> {
                    Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
        );
        // 删除
        Button delete = findViewById(R.id.detail_delete);
        delete.setOnClickListener(
                v -> {
                    ContactDao.deleteById(id);
                    Toast.makeText(DetailActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
        );

        // 修改
        Button update = findViewById(R.id.detail_update);
        update.setOnClickListener(
                v -> {
                    Intent intent = new Intent(DetailActivity.this, UpdateActivity.class);
                    intent.putExtra("id", String.valueOf(id));
                    startActivity(intent);
                    finish();
                }
        );
    }

    @Override
    public void finishViewData(boolean isFinish) {
        if (isFinish) {
            // 获取Id
            int id = getIntent().getIntExtra("id", 0);
            //获取数据
            contact = ContactDao.getOneById(String.valueOf(id));
        }
        // 头像
        ImageView avatar = findViewById(R.id.detail_picture);
        //姓名
        TextView name = findViewById(R.id.detail_name);
        // 电话号码
        TextView call_phone_number = findViewById(R.id.detail_phone);
        TextView gender = findViewById(R.id.detail_gender);
        TextView remake = findViewById(R.id.detail_remake);
        LinearLayout top = findViewById(R.id.detail_content_top);
        if (contact == null) {
            return;
        }
        remake.setText(contact.getRemark());
        gender.setText(contact.getGender());
        // 根据性别设置头像
        if (MAN.equals(contact.getGender())) {
            avatar.setImageResource(R.drawable.man);
            top.setBackgroundColor(ContextCompat.getColor(this, R.color.man_background_color));
        } else if (WOMAN.equals(contact.getGender())) {
            avatar.setImageResource(R.drawable.woman);
            top.setBackgroundColor(ContextCompat.getColor(this, R.color.woman_background_color));
        } else {
            avatar.setImageResource(R.drawable.unknown_man);
            top.setBackgroundColor(ContextCompat.getColor(this, R.color.unknown_background_color));
        }
        name.setText(contact.getName());
        call_phone_number.setText(contact.getPhone());
    }

}