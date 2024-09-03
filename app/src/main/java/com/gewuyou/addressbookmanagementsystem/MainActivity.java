package com.gewuyou.addressbookmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.gewuyou.addressbookmanagementsystem.activity.AddActivity;
import com.gewuyou.addressbookmanagementsystem.activity.interfaces.APPActivity;
import com.gewuyou.addressbookmanagementsystem.adapter.ContactAdapter;
import com.gewuyou.addressbookmanagementsystem.data.dao.ContactDao;
import com.gewuyou.addressbookmanagementsystem.data.database.manager.DataBaseManager;
import com.gewuyou.addressbookmanagementsystem.data.entity.Contact;
import com.gewuyou.addressbookmanagementsystem.fragment.homeFragment;
import com.gewuyou.addressbookmanagementsystem.fragment.ggFragment;
import com.gewuyou.addressbookmanagementsystem.fragment.gggFragment;
import com.gewuyou.addressbookmanagementsystem.fragment.mineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

//定义了应用的主活动
public class MainActivity extends AppCompatActivity implements APPActivity {
    private static final DataBaseManager dataBaseManager = DataBaseManager.getInstance();

    private homeFragment mhomeFragment;

    private ggFragment mggFragment;

    private gggFragment mgggFragment;

    private mineFragment mmineFragment;

    private BottomNavigationView mbottomNavigationView;


    @Override
    //进行初始化视图的操作，包括设置列表视图、添加按钮的点击事件
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        finishViewData();

        mbottomNavigationView = findViewById(R.id.BottomNavigationView);
        //点击事件
        mbottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    //暂无页面
                } else if (item.getItemId() == R.id.home) {
                    seletedFragment(1);//广告页面

                } else if (item.getItemId() == R.id.home) {
                    seletedFragment(2);//广告页面

                } else {
                    //seletedFragment(3);
                    Intent intent = new Intent(MainActivity.this, mineMainActivity.class);
                    startActivity(intent);
                }

                return true;
            }
        });

//        默认第一个页面
        seletedFragment(0);
    }
    private void seletedFragment(int position){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFrament(fragmentTransaction);
        if (position == 0){
            if (mhomeFragment ==null){
                mhomeFragment = new homeFragment();
                fragmentTransaction.add(R.id.content,mhomeFragment);
            }else {
                fragmentTransaction.show(mhomeFragment);
            }
        }else if (position == 1){
            if (mggFragment ==null){
                mggFragment = new ggFragment();
                fragmentTransaction.add(R.id.content,mggFragment);
            }else {
                fragmentTransaction.show(mggFragment);
            }
        }else if (position == 2){
            if (mgggFragment ==null){
                mgggFragment = new gggFragment();
                fragmentTransaction.add(R.id.content,mgggFragment);
            }else {
                fragmentTransaction.show(mgggFragment);
            }
        } else{
            if (mmineFragment == null) {
                mmineFragment = new mineFragment();
                fragmentTransaction.add(R.id.content, mmineFragment);
            } else {
                fragmentTransaction.show(mmineFragment);
            }
        }
        //提交
        fragmentTransaction.commit();
    }
    private void hideFrament(FragmentTransaction fragmentTransaction){
        if (mhomeFragment!=null){
            fragmentTransaction.hide(mhomeFragment);
        }
        if (mggFragment!=null){
            fragmentTransaction.hide(mggFragment);
        }
        if (mgggFragment!=null){
            fragmentTransaction.hide(mgggFragment);
        }
        if (mmineFragment!=null){
            fragmentTransaction.hide(mmineFragment);
        }
    }


    //定义一些成员变量
    @Override
    //进行一些数据刷新的操作
    protected void onResume() {
        super.onResume();
        finishViewData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭数据库
        dataBaseManager.closeDatabase();
    }

    @Override
    public void initView() {
        ListView listView = this.findViewById(R.id.book_list);
        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(v -> {
            // 打开添加界面
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
//            finish();
        });
        // 搜素框
        EditText searchBox = findViewById(R.id.search_box);
        //搜索按钮
        findViewById(R.id.search).setOnClickListener(v -> {
            // 获取输入内容
            String nameOrPhone = searchBox.getText().toString();
            if (nameOrPhone.isEmpty()) {
                Toast.makeText(MainActivity.this, "输入不能为空，请检查输入!", Toast.LENGTH_SHORT).show();
            }
            listView.setAdapter(null);
            List<Contact> contactsByNameOrPhone = ContactDao.getContactsByNameOrPhone(nameOrPhone);
            listView.setAdapter(new ContactAdapter(this, contactsByNameOrPhone));
        });
    }

    @Override
    public void finishViewData(boolean isRefresh) {
        // ignore
    }

    private void finishViewData() {
        // 获取导航条
        Toolbar toolBar = this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolBar);
        dataBaseManager.initDatabase(getApplicationContext());//初始化数据库
        List<Contact> contacts = ContactDao.getAll();//获取所有的联系人列表
        ListView listView = this.findViewById(R.id.book_list);
        if (contacts.isEmpty()) {
            listView.setAdapter(null);//判断联系人是否为空
        } else {
            ContactAdapter contactAdapter = new ContactAdapter(this, contacts);
            listView.setAdapter(contactAdapter);//将其设置给列表视图
        }
    }
}