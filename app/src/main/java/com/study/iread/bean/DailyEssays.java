package com.study.iread.bean;

/**
 * Created by dnw on 2017/4/26.
 */
public class DailyEssays {
    private String curr_date;

    public String getPrev_date() {
        return prev_date;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getNext_date() {
        return next_date;
    }

    public String getCurr_date() {
        return curr_date;
    }

    public void setCurr_date(String curr_date) {
        this.curr_date = curr_date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setNext_date(String next_date) {
        this.next_date = next_date;
    }

    public void setPrev_date(String prev_date) {
        this.prev_date = prev_date;
    }

    private String prev_date;
    private String next_date;
    private String author;
    private String title;
    private String content;
}
