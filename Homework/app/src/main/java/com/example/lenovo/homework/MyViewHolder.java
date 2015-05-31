package com.example.lenovo.homework;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lenovo on 2015/5/30.
 */
public class MyViewHolder extends ViewHolder {

    public TextView praise;
    public TextView praise_num;
    public TextView bilittle;
    public TextView bilittle_num;
    public TextView complain;
    public TextView complain_num;
    public TextView author;
    public TextView time;
    public ImageView beauty_image;

    public MyViewHolder(View view){
        super(view);
        praise = (TextView)itemView.findViewById(R.id.praise);
        praise_num = (TextView)itemView.findViewById(R.id.praise_num);
        bilittle = (TextView)itemView.findViewById(R.id.bilittle);
        bilittle_num = (TextView)itemView.findViewById(R.id.bilittle_num);
        complain = (TextView)itemView.findViewById(R.id.complain);
        complain_num = (TextView)itemView.findViewById(R.id.complain_num);
        author = (TextView)itemView.findViewById(R.id.author);
        time = (TextView)itemView.findViewById(R.id.time);
        beauty_image = (ImageView)itemView.findViewById(R.id.beauty_image);
    }
}
