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

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lenovo on 2015/5/30.
 */
public class MyAdapter extends Adapter<MyViewHolder> {

    int sum = 8;
    String author;
    String time;
    String url;
    String praise_num;
    String bilittle_num;
    String complain_num;

    TextView author_text;
    TextView bilittle_text;
    TextView complain_text;
    TextView praise_text;
    TextView time_text;
    TextView praise_num_text;
    TextView bilittle_num_text;
    TextView complain_num_text;
    ImageView imageView;
 //   ProgressBar progressBar;

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
        imageView = (ImageView)item.findViewById(R.id.beauty_image);
      //  progressBar = (ProgressBar)item.findViewById(R.id.progressBar_1);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {

        new ReadHttpGet().execute("http://jandan.net/?oxwlxojflwblxbsapi=jandan.get_ooxx_comments&page=1");
        /*myViewHolder.praise.setText("OO");
        myViewHolder.praise_num.setText(praise_num);
        myViewHolder.bilittle.setText("XX");
        myViewHolder.bilittle_num.setText(bilittle_num);
        myViewHolder.complain.setText("Ridicule");
        myViewHolder.complain_num.setText(complain_num);
        myViewHolder.author.setText(author);
        myViewHolder.time.setText(time);
        myViewHolder.beauty_image.setImageResource(R.drawable.icon);*/
        Log.d("MyAdapter","chushihua");
    }

    public class ReadHttpGet extends AsyncTask<Object, Object, Object> {

        String authore;
        @Override
        protected Object doInBackground(Object... params) {
            HttpGet httpRequest = new HttpGet(params[0].toString());
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse httpResponse = httpClient.execute(httpRequest);
                if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String strResult = EntityUtils.toString(httpResponse.getEntity());
                    return strResult;
                }else {
                    return "Failed";
                }
            }
            catch(ClientProtocolException e) {
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onCancelled(Object result) {
            super.onCancelled(result);
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            try {
                JSONArray jsonArray = new JSONObject(result.toString()).getJSONArray("comments");
                Log.d("MainActivity",jsonArray.toString());
                sum = jsonArray.length();
                JSONObject jsonObject;
                /*jsonObject= jsonArray.getJSONObject(0);
                author = jsonObject.getString("comment_author");
                time = jsonObject.getString("comment_date");
                praise_num = jsonObject.getString("vote_positive");
                bilittle_num = jsonObject.getString("vote_negative");
                complain_num = jsonObject.getString("comment_approved");
                url = jsonObject.getString("pics");

                praise_text.setText("OO");
                bilittle_text.setText("XX");
                complain_text.setText("complain");
                author_text.setText(author);
                time_text.setText(time);
                praise_num_text.setText(praise_num);
                bilittle_num_text.setText(bilittle_num);
                complain_num_text.setText(complain_num);*/

                for (int i=0;i < 5;i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    Log.d("MyAdapterrrrrrrrrrrrr",jsonObject.toString());
                    author = jsonObject.getString("comment_author");
                    //content = jsonObject.getString("comment_content");
                    url = jsonObject.getString("pics");
                    Log.d("URLLLLLLLLLLL", url);
                    url = url.replaceAll("\\\\/","/");
                    Log.d("URLLLLLLLLLLL", url);
                    try {
                        URL httpUrl = new URL(url);
                        try {
                            HttpURLConnection connection = (HttpURLConnection)httpUrl.openConnection();
                            InputStream inputStream = connection.getInputStream();
                            BufferedInputStream bis = new BufferedInputStream(inputStream);
                            Bitmap bitmap = BitmapFactory.decodeStream(bis);
                            bis.close();
                            inputStream.close();
                            imageView.setImageBitmap(bitmap);
                            Log.d("00000000000000000","bitmap exeuted");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    time = jsonObject.getString("comment_date");
                    praise_num = jsonObject.getString("vote_positive");
                    bilittle_num = jsonObject.getString("vote_negative");
                    complain_num = jsonObject.getString("comment_approved");
                    Log.d("MainActivity_author", author);

                    praise_text.setText("OO");
                    bilittle_text.setText("XX");
                    complain_text.setText("complain");
                    author_text.setText(author);
                    time_text.setText(time);
                    praise_num_text.setText(praise_num);
                    bilittle_num_text.setText(bilittle_num);
                    complain_num_text.setText(complain_num);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

      //      progressBar.setVisibility(View.GONE);
        }
        @Override
        protected void onPreExecute() {
            Log.d("MainActivity", "¿ªÊ¼HTTP GETÇëÇó");
         //   progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }


}
