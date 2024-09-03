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

import com.gewuyou.addressbookmanagementsystem.R;
import com.gewuyou.addressbookmanagementsystem.activity.interfaces.APPActivity;
import com.gewuyou.addressbookmanagementsystem.data.dao.ContactDao;
import com.gewuyou.addressbookmanagementsystem.data.entity.Contact;
//更新联系人信息的活动页面
public class UpdateActivity extends AppCompatActivity implements APPActivity {

    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        finishViewData(true);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        finishViewData(true);
    }

    @Override
    public void initView() {
        TextView name = findViewById(R.id.edit_name);
        TextView phone = findViewById(R.id.edit_phone);
        RadioButton male = findViewById(R.id.radio_male);
        RadioButton female = findViewById(R.id.radio_female);
        TextView remark = findViewById(R.id.edit_remark);
        // 设置保存修改按钮点击
        findViewById(R.id.edit_update).setOnClickListener(v -> {
            if (
                    name.getText().toString().trim().isEmpty() ||
                            phone.getText().toString().trim().isEmpty() ||
                            remark.getText().toString().trim().isEmpty()
            ) {
                Toast.makeText(UpdateActivity.this, "输入不能为空，请检查输入!", Toast.LENGTH_SHORT).show();
            } else {
                Contact temp = new Contact();
                temp.setId(contact.getId());
                temp.setName(name.getText().toString().trim());
                temp.setPhone(phone.getText().toString().trim());
                temp.setRemark(remark.getText().toString().trim());
                if (male.isChecked()) {
                    temp.setGender(MAN);
                } else if (female.isChecked()) {
                    temp.setGender(WOMAN);
                }
                ContactDao.save(temp, true);
                Toast.makeText(UpdateActivity.this, "修改成功!", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
        // 返回功能
        //获取工具栏
        Toolbar toolbar = findViewById(R.id.edit_toolbar);
        this.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            Intent back = new Intent(UpdateActivity.this, DetailActivity.class);
            startActivity(back);
            finish();
        });
    }

    @Override
    public void finishViewData(boolean isRefresh) {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        //获取数据
        contact = ContactDao.getOneById(id);

        TextView name = findViewById(R.id.edit_name);
        TextView phone = findViewById(R.id.edit_phone);
        RadioButton male = findViewById(R.id.radio_male);
        RadioButton female = findViewById(R.id.radio_female);
        TextView remark = findViewById(R.id.edit_remark);
        if (contact == null) {
            return;
        }
        name.setText(contact.getName());
        phone.setText(contact.getPhone());
        remark.setText(contact.getRemark());
        if (MAN.equals(contact.getGender())) {
            male.setChecked(true);
        } else if (WOMAN.equals(contact.getGender())) {
            female.setChecked(true);
        }
    }
}