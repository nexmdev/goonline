package com.nexm.ghatanjionline.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by user on 07-06-2017.
 */
@IgnoreExtraProperties
public class Item {

    public String itemID;
    public String itemNAME;
    public String itemDESCRIPTION;
    public String itemPHOTOURL1 = "X";
    public String itemPHOTOURL2 = "X";
    public String itemPHOTOURL3 = "X";
    public float itemPRICE;
    public int itemNO_OF_REVIEWS = 0;
    public int itemRATINGS = 0;

    public Item(){}

    public void setItemID(String mitemID)
    {
        this.itemID = mitemID;
    }
    public void setItemNAME(String mitemNAME)
    {
        this.itemNAME = mitemNAME;
    }
    public void setItemDESCRIPTION(String mitemDESCRIPTION)
    {
        this.itemDESCRIPTION = mitemDESCRIPTION;
    }
    public void setItemPHOTOURL1(String mitemURL1)
    {
        this.itemPHOTOURL1 = mitemURL1;
    }
    public void setItemPHOTOURL2(String mitemURL2)
    {
        this.itemPHOTOURL2 = mitemURL2;
    }
    public void setItemPHOTOURL3(String mitemURL3)
    {
        this.itemPHOTOURL3 = mitemURL3;
    }
    public void setItemPRICE(int mitemPRICE)
    {
        this.itemPRICE = mitemPRICE;
    }
    public void setItemNO_OF_REVIEWS(int mitemNOOFREVIEWS)
    {
        this.itemNO_OF_REVIEWS = mitemNOOFREVIEWS;
    }
    public void setItemRATINGS(int mitemRATINGS)
    {
        this.itemRATINGS = mitemRATINGS;
    }
}
