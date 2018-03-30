package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 01-01-2018.
 */
@IgnoreExtraProperties
public class AdData {

    public String adType_url_1 ;
    public String selectedTag_url_2;
    public String category ;
    public String subCategory ;
    public String listItemID_url_3 ;
    public String start_date_url_4 ;
    public String end_date_title_1;
    public String adImageUrl_title_2 ;
    public String webUrl_title_3 ;
    public String client_name_title_4 ;
    public String client_mono_sub_title_1 ;
    public String sub_title_2;
    public String sub_title_3;
    public String sub_title_4;
    public String listID_1 ;
    public String listID_2 ;
    public String listID_3 ;
    public String listID_4 ;
    public String type ;

    public AdData(){}

    public void setAdType_url_1(String Adtype){

        this.adType_url_1 = Adtype;
    }
    public void setSelectedTag_url_2(String Tag){

        this.selectedTag_url_2 = Tag;
    }
    public void setCategory(String Category){

        this.category = Category;
    }
    public void setSubCategory(String SubCategory){

        this.subCategory = SubCategory;
    }
    public void setListItemID_url_3(String ID){

        this.listItemID_url_3 = ID;
    }
    public void setStart_date_url_4(String SDate) {

        this.start_date_url_4 = SDate;
    }
    public void setEnd_date_title_1(String EDate){

        this.end_date_title_1 = EDate;
    }
    public void setAdImageUrl_title_2(String Url){

        this.adImageUrl_title_2 = Url;
    }
    public void setWebUrl_title_3(String WebUrl){

        this.webUrl_title_3 = WebUrl;
    }
    public void setClient_name_title_4(String name){

        this.client_name_title_4 = name;
    }
    public void setClient_mono_sub_title_1(String mono){

        this.client_mono_sub_title_1 = mono;
    }
    public void setSub_title_2(String t2){

        this.sub_title_2 = t2;
    }
    public void setSub_title_3(String t3){

        this.sub_title_3 = t3;
    }
    public void setSub_title_4(String t4){

        this.sub_title_4 = t4;
    }
    public void setListID_1(String i1){

        this.listID_1 = i1;
    }
    public void setListID_2(String i2){

        this.listID_2 = i2;
    }
    public void setListID_3(String i3){

        this.listID_3 = i3;
    }
    public void setListID_4(String i4){

        this.listID_4 = i4;
    }
    public void setType(String type){

        this.type = type;
    }



}
