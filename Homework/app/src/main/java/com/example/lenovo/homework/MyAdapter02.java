package com.example.lenovo.homework;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lenovo on 2015/5/30.
 */
public class MyAdapter02 extends RecyclerView.Adapter<MyViewHolder02> {


    TextView author_text;
    TextView time_text;
    TextView content_text;
    TextView praise_num2_text;
    TextView bilittle_num2_text;
    TextView complain_num2_text;
   // ProgressBar progressBar;

    ArrayList<Information> informations = new ArrayList<Information>();

    public MyAdapter02(ArrayList<Information> informations){
        this.informations = informations;
    }

    @Override
    public MyViewHolder02 onCreateViewHolder(ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item02,viewGroup,false);
        MyViewHolder02 vh = new MyViewHolder02(item);

        author_text = (TextView)item.findViewById(R.id.author2);
        time_text = (TextView)item.findViewById(R.id.time2);
        content_text = (TextView)item.findViewById(R.id.content);
        praise_num2_text = (TextView)item.findViewById(R.id.praise2);
        bilittle_num2_text = (TextView)item.findViewById(R.id.bilittle2);
        complain_num2_text = (TextView)item.findViewById(R.id.complain2);
        //progressBar = (ProgressBar)item.findViewById(R.id.action_menu_presenter);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder02 myViewHolder02, int position) {
        String author = informations.get(position).getAuthor();
        String time = informations.get(position).getTime();
        String content = informations.get(position).getContentr();
        String praise_num2 = informations.get(position).getPraise_num2();
        String bilittle_num2 = informations.get(position).getBilittle_num2();
        String complain_num2 = informations.get(position).getComplain_num2();

        author_text.setText(author);
        time_text.setText(time);
        content_text.setText(content);
        praise_num2_text.setText(praise_num2);
        bilittle_num2_text.setText(bilittle_num2);
        complain_num2_text.setText(complain_num2);
    }

    @Override
    public int getItemCount() {
        return 20;
    }


}
