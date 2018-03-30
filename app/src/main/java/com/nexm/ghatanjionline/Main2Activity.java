package com.nexm.ghatanjionline;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.nexm.ghatanjionline.fragments.AllCategoriesFragment;
import com.nexm.ghatanjionline.fragments.NewHomeFragment;
import com.nexm.ghatanjionline.models.AdData;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,
        NewHomeFragment.OnFragmentInteractionListener,
        AllCategoriesFragment.OnFragmentInteractionListener

{


    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       // getSupportActionBar().setLogo(R.drawable.ic_action_name);
      //  getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this,Main3Activity.class);
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this,ProductActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

            Intent intent = new Intent(this,AddNewItemActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public  void setCurrentTab(int position){

        setTab(position);
    }
    private void setTab(int position){

        viewPager.setCurrentItem(position,true);
    }



    @Override
    public void onImageAdClick(String tag, String webUrl) {
        //Toast.makeText(getApplicationContext(),category,Toast.LENGTH_LONG).show();
        switch (tag){
            case "Announcment":
                startProductActivity(webUrl,"x","x","x","Web");
        }
    }
    @Override
    public void onContainerItemClick(String category,String subCategory,String listID) {
        Toast.makeText(getApplicationContext(),category +"/"+ subCategory +"/"+ listID ,Toast.LENGTH_LONG).show();
        String tag = listID.matches("x")?"Sub":"Item";
        if(listID.matches("SeeAll")){
            tag = "All";
        }
        startProductActivity(category, subCategory,listID,"x",tag);
    }

    @Override
    public void onCategorySelected(String categoryName) {

        if(categoryName.matches("All")){
            viewPager.setCurrentItem(1);
        }else{
            startProductActivity(categoryName,"x","x","x","All");
        }


    }

    private void startProductActivity(String category, String subCategory,
                                      String itemID,String allCategories,String tag) {
        Intent intent = new Intent(this,ProductActivity.class);
        intent.putExtra("Category",category);
        intent.putExtra("Selected subCategory",subCategory);
        intent.putExtra("ItemID",itemID);
        intent.putExtra("AllCategories",allCategories);
        intent.putExtra("Tag",tag);
        startActivity(intent);
    }

    @Override
    public void onHeaderClick(AdData data, int position) {
        //Toast.makeText(getApplicationContext(),"Selected " + data,Toast.LENGTH_SHORT).show();
        switch(position){
            case 0:
                switch (data.client_name_title_4){
                    case "Item promotion":
                        startProductActivity(data.category,data.subCategory,data.listID_1,"x","Item");
                        break;
                    case "Category promotion":
                        startProductActivity(data.category,data.subCategory,data.listID_1,"x","All");
                        break;
                    case "Sub Category promotion":
                        startProductActivity(data.category,data.subCategory,data.listID_1,"x","Sub");
                        break;
                    case "Registration":
                        //startProductActivity(data.category,data.subCategory,data.listID_1,"x","Sub");
                        break;
                    case "Store Promotion":
                        break;
                    case "Sale":
                        break;
                }
                break;
            case 1 :
                switch (data.adImageUrl_title_2){
                    case "Item promotion":
                        startProductActivity(data.listID_3,data.listID_4,data.sub_title_4,"x","Item");
                        break;
                    case "Category promotion":
                        startProductActivity(data.listID_3,data.listID_4,data.sub_title_4,"x","All");
                        break;
                    case "Sub Category promotion":
                        startProductActivity(data.listID_3,data.listID_4,data.sub_title_4,"x","Sub");
                        break;
                    case "Registration":
                        //startProductActivity(data.category,data.subCategory,data.listID_1,"x","Sub");
                        break;
                    case "Store Promotion":
                        break;
                    case "Sale":
                        break;
                }
                break;
            case 2 :
                switch (data.webUrl_title_3){
                    case "Item promotion":
                        startProductActivity(data.listID_3,data.listID_4,data.sub_title_4,"x","Item");
                        break;
                    case "Category promotion":
                        startProductActivity(data.listID_3,data.listID_4,data.sub_title_4,"x","All");
                        break;
                    case "Sub Category promotion":
                        startProductActivity(data.listID_3,data.listID_4,data.sub_title_4,"x","Sub");
                        break;
                    case "Registration":
                        //startProductActivity(data.category,data.subCategory,data.listID_1,"x","Sub");
                        break;
                    case "Store Promotion":
                        break;
                    case "Sale":
                        break;
                    case "Announcment":
                        startProductActivity(data.listID_2,data.listID_4,data.sub_title_4,"x","Web");
                        break;
                }
                break;

        }
    }




    @Override
    public void onAllCategorySelected(String categoryName) {
        startProductActivity(categoryName,"x","x","x","All");
    }
    @Override public void onPause(){
        super.onPause();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
}
