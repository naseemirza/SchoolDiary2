package com.lead.infosystems.schooldiary.Data;

import android.graphics.drawable.Drawable;

/**
 * Created by Faheem on 29-09-2016.
 */

public class Post_Data {
    private String first_name,last_name,id,text_message,src_link;
    private long timeInmilisec;

    public Post_Data(String first_name, String last_name, String id, String text_message, String src_link, long timeInmilisec) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.id = id;
        this.text_message = text_message;
        this.src_link = src_link;
        this.timeInmilisec = timeInmilisec;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getId() {
        return id;
    }

    public String getText_message() {
        return text_message;
    }

    public String getSrc_link() {
        return src_link;
    }

    public long getTimeInmilisec() {
        return timeInmilisec;
    }

}
