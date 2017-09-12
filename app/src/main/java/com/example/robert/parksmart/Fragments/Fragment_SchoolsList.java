package com.example.robert.parksmart.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.robert.parksmart.listServices.AdapterSchoolNameCardView;
import com.example.robert.parksmart.enteties.SchoolName;
import com.example.robert.parksmart.activities.MainActivity;
import com.example.robert.parksmart.R;

import java.util.ArrayList;


/**
 * Created by Roberto on 8/3/2017.
 */


public class Fragment_SchoolsList extends Fragment implements MainActivity.SearchSchoolList{

    /*Create our list that we will pass in to the RecyclerView*/
    public String[] schoolNames = {
            "California State University, Los Angeles",
            "California State University, Long Beach", "California State University, San Marcos",
            "Grossmont Community College","San Diego State University", "San Diego Mesa College",
            "San Francisco State University", "University of California, Berkeley",
            "University of California, Los Angeles", "University of California, San Diego","University of San Diego"};

    String qText;
    RecyclerView recyclerViewSchools;
    AdapterSchoolNameCardView adapterSchoolNameCardView;
    RecyclerView.LayoutManager layoutManagerSchool;
    ArrayList<SchoolName> schoolList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;





    public static Fragment_SchoolsList newInstance(){

        Log.d("fragmentCall","Fragment is called" );
        //create new instance method
        Fragment_SchoolsList fragment_schoolList = new Fragment_SchoolsList();
        Bundle args = new Bundle();
        fragment_schoolList.setArguments(args);
        return fragment_schoolList;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_list,container,false);

        recyclerViewSchools = (RecyclerView) view.findViewById(R.id.recyclerViewSchools);
        layoutManagerSchool = new LinearLayoutManager(getContext());
        recyclerViewSchools.setLayoutManager(layoutManager);
        recyclerViewSchools.setHasFixedSize(true);
        createList();


        return view;
    }




    private  void createList(){


        Log.d("QText","Value is: "+qText); //is our value passed into the searhView from the action bar


        /*Add the school names to the Recycler View */
        for(int i = 0 ; i < schoolNames.length; i++){
            if(schoolNames[i].contains(qText)) {
                //Log.d("ListLoop", "passed!");
                schoolList.add(new SchoolName(schoolNames[i]));
            }

        }




        if(schoolList.size() == 0){
            //if the new list is composed of zero objects
            Log.d("test","newList is 0");
            return;

        }else{
            Log.d("test","list contains values");

            /*Connect the recycler view with the data that will fill into the cardView objects*/
            adapterSchoolNameCardView = new AdapterSchoolNameCardView(schoolList,getContext());
            recyclerViewSchools.setAdapter(adapterSchoolNameCardView);
            recyclerViewSchools.setLayoutManager(layoutManagerSchool);
            recyclerViewSchools.setHasFixedSize(true);
        }



    }


    @Override
    public void searchQuery(String val) {
        Log.d("SchoolCaught", "Value: "+val);
        this.qText = val; // value passed in to the searchView
    }
}
