package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 08-05-2017.
 */
@IgnoreExtraProperties
public class SubRepairData {

    public String subRepairImage_url;
    public String subRepairName;
    public String subRepairWork;
    public String subRepairYears;
    public int subRepairRatings;
    public int subRepairNoOfUsers;

    public SubRepairData(){}

    public void setSubRepairImage_url(String url){

        this.subRepairImage_url = url;
    }
    public void setSubRepairName (String name){

        this.subRepairName = name;
    }
    public void setSubRepairWork(String work){

        this.subRepairWork = work;
    }
    public void setSubRepairYears(String years){

        this.subRepairYears = years;
    }
    public void setSubRepairRatings(int repairRatings){

        this.subRepairRatings = repairRatings;
    }
    public void setSubRepairNoOfUsers(int noOfUsers){

        this.subRepairNoOfUsers = noOfUsers;
    }
}
