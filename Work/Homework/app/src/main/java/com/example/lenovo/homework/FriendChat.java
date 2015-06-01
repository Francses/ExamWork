package com.example.lenovo.homework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FriendChat extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fiendchat,container,false );
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycleview_one);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        final RecyclerView.ItemDecoration decoration = new SampleDivider(this.getActivity());
        recyclerView.addItemDecoration(decoration);
        final MyAdapter myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        return view;
    }





}
