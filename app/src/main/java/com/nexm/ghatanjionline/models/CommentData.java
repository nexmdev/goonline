package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 15-05-2017.
 */
@IgnoreExtraProperties
public class CommentData {

    public String Comment;
    public String rating;
    public String user;

    public CommentData(){

    }

    public CommentData(String mcomment, String mrating , String muser){

        this.Comment = mcomment;
        this.rating = mrating;
        this.user = muser;
    }
}
