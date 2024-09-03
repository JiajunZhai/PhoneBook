package com.gewuyou.addressbookmanagementsystem.data.database.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gewuyou.addressbookmanagementsystem.data.database.helper.DataBaseOpenHelper;
//对数据库进行集中管理和操作
/**
 * 数据库管理类
 */
public class DataBaseManager {
    private static DataBaseManager instance;
    private SQLiteDatabase database;
    private DataBaseOpenHelper openHelper;

    public static DataBaseManager getInstance() {
        synchronized (DataBaseManager.class) {
            if (instance == null) {
                instance = new DataBaseManager();
            }
        }
        return instance;
    }

    /**
     * 初始化数据库
     *
     * @param context 上下文
     */
    public void initDatabase(Context context) {
        openHelper = new DataBaseOpenHelper(context.getApplicationContext());
        openDatabase();
    }

    /**
     * 获取数据库
     *
     * @return 数据库对象
     */
    public SQLiteDatabase getDatabase() {
        synchronized (DataBaseManager.class) {
            if (database == null) {
                openDatabase();
            }
            return database;
        }
    }

    /**
     * 创建或打开一个可以读的数据库
     */
    private void openDatabase() {
        if (this.openHelper != null) {
            try {
                database = openHelper.getWritableDatabase();
            } catch (Exception e) {
                database = openHelper.getReadableDatabase();
                Log.e("数据库", "打开数据库失败");
            }
        }
    }

    /**
     * 关闭数据库
     */
    public void closeDatabase() {
        try {
            if (database != null) {
                database.close();
            }
        } catch (Exception e) {
            Log.e("数据库", "关闭数据库失败");
        }
    }
}
