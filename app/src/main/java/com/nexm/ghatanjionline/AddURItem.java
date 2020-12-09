package com.nexm.ghatanjionline;

import com.google.android.material.appbar.AppBarLayout;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexm.ghatanjionline.fragments.AddItem_Category_Fragment;
import com.nexm.ghatanjionline.fragments.ItemDetails_Fragment;

public class AddURItem extends AppCompatActivity implements
        AddItem_Category_Fragment.OnFragmentInteractionListener,
        ItemDetails_Fragment.OnFragmentInteractionListener{
    ImageView p1,p2,p3,p4,p5,stepImage;
    TextView title1,title2;
    String category,subCategory;
    FragmentManager manager;
    public static AppBarLayout appBarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ur_item_parent_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        appBarLayout = (AppBarLayout)findViewById(R.id.productAppBar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Item");

       /* p1 = (ImageView) findViewById(R.id.add_ur_item_category_tab);
        p2 = (ImageView) findViewById(R.id.add_ur_item_details_tab);
        p3 = (ImageView) findViewById(R.id.add_ur_item_photo_tab);
        p4 = (ImageView) findViewById(R.id.add_ur_item_delivery_tab);
        p5 = (ImageView) findViewById(R.id.add_ur_item_full_tab);*/

       stepImage = (ImageView)findViewById(R.id.step_icon_imageview);

        title1 = (TextView)findViewById(R.id.step_no_textView) ;
        title2 = (TextView)findViewById(R.id.step_description_textview) ;

        manager = getSupportFragmentManager();

        if(savedInstanceState == null){


           // p1.setImageResource(R.drawable.add_item_category_current);
            AddItem_Category_Fragment fragment = AddItem_Category_Fragment.newInstance();
            manager.beginTransaction()
                    .add(R.id.step_frame_layout,fragment)
                    .commit();

        }

    }

    @Override
    public void onCategorySelection(String Category, String subcategory) {
        category = Category;
        subCategory = subcategory;
       // p1.setImageResource(R.drawable.add_item_category_complete);
        stepImage.setImageResource(R.drawable.add_item_details_default);
        title1.setText("Step 2 of 5");
        title2.setText("Give details of item");
        ItemDetails_Fragment fragment = ItemDetails_Fragment.newInstance();
        manager.beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right,
                        R.anim.slide_in_right,
                        R.anim.slide_out_left)
                .replace(R.id.step_frame_layout,fragment)
                .commit();

    }

    @Override
    public void onItemDetalsSubmitted(String itemID) {

    }
}
