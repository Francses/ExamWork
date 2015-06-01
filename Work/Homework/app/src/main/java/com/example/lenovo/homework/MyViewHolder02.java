package com.example.lenovo.homework;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by lenovo on 2015/5/30.
 */
public class MyViewHolder02 extends RecyclerView.ViewHolder {

    public TextView praise2;
    public TextView praise_num2;
    public TextView bilittle2;
    public TextView bilittle_num2;
    public TextView complain2;
    public TextView complain_num2;
    public TextView author2;
    public TextView time2;
    public TextView content2;

    public MyViewHolder02(View view){
        super(view);
        praise_num2 = (TextView)itemView.findViewById(R.id.praise_num2);
        praise2 = (TextView)itemView.findViewById(R.id.praise2);
        bilittle2 = (TextView)itemView.findViewById(R.id.bilittle2);
        bilittle_num2 = (TextView)itemView.findViewById(R.id.bilittle_num2);
        complain2 = (TextView)itemView.findViewById(R.id.complain2);
        complain_num2 = (TextView)itemView.findViewById(R.id.complain_num2);
        author2 = (TextView)itemView.findViewById(R.id.author2);
        time2 = (TextView)itemView.findViewById(R.id.time2);
        content2 = (TextView)itemView.findViewById(R.id.content);
    }
}
