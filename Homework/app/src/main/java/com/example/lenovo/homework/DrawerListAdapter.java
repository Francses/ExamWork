package com.example.lenovo.homework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lenovo on 2015/5/24.
 */
public class DrawerListAdapter extends ArrayAdapter<Item> {

    private int resourceId;

    public DrawerListAdapter(Context context, int textViewResourceId, List<Item> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        Item fruit = getItem(position);

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.textView);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.textView.setText(fruit.getName());
        return view;
    }

    class ViewHolder{
        TextView textView;
    }
}
