package com.gewuyou.addressbookmanagementsystem.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.gewuyou.addressbookmanagementsystem.R;
import com.gewuyou.addressbookmanagementsystem.activity.AddActivity;
import com.gewuyou.addressbookmanagementsystem.adapter.ContactAdapter;
import com.gewuyou.addressbookmanagementsystem.data.dao.ContactDao;
import com.gewuyou.addressbookmanagementsystem.data.entity.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class homeFragment extends Fragment {

    private ListView listView;
    private ContactAdapter contactAdapter;
    private List<Contact> contactList;
    private ContactDao contactDao;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        return rootView;
    }



}

