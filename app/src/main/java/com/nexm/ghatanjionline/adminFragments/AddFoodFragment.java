package com.nexm.ghatanjionline.adminFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.nexm.ghatanjionline.AddNewItemActivity;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.FoodItem;
import com.nexm.ghatanjionline.models.OldItem;
import com.nexm.ghatanjionline.models.PropertyItem;
import com.nexm.ghatanjionline.models.ServicesItem;
import com.nexm.ghatanjionline.models.TransportItem;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link AddFoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFoodFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String category;
    private String subCategory;

    private EditText e1,e2,e3,e4,e5,e6;
    private Button button;
    private CheckBox checkBox;
    private DatabaseReference reference;


    public AddFoodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFoodFragment newInstance(String param1, String param2) {
        AddFoodFragment fragment = new AddFoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(ARG_PARAM1);
            subCategory = getArguments().getString(ARG_PARAM2);
            reference = GOApplication.database.getReference().child(category).child(subCategory);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_food, container, false);
        e1 = (EditText)view.findViewById(R.id.add_food_editText_name);
        e2 = (EditText)view.findViewById(R.id.add_food_editText_price);
        e3 = (EditText)view.findViewById(R.id.add_food_editText_description);
        e4 = (EditText)view.findViewById(R.id.add_food_editText_extra1);
        e5 = (EditText)view.findViewById(R.id.add_food_editText_extra2);
        e6 = (EditText)view.findViewById(R.id.add_food_editText_extra3);
        checkBox = (CheckBox)view.findViewById(R.id.add_food_checkbox);

        if(category.matches("Transport")){
            e3.setHint("model year");
        }else{
            e3.setHint("description");
        }

        if(category.matches("Old Shop")){
           checkBox.setVisibility(View.VISIBLE);
        }else{
            checkBox.setVisibility(View.GONE);
        }

        switch(category){

            case "Food":
                toggleExtraEditTexts(false,null,null,null);
                break;
            case "Transport":
                toggleExtraEditTexts(true,"reg.no","driver name","driver no");
                break;
            case "Services":
                toggleExtraEditTexts(true,"experience","qualification",null);
                break;
            case "Rentals":
                toggleExtraEditTexts(true,"item address","item age",null);
                break;
            case "Old Shop":
                toggleExtraEditTexts(true,"item age",null,null);
                break;

            }
        button = (Button)view.findViewById(R.id.add_food_submit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button button = (Button)v;
                button.setEnabled(false);
                final String e1Text = e1.getText().toString();
                final String e2Text = e2.getText().toString();
                final String e3Text = e3.getText().toString();
                String e4Text = "";
                String e5Text = "";
                String e6Text = "";

                if(e4.isShown()){ e4Text = e4.getText().toString();}
                if(e5.isShown()){e5Text = e5.getText().toString();}
                if(e6.isShown()){e6Text = e6.getText().toString();}
                switch (category){

                    case "Food":

                        final FoodItem foodItem = new FoodItem();
                        foodItem.foodItemNAME = e1Text;
                        foodItem.foodItemPRICE = e2Text;
                        foodItem.foodItemDESCRIPTION = e3Text;
                        foodItem.foodItemID = reference.push().getKey();
                        addToFirebase(foodItem.foodItemID,foodItem);
                        break;
                    case "Transport":
                        final TransportItem transportItem = new TransportItem();
                        transportItem.transportItemNAME = e1Text;
                        transportItem.transportItemPRICE = e2Text;
                        transportItem.transportItemMODEL = e3Text;
                        transportItem.transportItemREG_NO = e4Text ;
                        transportItem.transportItemDRIVER_NAME = e5Text;
                        transportItem.transportItemDRIVER_NO = e6Text;
                        transportItem.transportItemID = reference.push().getKey();
                        addToFirebase(transportItem.transportItemID,transportItem);
                        break;
                    case "Services":
                        final ServicesItem servicesItem = new ServicesItem();
                        servicesItem.servicesItemNAME = e1Text;
                        servicesItem.servicesItemPRICE = e2Text;
                        servicesItem.servicesItemDESCRIPTION = e3Text;
                        servicesItem.servicesItemEXPERIENCE = e4Text;
                        servicesItem.servicesItemQUALIFICATION = e5Text;
                        servicesItem.servicesItemID = reference.push().getKey();
                        addToFirebase(servicesItem.servicesItemID,servicesItem);
                        break;
                    case "Rentals":
                        final PropertyItem propertyItem = new PropertyItem();
                        propertyItem.rentalsItemNAME = e1Text;
                        propertyItem.rentalsItemPRICE = e2Text;
                        propertyItem.rentalsItemDESCRIPTION = e3Text;
                        propertyItem.rentalsItemADDRESS = e4Text;
                        propertyItem.rentalsItemAGE = e5Text;
                        propertyItem.rentalsItemID = reference.push().getKey();
                        addToFirebase(propertyItem.rentalsItemID, propertyItem);
                        break;
                    case "Old Shop":
                        final OldItem oldItem = new OldItem();
                        oldItem.oldItemNAME = e1Text;
                        oldItem.oldItemPRICE = e2Text;
                        oldItem.oldItemDESCRIPTION = e3Text;
                        oldItem.oldItemAGE = e4Text;
                        oldItem.oldItemBILL_AVAILABLE = checkBox.isChecked();
                        oldItem.oldItemID = reference.push().getKey();
                        addToFirebase(oldItem.oldItemID,oldItem);
                        break;
                }

            }
        });
        return view;
    }

    private void addToFirebase(final String id, Object o) {

        reference.child(id)
                .setValue(o)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful()) {
                           Toast.makeText(getActivity(),"Data upload Successful",Toast.LENGTH_SHORT).show();
                           addToList(id,e1.getText().toString(),e2.getText().toString());

                       }

                    }
                });
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    private void toggleExtraEditTexts(boolean toogle,String hint1,String hint2,String hint3){

        if(!toogle){

            e4.setVisibility(View.GONE);
            e5.setVisibility(View.GONE);
            e6.setVisibility(View.GONE);
        }else{
            e4.setVisibility(View.VISIBLE);



            e4.setHint(hint1);
            if(hint2!= null){e5.setHint(hint2);e5.setVisibility(View.VISIBLE);}else{e5.setVisibility(View.GONE);}
            if(hint3!=null){e6.setHint(hint3);e6.setVisibility(View.VISIBLE);}else {e6.setVisibility(View.GONE);}

        }
    }

    private void addToList(String id,String name , String price){

        AddNewItemActivity.listItem.itemID = id;
        AddNewItemActivity.listItem.itemNAME = name;
       // AddNewItemActivity.listItem.itemPRICE =Float.parseFloat(price);

    }
}
