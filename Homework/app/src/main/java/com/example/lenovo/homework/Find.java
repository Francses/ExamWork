package com.example.lenovo.homework;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class Find extends Fragment{
    private DrawerLayout mDrawerLayout;
    private List<Item> list = new ArrayList<Item>();
    Button buttonx;
    ProgressBar progressBar;
    ImageView imageView;
    private View view;
    private static String URL = "http://img4.imgtn.bdimg.com/it/u=2511776455,3603596827&fm=15&gp=0.jpg";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.find,container,false);
        initItem();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDrawerLayout = (DrawerLayout)view.findViewById(R.id.drawer_layout);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        imageView = (ImageView) view.findViewById(R.id.mredrock_pic);
        buttonx = (Button)view.findViewById(R.id.button_drawer);
        buttonx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerListAdapter adapter = new DrawerListAdapter(getActivity(), R.layout.drawer_item, list);
                final ListView listView = (ListView) view.findViewById(R.id.left_drawer);
                listView.setAdapter(adapter);
                mDrawerLayout.openDrawer(Gravity.LEFT);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            new MyAsyncTask().execute(URL);
                        } else if (position == 1) {
                            Toast.makeText(getActivity(), "Update!", Toast.LENGTH_SHORT).show();
                            sendRequestWithHttpClient();
                            String downloadUrl = "http://hongyan.cqupt.edu.cn/app/com.mredrock.cyxbs.apk";
                            try {
                                File downloadFile;
                                FileOutputStream fileOutputStream = null;
                                URL apkURL = new URL(downloadUrl);
                                try {
                                    HttpURLConnection downConnection = (HttpURLConnection)apkURL.openConnection();
                                    downConnection.setReadTimeout(5000);
                                    downConnection.setRequestMethod("GET");
                                    downConnection.setDoInput(true);

                                    InputStream inputStream = downConnection.getInputStream();

                                    String timeString = String.valueOf(System.currentTimeMillis());

                                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                                        String sdPath = Environment.getExternalStorageDirectory() + "/";
                                        File parent1 = Environment.getExternalStorageDirectory();
                                        Log.e("tag",sdPath);
                                        downloadFile = new File(parent1+"/test",timeString+".jpg");
                                        fileOutputStream = new FileOutputStream(downloadFile);byte[] b = new byte[1024*2];
                                        int len ;
                                        Log.e("tag", "....................4");
                                        if (fileOutputStream != null){
                                            while ((len = inputStream.read(b)) != -1){
                                                fileOutputStream.write(b,0,len);
                                            }
                                        }
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setDataAndType(Uri.parse("file://" + downloadFile.toString()), "application/vnd.android.package-archive");
                                        getActivity().startActivity(i);

                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        mDrawerLayout.closeDrawer(listView);
                    }
                });
            }
        });
    }

    public void sendRequestWithHttpClient(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("http://hongyan.cqupt.edu.cn/app/cyxbsAppUpdate.xml");
                try {
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200){
                        HttpEntity entity = httpResponse.getEntity();
                        String response = EntityUtils.toString(entity,"utf-8");
                        parseXMLWithPull(response);
                        Log.d("Findeeeeeeeeeee","sendRequestWithHttpClient");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseXMLWithPull(String xmlData){
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            String versionCode = "";
            String versionName = "";
            String updateContent = "";
            String apkURL = "";
            Log.d("Findeeeeeeeeeee","parseXMLWithPull");
            while (eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        if ("versionCode".equals(nodeName)){
                            try {
                                versionCode = xmlPullParser.nextText();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else if ("versionName".equals(nodeName)){
                            try {
                                versionName = xmlPullParser.nextText();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else if ("updateContent".equals(nodeName)){
                            try {
                                updateContent = xmlPullParser.nextText();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else if ("apkURL".equals(nodeName)){
                            try {
                                apkURL = xmlPullParser.nextText();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                }
            }

            Log.d("Findeeeeeeeeeee","parseXMLWithPull");

            Log.d("versionCodeeeeeeee",versionCode);
            Log.d("versionNameeeeeeeeeee",versionName);
            Log.d("updateContenteeeeeeee",updateContent);
            Log.d("apkURLeeeeeeeeee",apkURL);



        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    private void initItem(){
        Item first = new Item("To showOur mRedrock Logo");
        list.add(first);
        Item second = new Item("To Update");
        list.add(second);
    }

    class MyAsyncTask extends AsyncTask<String,Void,Bitmap> {
        @Override
        protected void onPreExecute() {
           progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = null;
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
        protected void onPostExecute(final Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(),"Click the picture to see the big picture",Toast.LENGTH_LONG).show();
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Picture.class);
                    intent.putExtra("bitmap",bitmap);
                    startActivity(intent);
                }
            });
        }
    }
}
