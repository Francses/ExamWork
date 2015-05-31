package com.example.lenovo.homework;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.IOException;

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

    String author;
    String time;
    String content;
    String bilittle_num2;
    String praise_num2;
    String complain_num2;

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
    public void onBindViewHolder(MyViewHolder02 myViewHolder02, int i) {
        new ReadHttpGet().execute("http://jandan.net/?oxwlxojflwblxbsapi=jandan.get_duan_comments&page=1");
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ReadHttpGet extends AsyncTask<Object, Object, Object> {
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
                    return "请求出错";
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
                JSONObject jsonObject;

                /*jsonObject = jsonArray.getJSONObject(0);
                author = jsonObject.getString("comment_author");
                time = jsonObject.getString("comment_date");
                content = jsonObject.getString("comment_content");
                praise_num2 = jsonObject.getString("vote_positive");
                complain_num2 = jsonObject.getString("vote_negative");
                bilittle_num2 = jsonObject.getString("comment_approved");

                author_text.setText(author);
                time_text.setText(time);
                content_text.setText(content);
                praise_num2_text.setText(praise_num2);
                bilittle_num2_text.setText(bilittle_num2);
                complain_num2_text.setText(complain_num2);

                Log.d("MainActivity_author", author + "aa");
                Log.d("MainActivity_time", time + "aa");
                Log.d("MainActivity_content", content + "aa");
                Log.d("MainActivity_praise", praise_num2 + "aa");
                Log.d("complain_complain", complain_num2 + "aa");
                Log.d("MainActivity_bilittle", bilittle_num2+ "aa");*/

                for (int i=0;i < 5;i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    Log.d("MyAdapter02222222",jsonObject.toString());
                    author = jsonObject.getString("comment_author");
                    time = jsonObject.getString("comment_date");
                    content = jsonObject.getString("comment_content");
                    praise_num2 = jsonObject.getString("vote_positive");
                    complain_num2 = jsonObject.getString("vote_negative");
                    bilittle_num2 = jsonObject.getString("comment_approved");

                    author_text.setText(author);
                    time_text.setText(time);
                    content_text.setText(content);
                    praise_num2_text.setText(praise_num2);
                    bilittle_num2_text.setText(bilittle_num2);
                    complain_num2_text.setText(complain_num2);

                    Log.d("MainActivity_author", author);
                    Log.d("MainActivity_time",time);
                    Log.d("MainActivity_content",content);
                    Log.d("MainActivity_praise",praise_num2);
                    Log.d("complain_complain",complain_num2);
                    Log.d("MainActivity_bilittle",bilittle_num2);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
           // progressBar.setVisibility(View.GONE);
        }
        @Override
        protected void onPreExecute() {
            Log.d("MainActivity", "Start HTTP GET");
            //progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
        }
    }
}
