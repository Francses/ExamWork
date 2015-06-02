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


public class FriendChat extends Fragment{

    String author;
    String time;
    String url;
    String praise_num;
    String bilittle_num;
    String complain_num;

    public List<String> authorList = new ArrayList<String>();
    public List<String> timeList = new ArrayList<String>();
    public List<String> urlList = new ArrayList<String>();
    public List<String> bilittle_numList = new ArrayList<String>();
    public List<String> praise_numList = new ArrayList<String>();
    public List<String> complain_numList = new ArrayList<String>();

    public ArrayList<Information_pic> getInformation_pic(int size){
        int i=0;
        ArrayList<Information_pic> information_pics = new ArrayList<Information_pic>(size);
        for (;i < size; i++){
            Information_pic information = new Information_pic(authorList.get(i),timeList.get(i),urlList.get(i),praise_numList.get(i),bilittle_numList.get(i),complain_numList.get(i));
            information_pics.add(information);
        }
        return information_pics;
    }

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fiendchat,container,false );
        recyclerView = (RecyclerView)view.findViewById(R.id.recycleview_one);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        final RecyclerView.ItemDecoration decoration = new SampleDivider(this.getActivity());
        recyclerView.addItemDecoration(decoration);

        new ReadHttpGet().execute("http://jandan.net/?oxwlxojflwblxbsapi=jandan.get_ooxx_comments&page=1");
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

            /*HttpGet httpRequest = new HttpGet(params[0].toString());
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
            return null;*/
        }

        @Override
        protected void onCancelled(Object result) {
            super.onCancelled(result);
        }

        @Override
        protected void onPostExecute(Object result) {
            /*for (i=0;i<i+1;i++){
                if (result != null){
                    break;
                }
            }*/

            if (result != null){
                super.onPostExecute(result);
                try {
                    JSONArray jsonArray = new JSONObject(result.toString()).getJSONArray("comments");
                    Log.d("MainActivity",jsonArray.toString());
                    JSONObject jsonObject;
                    for (int i=0;i < 6;i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        Log.d("MyAdapter02222222",jsonObject.toString());
                        author = jsonObject.getString("comment_author");
                        time = jsonObject.getString("comment_date");
                        url = jsonObject.getString("pics");
                        praise_num = jsonObject.getString("vote_positive");
                        complain_num = jsonObject.getString("vote_negative");
                        bilittle_num = jsonObject.getString("comment_approved");

                        authorList.add(author);
                        timeList.add(time);
                        urlList.add(url);
                        praise_numList.add(praise_num);
                        complain_numList.add(complain_num);
                        bilittle_numList.add(bilittle_num);

                        Log.d("MainActivity_author", author);
                        Log.d("MainActivity_time",time);
                        Log.d("MainActivity_url",url);
                        Log.d("MainActivity_praise",praise_num);
                        Log.d("complain_complain",complain_num);
                        Log.d("MainActivity_bilittle",bilittle_num);
                    }

                    final MyAdapter myAdapter = new MyAdapter(getInformation_pic(6));
                    recyclerView.setAdapter(myAdapter);
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
            }else {
                Log.d("panduan","nullllllllllllllllllllll");
            }



            //      progressBar.setVisibility(View.GONE);
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
        protected void onPreExecute() {
            Log.d("MainActivity", "¿ªÊ¼HTTP GETÇëÇó");
            //   progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
        }
    }

}
