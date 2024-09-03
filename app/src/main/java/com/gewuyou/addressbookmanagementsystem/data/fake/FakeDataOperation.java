package com.gewuyou.addressbookmanagementsystem.data.fake;

import android.database.sqlite.SQLiteDatabase;

public class FakeDataOperation {
    public static void createFakeDateTable(SQLiteDatabase db) {
        db.execSQL("insert into contact(name, phone, gender, remark) " +
                "values('张三', '12345678901', '男', '张三')");
        db.execSQL("insert into contact(name, phone, gender, remark) " +
                "values('李四', '12345678902', '女', '李四')");
        db.execSQL("insert into contact(name, phone, gender, remark) " +
                "values('王五', '12345678903', '男', '王五')");
        db.execSQL("insert into contact(name, phone, gender, remark) " +
                "values('王五', '12345678903', '女', '王五')");
        db.execSQL("insert into contact(name, phone, gender, remark) " +
                "values('赵六', '12345678904', '女', '赵六')");
        db.execSQL("insert into contact(name, phone, gender, remark) " +
                "values('赵六', '12345678904', '', '赵六')");
    }
}
