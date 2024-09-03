package com.gewuyou.addressbookmanagementsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gewuyou.addressbookmanagementsystem.data.database.helper.UserDataBaseHelper;
import com.gewuyou.addressbookmanagementsystem.data.entity.UserInfo;

public class LoginActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;
    private CheckBox checkbbox;

    private boolean is_login;

    private SharedPreferences mSharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loign);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //获取mSharedPreferences
        mSharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        //初始化控件
        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);
        checkbbox=findViewById(R.id.checkbbox);

        //启动时判断是否勾选记住密码
        is_login= mSharedPreferences.getBoolean("is_login",false);
        if (is_login) {
        String username = mSharedPreferences.getString("username",null);
        String password = mSharedPreferences.getString("password",null);
        et_username.setText(username);
        et_password.setText(password);
        checkbbox.setChecked(true);
        }



        //点击注册
        findViewById(R.id.GoRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        //登录
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "请输入用户名和密码！", Toast.LENGTH_SHORT).show();
                } else {
                    UserInfo login = UserDataBaseHelper.getInstance(LoginActivity.this).login(username);
                    if (login != null) {
                        if (username.equals(login.getUsername()) && password.equals(login.getPassword())) {
                            //获取用户提交的账号密码
                            SharedPreferences.Editor edit = mSharedPreferences.edit();
                            edit.putBoolean("is_login",is_login);
                            edit.putString("username",username);
                            edit.putString("password",password);
                            //提交
                           edit.commit();
                            // 登陆成功 并获取账号信息
                            SharedPreferences sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username", username);
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // 登陆失败
                            Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "用户未注册！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //记住我的点击事件


        checkbbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                is_login=isChecked;
            }
        });
    }
}