package com.example.lenovo.homework;

/**
 * Created by lenovo on 2015/6/2.
 */
public class Information {

    public String author;
    public String time;
    public String content;
    public String bilittle_num2;
    public String praise_num2;
    public String complain_num2;

    public String getAuthor(){
        return author;
    }

    public String getTime(){
        return time;
    }

    public String getContentr(){
        return content;
    }

    public String getPraise_num2(){
        return praise_num2;
    }

    public String getBilittle_num2(){
        return bilittle_num2;
    }

    public String getComplain_num2(){
        return complain_num2;
    }

    public Information(String author, String time, String content, String praise_num2, String bilittle_num2, String complain_num2){
        this.author = author;
        this.time = time;
        this.content = content;
        this.praise_num2 = praise_num2;
        this.bilittle_num2 = bilittle_num2;
        this.complain_num2 = complain_num2;
    }

}
