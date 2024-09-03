package com.gewuyou.addressbookmanagementsystem.activity;

import static com.gewuyou.addressbookmanagementsystem.constants.StringConstant.MAN;
import static com.gewuyou.addressbookmanagementsystem.constants.StringConstant.WOMAN;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.gewuyou.addressbookmanagementsystem.MainActivity;
import com.gewuyou.addressbookmanagementsystem.R;
import com.gewuyou.addressbookmanagementsystem.activity.interfaces.APPActivity;
import com.gewuyou.addressbookmanagementsystem.data.dao.ContactDao;
import com.gewuyou.addressbookmanagementsystem.data.entity.Contact;
//用于处理添加联系人的界面逻辑。
public class AddActivity extends AppCompatActivity implements APPActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
    }

    @Override
    public void initView() {
        TextView name = findViewById(R.id.add_name);
        TextView phone = findViewById(R.id.add_phone);
        RadioButton male = findViewById(R.id.add_radio_male);
        RadioButton female = findViewById(R.id.add_radio_female);
        TextView remark = findViewById(R.id.add_remark);

        // 设置保存修改按钮点击
        findViewById(R.id.add_update).setOnClickListener(v -> {
            if (
                    name.getText().toString().trim().isEmpty() ||
                            phone.getText().toString().trim().isEmpty() ||
                            remark.getText().toString().trim().isEmpty()
            ) {
                Toast.makeText(AddActivity.this, "输入不能为空，请检查输入!", Toast.LENGTH_SHORT).show();
            } else {
                Contact temp = new Contact();
                temp.setName(name.getText().toString().trim());
                temp.setPhone(phone.getText().toString().trim());
                temp.setRemark(remark.getText().toString().trim());
                if (male.isChecked()) {
                    temp.setGender(MAN);
                } else if (female.isChecked()) {
                    temp.setGender(WOMAN);
                }
                ContactDao.save(temp);
                Toast.makeText(AddActivity.this, "保存成功!", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
        // 返回功能
        //获取工具栏
        Toolbar toolbar = findViewById(R.id.add_toolbar);
        this.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            Intent back = new Intent(AddActivity.this, MainActivity.class);
            startActivity(back);
            finish();
        });
    }

    @Override
    public void finishViewData(boolean isRefresh) {
        //ignore
    }
}