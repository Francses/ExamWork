package com.example.lenovo.homework;

/**
 * Created by lenovo on 2015/6/2.
 */
public class Information_pic {

    String author;
    String time;
    String url;
    String praise_num;
    String bilittle_num;
    String complain_num;

    public Information_pic(String author, String time, String url, String praise_num, String bilittle_num, String complain_num){
        this.author = author;
        this.time = time;
        this.url = url;
        this.praise_num = praise_num;
        this.bilittle_num = bilittle_num;
        this.complain_num = complain_num;
    }

    public String getAuthor(){
        return author;
    }

    public String getTime(){
        return time;
    }

    public String getUrl(){
        return url;
    }
    public String getPraise_num(){
        return praise_num;
    }
    public String getComplain_num(){
        return complain_num;
    }
    public String getBilittle_num(){
        return bilittle_num;
    }

}
