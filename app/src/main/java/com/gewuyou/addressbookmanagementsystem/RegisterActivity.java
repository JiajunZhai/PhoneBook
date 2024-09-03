package com.gewuyou.addressbookmanagementsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gewuyou.addressbookmanagementsystem.data.database.helper.UserDataBaseHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;

//    private SharedPreferences mSharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //获取mSharedPreferences
//        mSharedPreferences=getSharedPreferences("user",MODE_PRIVATE);

        //初始化控件
        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);



        findViewById(R.id.deleteRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //销毁注册页面
                finish();
            }
        });

        //点击注册事件
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "请输入用户名和密码！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) { // 新增对密码为空的单独判断
                    Toast.makeText(RegisterActivity.this, "请输入密码！", Toast.LENGTH_SHORT).show();
                } else {
                    // SharedPreferences.Editor edit = mSharedPreferences.edit();
                    // edit.putString("username",username);
                    // edit.putString("password",password);
                    int row = UserDataBaseHelper.getInstance(RegisterActivity.this).register(username,password,"暂无");
                    if (row > 0) {
                        Toast.makeText(RegisterActivity.this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                        finish();
                    }



                }
            }
        });
    }
}