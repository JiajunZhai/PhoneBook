package com.gewuyou.addressbookmanagementsystem.data.database.helper;

import static com.gewuyou.addressbookmanagementsystem.constants.StringConstant.DATABASE;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.gewuyou.addressbookmanagementsystem.data.fake.FakeDataOperation;

//用于构建和管理与数据库相关的操作
/**
 * 数据库连接工具
 */
public class DataBaseOpenHelper extends SQLiteOpenHelper {
    /**
     * 数据库名
     */
    private static final String DATABASE_NAME = "address_book.db";
    /**
     * 版本
     */

    private static final int DATABASE_VERSION = 2;

    public DataBaseOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, null);
    }

    /**
     * 调用时刻：当数据库第1次创建时调用
     * 作用：创建数据库 表 & 初始化数据
     * SQLite数据库创建支持的数据类型： 整型数据、字符串类型、日期类型、二进制
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建表
        createContactTable(db);
    }

    /**
     * 调用时刻：当数据库升级时则自动调用（即 数据库版本 发生变化时）
     * 作用：更新数据库表结构
     * 注：创建SQLiteOpenHelper子类对象时,必须传入一个version参数，该参数 = 当前数据库版本, 若该版本高于之前版本, 就调用onUpgrade()
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
        // 创建测试数据
        FakeDataOperation.createFakeDateTable(db);
    }

    /**
     * 创建联系人表
     */
    private void createContactTable(SQLiteDatabase db) {
        try {
            db.execSQL("drop table if exists contact");
            db.execSQL("create table contact (id integer primary key autoincrement," +
                    "name varchar(20)," +
                    "phone varchar(20)," +
                    "gender varchar(20)," +
                    "remark varchar(20))");
        } catch (SQLException e) {
            Log.d(DATABASE, "创建联系人表失败，表已存在!");
        }
    }
}
