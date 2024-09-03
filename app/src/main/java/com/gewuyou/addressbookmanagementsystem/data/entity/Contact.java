package com.gewuyou.addressbookmanagementsystem.data.entity;

import androidx.annotation.NonNull;
//用于表示联系人的信息
public class Contact {
    /**
     * id
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 电话
     */
    private String phone;
    /**
     * 性别
     */

    private String gender;
    /**
     * 描述
     */
    private String remark;
    /**
     * 分组字母
     */
    private String tag;

    public Contact() {
    }

    public Contact(Integer id, String name, String phone, String gender, String remark) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.remark = remark;
    }

    public Contact(Integer id, String name, String phone, String gender, String remark, String tag) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.remark = remark;
        this.tag = tag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @NonNull
    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", remark='" + remark + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
