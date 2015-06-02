package com.example.lenovo.homework;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Friend extends Fragment{

    public String author;
    public String time;
    public String content;
    public String bilittle_num2;
    public String praise_num2;
    public String complain_num2;
    RecyclerView recyclerView2;

    public List<String> authorList = new ArrayList<String>();
    public List<String> timeList = new ArrayList<String>();
    public List<String> contentList = new ArrayList<String>();
    public List<String> bilittle_num2List = new ArrayList<String>();
    public List<String> praise_num2List = new ArrayList<String>();
    public List<String> complain_num2List = new ArrayList<String>();

    public ArrayList<Information> getInformation(int size){
        int i=0;
        ArrayList<Information> informations = new ArrayList<Information>(size);
        for (;i < size; i++){
            Information information = new Information(authorList.get(i),timeList.get(i),contentList.get(i),praise_num2List.get(i),bilittle_num2List.get(i),complain_num2List.get(i));
            informations.add(information);
        }
        return informations;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friend,container,false);
        recyclerView2 = (RecyclerView)view.findViewById(R.id.recycleview_two);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView2.setLayoutManager(layoutManager);
        final RecyclerView.ItemDecoration decoration = new SampleDivider(this.getActivity());
        recyclerView2.addItemDecoration(decoration);
        new ReadHttpGet().execute("http://jandan.net/?oxwlxojflwblxbsapi=jandan.get_duan_comments&page=1");
        return view;
    }

    public class ReadHttpGet extends AsyncTask<Object, Object, Object> {
        @Override
        protected Object doInBackground(Object... params) {

            try {
                URL url=new URL(params[0].toString());
                try {
                    HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                    connection.setConnectTimeout(3000);
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);

                    int code=connection.getResponseCode();
                    if (code == 200){
                        Log.d("HttpURLLLLLLLLLLLLLLLL", "succeed");
                        return changeJsonString(connection.getInputStream());
                    }else {
                        return "wrong";
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;

           /* HttpGet httpRequest = new HttpGet(params[0].toString());
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse httpResponse = httpClient.execute(httpRequest);
                if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String strResult = EntityUtils.toString(httpResponse.getEntity());
                    return strResult;
                }else {
                    return "ÇëÇó³ö´í";
                }
            }
            catch(ClientProtocolException e) {
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;*/
        }

        @Override
        protected void onCancelled(Object result) {
            super.onCancelled(result);
        }
        private  String changeJsonString(InputStream inputStream) {
            String jsonString="";
            try {
                ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
                int len=0;
                byte[] data=new byte[1024];
                while((len=inputStream.read(data))!=-1) {
                    outputStream.write(data,0,len);
                }
                jsonString=new String(outputStream.toByteArray());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return jsonString;
        }
        @Override
        protected void onPostExecute(Object result) {
            if (result != null){
                super.onPostExecute(result);
                try {
                    JSONArray jsonArray = new JSONObject(result.toString()).getJSONArray("comments");
                    Log.d("MainActivity",jsonArray.toString());
                    JSONObject jsonObject;
                    for (int i=0;i < 20;i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        Log.d("MyAdapter02222222",jsonObject.toString());
                        author = jsonObject.getString("comment_author");
                        time = jsonObject.getString("comment_date");
                        content = jsonObject.getString("comment_content");
                        praise_num2 = jsonObject.getString("vote_positive");
                        complain_num2 = jsonObject.getString("vote_negative");
                        bilittle_num2 = jsonObject.getString("comment_approved");

                        authorList.add(author);
                        timeList.add(time);
                        contentList.add(content);
                        praise_num2List.add(praise_num2);
                        complain_num2List.add(complain_num2);
                        bilittle_num2List.add(bilittle_num2);

                        Log.d("MainActivity_author", author);
                        Log.d("MainActivity_time",time);
                        Log.d("MainActivity_content",content);
                        Log.d("MainActivity_praise",praise_num2);
                        Log.d("complain_complain",complain_num2);
                        Log.d("MainActivity_bilittle",bilittle_num2);
                    }

                    final MyAdapter02 myAdapter02 = new MyAdapter02(getInformation(20));
                    recyclerView2.setAdapter(myAdapter02);
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
                complain_num2_text.setText(complain_num2);*/


                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
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
