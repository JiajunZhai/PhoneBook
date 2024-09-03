package com.gewuyou.addressbookmanagementsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Context;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gewuyou.addressbookmanagementsystem.activity.AddActivity;
import com.gewuyou.addressbookmanagementsystem.data.database.helper.UserDataBaseHelper;
import com.gewuyou.addressbookmanagementsystem.data.entity.UserInfo;

public class mineMainActivity extends AppCompatActivity {
    private TextView logoutTextView;

    private TextView userTextView;
    private TextView nicknameTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_main);

        // 从SharedPreferences中获取 username
        SharedPreferences sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        // 设置到 tv_username 控件中
        TextView tvUsername = findViewById(R.id.tv_username);
        tvUsername.setText(username);

        // 初始化控件
        logoutTextView = findViewById(R.id.tc);
        // 初始化控件
        userTextView = findViewById(R.id.tv_username);
        nicknameTextView = findViewById(R.id.tv_nickname);


        // 为控件添加点击事件监听器
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 执行退出登录操作，例如清除用户登录状态等
                // 这里只是简单地示范跳转到登录页面
                Intent intent = new Intent(mineMainActivity.this, LoginActivity.class);
                // 清除任务栈并创建一个新的任务栈，使得返回登录页后无法通过返回按钮返回到主页面
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                // 结束当前活动
                finish();
            }
        });

        Toolbar toolbar = findViewById(R.id.add_toolbar);
        this.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            Intent back = new Intent(mineMainActivity.this, MainActivity.class);
            startActivity(back);
            finish();
        });




    }

}