package com.gewuyou.addressbookmanagementsystem.adapter;

import static com.gewuyou.addressbookmanagementsystem.constants.StringConstant.MAN;
import static com.gewuyou.addressbookmanagementsystem.constants.StringConstant.WOMAN;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gewuyou.addressbookmanagementsystem.R;
import com.gewuyou.addressbookmanagementsystem.activity.DetailActivity;
import com.gewuyou.addressbookmanagementsystem.data.entity.Contact;

import java.util.List;
import java.util.Objects;
//是一个自定义的适配器，主要用于将联系人数据列表视图进行适配和显示。
public class ContactAdapter extends ArrayAdapter<Contact> {


    private final List<Contact> contacts;

    private String preTag;

    public ContactAdapter(@NonNull Context context, List<Contact> contacts) {
        super(context, R.layout.book_list_view, contacts);
        contacts.sort((o1, o2) -> {
            if (o1.getTag().equals("#")) {
                // 将"#"放在后面
                return 1;
            } else if (o2.getTag().equals("#")) {
                // 将"#"放在后面
                return -1;
            } else {
                // 按字母排序
                return o1.getTag().compareTo(o2.getTag());
            }
        });
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 获取上下文对象
        Context context = getContext();
        if (Objects.isNull(convertView)) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.book_list_view, parent, false);
        }
        // 图片
        ImageView picture = Objects.requireNonNull(convertView).findViewById(R.id.picture);
        // 姓名
        TextView name = Objects.requireNonNull(convertView).findViewById(R.id.name);
        // 标签
        TextView tag = Objects.requireNonNull(convertView).findViewById(R.id.tag);
        Contact contact = contacts.get(position);
        name.setText(contact.getName());

        if (contact.getGender().equals(MAN)) {
            picture.setImageResource(R.drawable.man);
        } else if (contact.getGender().equals(WOMAN)) {
            picture.setImageResource(R.drawable.woman);
        } else {
            picture.setImageResource(R.drawable.unknown_man);
        }
        // 如果上一个标签为空则赋值或获取标签与上一个标签不同则更新
        if (Objects.isNull(preTag) || !preTag.equals(contact.getTag())) {
            preTag = contact.getTag();
            tag.setText(preTag);
        } else {
            tag.setHeight(0);
        }
        // 实现点击跳转
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id", contact.getId());
            context.startActivity(intent);
        });
        return convertView;
    }

    public List<Contact> getContacts() {
        return contacts;
    }
}
