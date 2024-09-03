package com.gewuyou.addressbookmanagementsystem.data.database.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.gewuyou.addressbookmanagementsystem.data.entity.UserInfo;

public class UserDataBaseHelper extends SQLiteOpenHelper {
        private static UserDataBaseHelper sHelper;
        private static final String DB_NAME = "user.db";   //数据库名
        private static final int VERSION = 2;    //版本号

        //必须实现其中一个构方法
        public UserDataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        //创建单例，供使用调用该类里面的的增删改查的方法
        public synchronized static UserDataBaseHelper getInstance(Context context) {
            if (null == sHelper) {
                sHelper = new UserDataBaseHelper(context, DB_NAME, null, VERSION);
            }
            return sHelper;
        }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建user_table表
        db.execSQL("CREATE TABLE user_table(" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username text," +       //用户名
                "password text," +       //密码
                "nickname text" +        //注册类型   0---用户   1---管理员
                ")");
    }


    @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
        //登录
        @SuppressLint("Range")
        public UserInfo login(String username) {
            //获取SQLiteDatabase实例
            SQLiteDatabase db = getReadableDatabase();
            UserInfo userInfo = null;
            String sql = "select user_id,username,password,nickname  from user_table where username=?";
            String[] selectionArgs = {username};
            Cursor cursor = db.rawQuery(sql, selectionArgs);
            if (cursor.moveToNext()) {
                int user_id = cursor.getInt(cursor.getColumnIndex("user_id"));
                String name = cursor.getString(cursor.getColumnIndex("username"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String nickname = cursor.getString(cursor.getColumnIndex("nickname"));
                userInfo = new UserInfo(user_id, name, password, nickname);
            }
            cursor.close();
//            db.close();
            return userInfo;
        }

        //注册
        public int register(String username, String password, String nickname) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("password", password);
            values.put("nickname", nickname);
//            String nullColumnHack = "values(null,?,?,?)";
            int insert = (int) db.insert("user_table", null, values);
//            db.close();
            return insert;
        }
}

