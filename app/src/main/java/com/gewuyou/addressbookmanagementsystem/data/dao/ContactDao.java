package com.gewuyou.addressbookmanagementsystem.data.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.gewuyou.addressbookmanagementsystem.data.database.manager.DataBaseManager;
import com.gewuyou.addressbookmanagementsystem.data.entity.Contact;
import com.hankcs.hanlp.HanLP;

import java.util.ArrayList;
import java.util.List;
//用于与联系人相关的数据操作
public class ContactDao {
    /**
     * 数据库
     */
    private static final SQLiteDatabase db = DataBaseManager.getInstance().getDatabase();
    /**
     * 判断是否为小写字母的正则
     */
    private static final String LOWERCASE_LETTER_REGEXES = "^[a-zA-Z]*";

    /**
     * 判断是否为汉字的正则
     */
    private static final String CHINESE_REGEXES = "^[\\u4E00-\\u9FFF]$";
    /**
     * 数字正则
     */
    private static final String NUMBER_REGEX = "\\d+";

    /**
     * 列表查询
     *
     * @return 联系人列表
     */
    public static List<Contact> getAll() {
        List<Contact> contacts = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from contact", null);
        while (cursor.moveToNext()) {
            // 获取姓名的第一个字
            String tag = getTag(cursor.getString(1).substring(0, 1));
            Contact contact = new Contact(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    tag
            );
            contacts.add(contact);
        }
        cursor.close();
        return contacts;
    }

    /**
     * 获取首字首拼音
     *
     * @param first 首字
     * @return 首字首拼音
     */
    @NonNull
    private static String getTag(String first) {
        String tag;
        if (first.matches(LOWERCASE_LETTER_REGEXES)) {
            tag = first.toUpperCase();
        } else if (first.matches(CHINESE_REGEXES)) {
            // 获取汉字拼音首字母
            tag = HanLP.convertToPinyinFirstCharString(first, " ", false).toUpperCase();//第三方jar包
        } else {
            tag = "#";
        }
        return tag;
    }

    /**
     * 根据Id删除联系人
     *
     * @param id 联系人Id
     */
    public static void deleteById(int id) {
        db.delete("contact", "id = ?", new String[]{String.valueOf(id)});
    }

    /**
     * 保存与修改
     *
     * @param contact 联系人
     */
    public static void save(Contact contact) {
        // 检查是否存在该联系人
        if (getOneById(String.valueOf(contact.getId())) != null) {
            db.execSQL("update contact set name = ?, phone = ?, gender = ?, remark = ? where id = ?", new Object[]{
                    contact.getName(),
                    contact.getPhone(),
                    contact.getGender(),
                    contact.getRemark(),
                    contact.getId()
            });
        } else {
            db.execSQL("insert into contact(name, phone, gender, remark) values(?,?,?,?)", new Object[]{
                    contact.getName(),
                    contact.getPhone(),
                    contact.getGender(),
                    contact.getRemark()
            });
        }
    }

    /**
     * 保存或修改联系人
     *
     * @param contact  联系人
     * @param isUpdate 是否更新
     */
    public static void save(Contact contact, boolean isUpdate) {
        // 检查是否存在该联系人
        if (isUpdate) {
            db.execSQL("update contact set name = ?, phone = ?, gender = ?, remark = ? where id = ?", new Object[]{
                    contact.getName(),
                    contact.getPhone(),
                    contact.getGender(),
                    contact.getRemark(),
                    contact.getId()
            });
        } else {
            db.execSQL("insert into contact(name, phone, gender, remark) values(?,?,?,?)", new Object[]{
                    contact.getName(),
                    contact.getPhone(),
                    contact.getGender(),
                    contact.getRemark()
            });
        }
    }

    /**
     * 根据id返回联系人
     *
     * @param id 联系人Id
     * @return 联系人
     */
    public static Contact getOneById(String id) {
        Cursor cursor = db.query("contact", null, "id = ?", new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(1);
            return new Contact(
                    cursor.getInt(0),
                    name,
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    getTag(name.substring(0, 1))
            );
        } else {
            cursor.close();
            return null;
        }
    }

    /**
     * 根据姓名或者电话模糊查询
     *
     * @param nameOrPhone 姓名或者电话
     * @return 联系人集合
     */
    public static List<Contact> getContactsByNameOrPhone(String nameOrPhone) {
        // 判断是否为纯数字如果是则进行电话号码模糊查询反之则进行名字模糊查询
        if (nameOrPhone.matches(NUMBER_REGEX)) {
            return getContactsByPhone(nameOrPhone);
        } else {
            return getContactsByName(nameOrPhone);
        }
    }

    /**
     * 根据姓名模糊查询
     *
     * @param name 姓名
     * @return 联系人集合
     */
    private static List<Contact> getContactsByName(String name) {
        List<Contact> contacts = new ArrayList<>();
        String query = "select * from contact where name like ?";
        String[] selectionArgs = new String[]{"%" + name + "%"};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        while (cursor.moveToNext()) {
            String s = cursor.getString(1);
            // 从游标中获取联系人信息并添加到列表中
            Contact contact = new Contact(
                    cursor.getInt(0),
                    s,
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    getTag(s.substring(0, 1))
            );
            contacts.add(contact);
        }
        cursor.close();
        return contacts;
    }

    /**
     * 根据电话号码模糊查询
     *
     * @param phone 电话号码
     * @return 联系人集合
     */
    private static List<Contact> getContactsByPhone(String phone) {
        List<Contact> contacts = new ArrayList<>();
        String query = "select * from contact where phone like ?";
        String[] selectionArgs = new String[]{"%" + phone + "%"};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            // 从游标中获取联系人信息并添加到列表中
            Contact contact = new Contact(
                    cursor.getInt(0),
                    name,
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    getTag(name.substring(0, 1))
            );
            contacts.add(contact);
        }
        cursor.close();
        return contacts;
    }
}





