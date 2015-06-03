package com.example.lenovo.homework;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class MyAdapter extends Adapter<MyViewHolder> {

    TextView author_text;
    TextView bilittle_text;
    TextView complain_text;
    TextView praise_text;
    TextView time_text;
    TextView praise_num_text;
    TextView bilittle_num_text;
    TextView complain_num_text;
    Bitmap bitmap;
    String url;

    ArrayList<Information_pic> information_pics = new ArrayList<Information_pic>();

    public MyAdapter(ArrayList<Information_pic> information_pics){
        this.information_pics = information_pics;
    }

  //  ImageView imageView;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycylerview_item,viewGroup,false);
        MyViewHolder vh = new MyViewHolder(item);
        author_text = (TextView)item.findViewById(R.id.author);
        time_text = (TextView)item.findViewById(R.id.time);
        praise_num_text = (TextView)item.findViewById(R.id.praise_num);
        bilittle_num_text = (TextView)item.findViewById(R.id.bilittle_num);
        complain_num_text = (TextView)item.findViewById(R.id.complain);
        complain_text = (TextView)item.findViewById(R.id.complain);
        bilittle_text = (TextView)item.findViewById(R.id.bilittle);
        praise_text = (TextView)item.findViewById(R.id.praise);
       // imageView = (ImageView)item.findViewById(R.id.beauty_image);
        //  progressBar = (ProgressBar)item.findViewById(R.id.progressBar_1);
        return vh;
    }
    ImageView imageView1;
    int h;
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {


        myViewHolder.imageView.setTag(position);
        h = position;
        String author = information_pics.get(position).getAuthor();
        String time = information_pics.get(position).getTime();
        String url = information_pics.get(position).getUrl();
        String praise_num = information_pics.get(position).getPraise_num();
        String bilittle_num = information_pics.get(position).getBilittle_num();
        String complain_num = information_pics.get(position).getComplain_num();

        url = url.replaceAll("\\\\/", "/");
        url = url.substring(2, url.length() - 2);
        Log.d("URLLLLLLLLLLL", url);
        imageView1 = myViewHolder.imageView;
        imageView1.setTag(h);
        Log.d("hhhonBindViewHolder", "h的值为" + h);
        new MyAsyncTask().execute(url);

        author_text.setText(author);
        time_text.setText(time);
        praise_num_text.setText(praise_num);
        bilittle_num_text.setText(bilittle_num);
        complain_num_text.setText(complain_num);
    }

    public class MyAsyncTask extends AsyncTask<String,Void,Bitmap> {
        @Override
        protected void onPreExecute() {
            Log.d("MyAdapter","Start!");
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            url = params[0];
            URLConnection connection = null;
            InputStream is;
            try {
                connection = new URL(url).openConnection();
                is = connection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bitmap = BitmapFactory.decodeStream(bis);
                is.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Log.d("hhhhonPostExecute","h的值为" + h);
                if (imageView1.getTag() != null && imageView1.getTag().equals(h)){
                    imageView1.setImageBitmap(bitmap);
                }
            //imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }


}
