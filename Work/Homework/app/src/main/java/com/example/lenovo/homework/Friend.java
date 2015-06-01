package com.example.lenovo.homework;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Friend extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friend,container,false);
        RecyclerView recyclerView2 = (RecyclerView)view.findViewById(R.id.recycleview_two);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView2.setLayoutManager(layoutManager);
        final RecyclerView.ItemDecoration decoration = new SampleDivider(this.getActivity());
        recyclerView2.addItemDecoration(decoration);
        final MyAdapter02 myAdapter02 = new MyAdapter02();
        recyclerView2.setAdapter(myAdapter02);
        return view;
    }
}
